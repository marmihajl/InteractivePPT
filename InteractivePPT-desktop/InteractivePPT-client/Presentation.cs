using Microsoft.Office.Core;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Net;
using System.Windows.Forms;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using EXCEL = Microsoft.Office.Interop.Excel;
using System.Drawing;
using System.Diagnostics;
using System.Net.Sockets;
using System.Threading;

namespace InteractivePPT
{
    public partial class Presentation : MetroFramework.Forms.MetroForm
    {
        PowerPoint.Application pptApp = new PowerPoint.Application();
        string path;
        PowerPoint.Presentation p = null;
        List<Survey> surveyList = null;
        QuestionList myQuestionList = null;
        string serializedUserSurveys = null;
        AnswerList myAnswerList = null;
        int move = 1;
        string userUid;
        TextItemsList textItem;
        public static TextItemsList chooseItem = new TextItemsList();
        string name;
        public static bool make = false;
        TcpClient tcpClient;
        int slideIndex;

        public Presentation(string path, List<Survey> surveyList, string userUid)
        {
            InitializeComponent();
            this.path = path;
            this.surveyList = surveyList;
            this.userUid = userUid;
        }


        private void Presentation_Load(object sender, EventArgs e)
        {
            pptApp.Visible = Microsoft.Office.Core.MsoTriState.msoTrue;
            pptApp.Activate();
            PowerPoint.Presentations ps = pptApp.Presentations;
            p = ps.Open(path,
                        Microsoft.Office.Core.MsoTriState.msoFalse, Microsoft.Office.Core.MsoTriState.msoFalse, Microsoft.Office.Core.MsoTriState.msoTrue);


            BindingSource bs = new BindingSource();
            bs.DataSource = surveyList;
            comboBox1.DataSource = bs;
            comboBox1.DisplayMember = "name";
            comboBox1.ValueMember = "id";
            comboBox1.SelectedIndex = -1;

            Dictionary<string, string> test = new Dictionary<string, string>();
            test.Add("1", "slijedeći slajd");
            test.Add("2", "tag: naziv pitanja");
            graphPosition.DataSource = new BindingSource(test, null);
            graphPosition.DisplayMember = "Value";
            graphPosition.ValueMember = "Key";


            try
            {
                ushort port;
                byte[] response;
                using (WebClient httpClient = new WebClient())
                {
                    response =
                    httpClient.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                    {
                        { "request_type", "get_notifiers_listening_port" },
                        { "path", "ppt/" + path.Substring(path.LastIndexOf('\\')+1) }
                    });
                }
                if (!ushort.TryParse(System.Text.Encoding.UTF8.GetString(response), out port))
                {
                    MessageBox.Show("Pojavila se pogreška na strani poslužitelja kod pokušaja pokretanja daemon procesa za Vašu prezentaciju! Pokušajte ponovno za koji trenutak ili se javite administratoru poslužitelja");
                    this.Close();
                }
                tcpClient = new TcpClient(AddressFamily.InterNetwork);
                tcpClient.BeginConnect("46.101.247.168", port, ListenForInterestedUsers, tcpClient);
            }
            catch (ArgumentNullException ex)
            {
                MessageBox.Show("ArgumentNullException: ", ex.Message);
                Application.Exit();
            }
            catch (SocketException ex)
            {
                MessageBox.Show("SocketException: ", ex.Message);
                Application.Exit();
            }
        }

        private void ListenForInterestedUsers(IAsyncResult ar)
        {
            NetworkStream stream = tcpClient.GetStream();

            while (true)
            {
                Byte[] data = new Byte[256];

                String responseData = String.Empty;

                Int32 bytes;
                try
                {
                    bytes = stream.Read(data, 0, data.Length);
                }
                catch
                {
                    break;
                }
                responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes).TrimEnd('\n').Replace("\f", "\n");
                if (responseData == String.Empty || responseData == "exit")
                {
                    break;
                }
                string[] messageInfo = responseData.Split('\t');
                if (messageInfo[2] == "")
                {
                    dgvReplice.Invoke((MethodInvoker)delegate
                    {
                        dgvReplice.Rows.Add(messageInfo[1], messageInfo[0]);
                    });
                }
                else
                {
                    MessageBox.Show("Korisnik " + messageInfo[1] + " je poslao sljedeću poruku: " + messageInfo[2]);
                }
            }

            stream.Close();
            tcpClient.Close();
        }
        
        

        private int currentSlide()
        {
            PowerPoint.Slide slideIndex = pptApp.ActiveWindow.View.Slide;
            return slideIndex.SlideIndex;
        }

        private void addSlide(List<Answer> item, string question)
        {
            PowerPoint.Slides slides;
            PowerPoint._Slide slide;
            slides = p.Slides;
            
            if (graphPosition.SelectedIndex == 0)
                slideIndex = currentSlide() + move++;
            if (graphPosition.SelectedIndex == 1)
            {
                for (int i = currentSlide(); i < p.Slides.Count; i++)
                {
                    foreach (var it in p.Slides[i + 1].Shapes)
                    {
                        var shape = (PowerPoint.Shape)it;
                        if (shape.HasTextFrame == MsoTriState.msoTrue)
                        {
                            if (shape.TextFrame.HasText == MsoTriState.msoTrue)
                            {
                                string textRange = shape.TextFrame.TextRange.Text;
                                string hash = "[" + question + "]";
                                if (textRange == hash)
                                {
                                    slideIndex = i + 1;
                                }
                            }
                        }
                    }
                }
            }
            
            slide = slides.AddSlide(slideIndex, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);
            //var chart = slide.Shapes.AddChart(XlChartType.xlBarClustered, 10f, 10f, 900f, 400f).Chart;
            PowerPoint.Chart chart = null;

            if (radioBar.Checked == true)
                chart = slide.Shapes.AddChart(XlChartType.xlBarClustered, 10f, 10f, 900f, 400f).Chart;

            if (radioLine.Checked == true)
                chart = slide.Shapes.AddChart(XlChartType.xlLine, 10f, 10f, 900f, 400f).Chart;

            if (radioPie.Checked == true)
            {
                chart = slide.Shapes.AddChart(XlChartType.xlPie, 10f, 10f, 900f, 400f).Chart;
                chart.ApplyDataLabels();
                chart.HasLegend = true;
            }
            else
            {
                chart.HasLegend = false;
            }

            

            var workbook = (EXCEL.Workbook)chart.ChartData.Workbook;
            workbook.Windows.Application.Visible = false;

            var dataSheet = (EXCEL.Worksheet)workbook.Worksheets[1];
            
            int index = 1;

            dataSheet.Cells.Range["A" + index].Value2 = "";
            dataSheet.Cells.Range["B" + index].Value2 = "";
            index++;
            
            foreach (var i in item)
            {
                string input = "'" + i.choice_name;
                dataSheet.Cells.Range["A" + index].Value2 = input;
                dataSheet.Cells.Range["B" + index].Value2 = i.count;
                index++;
            }

            dataSheet.Cells.get_Range("A1:A10");

            if (index < 6)
            {
                for (int i = 6; i >= index; i--)
                { 
                    ((EXCEL.Range)dataSheet.Rows[i]).Delete(EXCEL.XlDeleteShiftDirection.xlShiftUp);
                }
            }

            for (int i = 4; i > 2; i--)
            {
                ((EXCEL.Range)dataSheet.Columns[i]).Delete(EXCEL.XlDeleteShiftDirection.xlShiftToLeft);
            }

            chart.HasTitle = true;
            chart.ChartTitle.Font.Italic = true;
            chart.ChartTitle.Text = question;
            chart.ChartTitle.Font.Size = 12;
            chart.ChartTitle.Font.Color = Color.Black.ToArgb();

            chart.Refresh();
            workbook.Close();

            p.Slides[slideIndex + 1].Delete();
            
            p.Save();

            foreach (Process clsProcess in Process.GetProcesses())
                if (clsProcess.ProcessName.Equals("EXCEL"))  
                    clsProcess.Kill();

        }

        private void updatePresentation()
        {
            string name = path.Substring(path.LastIndexOf('\\') + 1);
            using (WebClient client = new WebClient())
            {
                try
                {
                    byte[] response =
                    client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                    {
                    { "request_type", "delete_file" },
                    { "file", name }
                    });
                }
                catch
                {
                    MessageBox.Show("Communication with server-side of this application could not be established");
                    return;
                }

            }

            FileClass.uploadFile(path, userUid);
        }

        private void Presentation_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (tcpClient != null)
            {
                string name = "ppt/" + path.Substring(path.LastIndexOf('\\') + 1);
                using (WebClient client = new WebClient())
                {
                    try
                    {
                        byte[] response =
                        client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                        {
                    { "request_type", "update_subscription" },
                    { "path", name }
                        });
                    }
                    catch
                    {
                        MessageBox.Show("Communication with server-side of this application could not be established");
                        return;
                    }

                    try
                    {
                        client.UploadValuesAsync(new Uri("http://46.101.68.86/interactivePPT-server.php"), new NameValueCollection()
                    {
                    { "request_type", "shutdown_listener" },
                    { "path", name }
                    });
                    }
                    catch
                    {
                        MessageBox.Show("Communication with server-side of this application could not be established");
                        return;
                    }
                }
                tcpClient.Close();
            }
        }
        

        public void removeAudience(int selectedRow)
        {
            byte[] response;
            string name = "ppt/" + path.Substring(path.LastIndexOf('\\') + 1);
            using (WebClient client = new WebClient())
            {
                try
                {
                    response =
                    client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                    {
                        { "request_type", "delete_audience" },
                        { "app_uid", dgvReplice.Rows[selectedRow].Cells[1].Value.ToString()},
                        { "path", name }
                    });
                }
                catch
                {
                    MessageBox.Show("Communication with server-side of this application could not be established! Application will now shut down..");
                    Application.Exit();
                    return;
                }
            }

            dgvReplice.Invoke(new MethodInvoker(delegate
            {
                dgvReplice.Rows.RemoveAt(selectedRow);
                dgvReplice.Refresh();
            }));

        }

        private void Presentation_Activated(object sender, EventArgs e)
        {
            if(chooseItem.results != null && make)
            {
                addTextSlide(name);
                make = false;
            }
        }

        public void addTextSlide(string name)
        {
            string answer = name+"\n";
            PowerPoint.Slides slides;
            PowerPoint._Slide slide;
            slides = p.Slides;
            slide = slides.AddSlide(currentSlide() + move++, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);



            foreach (TextItems item in chooseItem.results)
            {
                answer += item.choice_name + "\n";
            }

            slide.Shapes[1].TextFrame.TextRange.Text = answer;
            slide.Shapes[1].TextFrame.TextRange.Font.Name = "Arial";
            slide.Shapes[1].TextFrame.TextRange.Font.Size = 18;

            p.Save();
        }

        private void comboBox1_SelectedIndexChanged_1(object sender, EventArgs e)
        {
            if (comboBox1.SelectedIndex != -1)
            {
                string chosenSurvey = comboBox1.SelectedValue.ToString();
                using (WebClient client = new WebClient())
                {
                    try
                    {
                        byte[] response =
                        client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                        {
                    { "request_type", "get_questions" },
                    { "survey_id", chosenSurvey }
                        });
                        serializedUserSurveys = System.Text.Encoding.UTF8.GetString(response);
                    }
                    catch
                    {
                        MessageBox.Show("Communication with server-side of this application could not be established");
                        return;
                    }
                }

                if (serializedUserSurveys != null)
                {
                    myQuestionList = JsonConvert.DeserializeObject<QuestionList>(serializedUserSurveys);
                }

                ((ListBox)questionList).DataSource = myQuestionList.questions;
                ((ListBox)questionList).DisplayMember = "name";
                questionList.Visible = true;

                foreach (Question item in questionList.Items)
                {
                    if (item.Question_type_idQuestion_type == 3)
                    {
                        item.name += "*";
                    }
                }
            }
        }

        private void metroGrid1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex != -1)
            {
                removeAudience(e.RowIndex);
            }
        }

        private void btnAddGraph_Click_1(object sender, EventArgs e)
        {
            int id = 0;
            for (int i = 0; i < questionList.Items.Count; i++)
            {
                if (questionList.GetItemCheckState(i) == CheckState.Checked)
                {

                    Question q = (Question)questionList.Items[i];
                    id = q.idQuestions;
                    name = q.name;
                    if (q.Question_type_idQuestion_type != 3)
                    {
                        using (WebClient client = new WebClient())
                        {
                            try
                            {
                                byte[] response =
                                client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                                {
                                    { "request_type", "get_results" },
                                    { "id", id.ToString() }
                                });
                                serializedUserSurveys = System.Text.Encoding.UTF8.GetString(response);
                            }
                            catch
                            {
                                MessageBox.Show("Communication with server-side of this application could not be established");
                                return;
                            }
                            if (serializedUserSurveys != null)
                            {
                                myAnswerList = JsonConvert.DeserializeObject<AnswerList>(serializedUserSurveys);
                                addSlide(myAnswerList.results, q.name);
                            }

                        }
                    }
                    else
                    {
                        using (WebClient client = new WebClient())
                        {
                            try
                            {
                                byte[] response =
                                client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                                {
                                    { "request_type", "get_text_results" },
                                    { "id", id.ToString() }
                                });
                                serializedUserSurveys = System.Text.Encoding.UTF8.GetString(response);
                            }
                            catch
                            {
                                MessageBox.Show("Communication with server-side of this application could not be established");
                                return;
                            }
                            if (serializedUserSurveys != null)
                            {
                                chooseItem.results = new List<TextItems>();
                                textItem = JsonConvert.DeserializeObject<TextItemsList>(serializedUserSurveys);
                                ComentarFilter cf = new ComentarFilter(textItem, this);
                                cf.Show();
                                Hide();
                            }

                        }
                    }

                }

            }
            move = 1;
            updatePresentation();
        }

        private void Presentation_FormClosed(object sender, FormClosedEventArgs e)
        {
            foreach (Process clsProcess in Process.GetProcesses())
                if (clsProcess.ProcessName.Equals("POWERPNT"))
                    clsProcess.Kill();

        }

        
    }
}
