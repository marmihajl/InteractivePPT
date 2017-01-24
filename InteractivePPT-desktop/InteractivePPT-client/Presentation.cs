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

namespace InteractivePPT
{
    public partial class Presentation : Form
    {
        PowerPoint.Application pptApp = new PowerPoint.Application();
        string path;
        PowerPoint.Presentation p = null;
        SurveyList surveyList = null;
        QuestionList myQuestionList = null;
        string serializedUserSurveys = null;
        AnswerList myAnswerList = null;
        int move = 1;
        Users users;
        string userUid;
        TextItemsList textItem;
        public static TextItemsList chooseItem = new TextItemsList();

        public Presentation(string path, SurveyList surveyList, string userUid)
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
            bs.DataSource = surveyList.data;
            comboBox1.DataSource = bs;
            comboBox1.DisplayMember = "name";
            comboBox1.ValueMember = "id";
            comboBox1.SelectedIndex = -1;

            checkAudienceTimer.Start();
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
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

        private void btnAddGraph_Click(object sender, EventArgs e)
        {
            int id = 0;
            for (int i = 0; i < questionList.Items.Count; i++)
            {
                if (questionList.GetItemCheckState(i) == CheckState.Checked)
                {
                    Question q = (Question)questionList.Items[i];
                    id = q.idQuestions;
                    if(q.Question_type_idQuestion_type != 3)
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
                                textItem = JsonConvert.DeserializeObject<TextItemsList>(serializedUserSurveys);
                                
                            }

                        }
                    }
                    
                }

            }
            move = 1;
            updatePresentation();
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
            slide = slides.AddSlide(currentSlide() + move++, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);
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
            workbook.Windows.Application.Visible = true;

            var dataSheet = (EXCEL.Worksheet)workbook.Worksheets[1];
            dataSheet.Cells.ClearContents();

            int index = 1;

            dataSheet.Cells.Range["A" + index].Value2 = "";
            dataSheet.Cells.Range["B" + index].Value2 = "";
            index++;
            
            foreach (var i in item)
            {
                dataSheet.Cells.Range["A" + index].Value2 = i.choice_name;
                dataSheet.Cells.Range["B" + index].Value2 = i.count;
                index++;
            }

            chart.HasTitle = true;
            chart.ChartTitle.Font.Italic = true;
            chart.ChartTitle.Text = question;
            chart.ChartTitle.Font.Size = 12;
            chart.ChartTitle.Font.Color = Color.Black.ToArgb();

            chart.Refresh();
            workbook.Close();
            
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

            }
        }

        public void action()
        {
            dgvReplice.Rows.Clear();
            dgvReplice.Refresh();
        }

        private void dgvReplice_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex != -1)
            {
                removeAudience(e.RowIndex);
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

        public void refreshAudience()
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
                            { "request_type", "get_interested_audience" },
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

            string serializedUsers = System.Text.Encoding.UTF8.GetString(response);

           
            if (serializedUsers != null)
            {
                users = JsonConvert.DeserializeObject<Users>(serializedUsers);
            }

            dgvReplice.Invoke(new MethodInvoker(delegate
            {
                dgvReplice.Rows.Clear();
                foreach (User user in users.data)
                {
                    dgvReplice.Rows.Add(
                        user.name,
                        user.uid
                    );
                }
            }));

        }

        private void checkAudienceTimer_Tick(object sender, EventArgs e)
        {
            checkAudienceTimer.Stop();
            refreshAudience();
            checkAudienceTimer.Start();
        }
    }
}
