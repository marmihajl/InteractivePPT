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
using System.Text.RegularExpressions;
using System.Text;
using System.Reflection;
using System.Runtime.InteropServices;

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
            foreach (TabPage currPage in this.audienceTabControl.TabPages)
            {
                this.audienceTabControl.SelectedTab = currPage;
            }
            this.audienceTabControl.SelectedTab = this.audienceTabControl.TabPages[0];

            pptApp.Visible = MsoTriState.msoTrue;
            pptApp.Activate();
            PowerPoint.Presentations ps = pptApp.Presentations;
            p = ps.Open(path,
                        MsoTriState.msoFalse, MsoTriState.msoFalse, MsoTriState.msoTrue);


            BindingSource bs = new BindingSource();
            bs.DataSource = surveyList;
            comboBox1.DataSource = bs;
            comboBox1.DisplayMember = "name";
            comboBox1.ValueMember = "id";
            comboBox1.SelectedIndex = -1;

            Dictionary<string, string> test = new Dictionary<string, string>();
            test.Add("1", "sljedeći slajd");
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
                    httpClient.UploadValues(Home.mainScriptUri, new NameValueCollection()
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
                tcpClient.BeginConnect(Home.serverHostname, port, ListenForInterestedUsers, tcpClient);
            }
            catch
            {
                this.Close();
            }
        }

        private void ListenForInterestedUsers(IAsyncResult ar)
        {
            NetworkStream stream = tcpClient.GetStream();

            while (true)
            {
                byte[] data = new byte[102400];

                string responseData = string.Empty;

                int bytes;
                try
                {
                    bytes = stream.Read(data, 0, data.Length);
                }
                catch
                {
                    break;
                }
                responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes).TrimEnd('\n').Replace("\f", "\n");
                if (responseData == string.Empty || responseData == "exit")
                {
                    break;
                }
                string[] messageInfo = responseData.Split('\t');
                Regex regex = new Regex(" \\((\\d+)\\)$");
                if (messageInfo[2] == "")
                {
                    dgvReplies.Invoke((MethodInvoker)delegate
                    {
                        dgvReplies.Rows.Add(messageInfo[0], messageInfo[1]);
                    });
                    audienceTabControl.Invoke((MethodInvoker)delegate
                    {
                        if (audienceTabControl.SelectedTab == tabChat)
                        {
                            int currNumOfUnreadReplies = 0;
                            int.TryParse(regex.Match(tabReplies.Text).Groups[1].Value, out currNumOfUnreadReplies);
                            currNumOfUnreadReplies++;
                            tabReplies.Text = regex.Replace(tabReplies.Text, "") + " (" + currNumOfUnreadReplies + ")";
                        }
                    });
                }
                else
                {
                    dgvChat.Invoke((MethodInvoker)delegate
                    {
                        dgvChat.Rows.Add(messageInfo[0], messageInfo[1], messageInfo[2].Trim());
                        dgvChat.FirstDisplayedScrollingRowIndex = dgvChat.RowCount - 1;
                    });
                    audienceTabControl.Invoke((MethodInvoker)delegate
                    {
                        if (audienceTabControl.SelectedTab == tabReplies)
                        {
                            int currNumOfUnreadChat = 0;
                            int.TryParse(regex.Match(tabChat.Text).Groups[1].Value, out currNumOfUnreadChat);
                            currNumOfUnreadChat++;
                            tabChat.Text = regex.Replace(tabChat.Text, "") + " (" + currNumOfUnreadChat + ")";
                        }
                    });
                }
            }

            stream.Close();
            tcpClient.Close();
        }
        
        

        private int GetIndexOfCurrentSlide()
        {
            PowerPoint.Slide currSlide = pptApp.ActiveWindow.View.Slide;
            return currSlide.SlideIndex;
        }

        private void AddSlide(List<Answer> item, string question)
        {
            PowerPoint.Slides slides;
            PowerPoint._Slide slide;
            slides = p.Slides;

            if (graphPosition.SelectedIndex == 0)
            {
                slideIndex = GetIndexOfCurrentSlide() + move++;
            }
            else if (graphPosition.SelectedIndex == 1)
            {
                for (int i = GetIndexOfCurrentSlide(); i < p.Slides.Count; i++)
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
            
            if (slideIndex == 0)
            {
                MessageBox.Show(String.Format("Za sada na prezentaciji ne postoji graf s pitanjem \"{0}\". Pozicionirajte se na slajd iza kojeg želite dodati slajd s grafikonom, odaberite u aplikaciji opciju \"sljedeći slajd\" za mjesto grafa te ponovno dodajte graf", question), "Ne postoji grafikon za odabrano pitanje u prezentaciji");
                return;
            }
            slide = slides.AddSlide(slideIndex, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);
            //var chart = slide.Shapes.AddChart(XlChartType.xlBarClustered, 10f, 10f, 900f, 400f).Chart;
            PowerPoint.Chart chart = null;

            if (radioBar.Checked == true)
                chart = slide.Shapes.AddChart(XlChartType.xlBarClustered, 10f, 10f, 900f, 400f).Chart;

            else if (radioLine.Checked == true)
                chart = slide.Shapes.AddChart(XlChartType.xlLine, 10f, 10f, 900f, 400f).Chart;

            else if (radioPie.Checked == true)
            {
                chart = slide.Shapes.AddChart(XlChartType.xlPie, 10f, 10f, 900f, 400f).Chart;
                chart.ApplyDataLabels();
                chart.HasLegend = true;
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

            if (graphPosition.SelectedIndex == 1)
            {
                p.Slides[slideIndex + 1].Delete();
            }
            
            p.Save();

            foreach (Process clsProcess in Process.GetProcesses())
                if (clsProcess.ProcessName.Equals("EXCEL"))  
                    clsProcess.Kill();

        }

        private void UpdatePresentation()
        {
            string name = path.Substring(path.LastIndexOf('\\') + 1);
            using (WebClient client = new WebClient())
            {
                try
                {
                    byte[] response =
                    client.UploadValues(Home.mainScriptUri, new NameValueCollection()
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

            FileUploader.UploadFile(path, userUid);
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
                        client.UploadValues(Home.mainScriptUri, new NameValueCollection()
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
                        client.UploadValuesAsync(new Uri(Home.mainScriptUri), new NameValueCollection()
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
        

        public void RemoveAudience(int selectedRow)
        {
            byte[] response;
            string name = "ppt/" + path.Substring(path.LastIndexOf('\\') + 1);
            using (WebClient client = new WebClient())
            {
                try
                {
                    response =
                    client.UploadValues(Home.mainScriptUri, new NameValueCollection()
                    {
                        { "request_type", "delete_audience" },
                        { "app_uid", (string)dgvReplies.Rows[selectedRow].Cells[0].Value},
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

            dgvReplies.Invoke(new MethodInvoker(delegate
            {
                dgvReplies.Rows.RemoveAt(selectedRow);
                dgvReplies.Refresh();
            }));

        }

        private void Presentation_Activated(object sender, EventArgs e)
        {
            if(chooseItem.results != null && make)
            {
                AddTextSlide(name);
                make = false;
            }
        }

        public void AddTextSlide(string name)
        {
            string answer = name+"\n";
            PowerPoint.Slides slides;
            PowerPoint._Slide slide;
            slides = p.Slides;
            slide = slides.AddSlide(GetIndexOfCurrentSlide() + move++, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);



            foreach (TextItems item in chooseItem.results)
            {
                answer += item.choice_name + "\n";
            }

            slide.Shapes[1].TextFrame.TextRange.Text = answer;
            slide.Shapes[1].TextFrame.TextRange.Font.Name = "Arial";
            slide.Shapes[1].TextFrame.TextRange.Font.Size = 18;

            p.Save();
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
                        client.UploadValues(Home.mainScriptUri, new NameValueCollection()
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
                    name = q.name;
                    if (q.Question_type_idQuestion_type != 3)
                    {
                        using (WebClient client = new WebClient())
                        {
                            try
                            {
                                byte[] response =
                                client.UploadValues(Home.mainScriptUri, new NameValueCollection()
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
                                AddSlide(myAnswerList.results, q.name);
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
                                client.UploadValues(Home.mainScriptUri, new NameValueCollection()
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
                                CommentFilter cf = new CommentFilter(textItem, this);
                                cf.Show();
                                Hide();
                            }

                        }
                    }

                }

            }
            move = 1;
            UpdatePresentation();
        }

        private void Presentation_FormClosed(object sender, FormClosedEventArgs e)
        {
            foreach (Process clsProcess in Process.GetProcesses())
                if (clsProcess.ProcessName.Equals("POWERPNT"))
                    clsProcess.Kill();

        }

        private void tabReplies_Enter(object sender, EventArgs e)
        {
            Regex regex = new Regex(" \\(\\d+\\)$");
            tabReplies.Invoke((MethodInvoker)delegate
            {
                tabReplies.Text = regex.Replace(tabReplies.Text, "");
            });
        }

        private void tabChat_Enter(object sender, EventArgs e)
        {
            Regex regex = new Regex(" \\(\\d+\\)$");
            tabChat.Invoke((MethodInvoker)delegate
            {
                tabChat.Text = regex.Replace(tabChat.Text, "");
            });
        }

        private void dgvReplies_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex != -1)
            {
                RemoveAudience(e.RowIndex);
            }
        }

        /*  defined ShadowType property on MetroForms in MetroPanel caused controls and components to be unresponsive, e.g. no tool-tips, inability to select row in DataGridView/MetroGrid, etc. (this is due to bug in MetroUI and its forked project MetroFramework)
        private void dgvChat_CellMouseEnter(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.ColumnIndex >= 0)
            {
                DataGridViewCell cell = dgvChat[e.ColumnIndex, e.RowIndex];
                int contentWidth;
                if (e.ColumnIndex != 2)
                {
                    contentWidth = TextRenderer.MeasureText((string)cell.Value, dgvChat.Font).Width;
                }
                else
                {
                    SortedList<int,string> top3LongestWords = new SortedList<int,string>(3, new DuplicateKeyComparer<int>());   // Remove method on this object won't work if this comparer will be used. RemoveAt method will still work!

                    int numOfAddedElems = 0;
                    foreach (string word in Regex.Split((string)cell.Value, "\\s+", RegexOptions.Multiline))
                    {
                        if (numOfAddedElems < 3 ) {
                            top3LongestWords.Add(word.Length, word);
                        }
                        else
                        {
                            if (top3LongestWords.Keys[0] < word.Length)
                            {
                                top3LongestWords.RemoveAt(0);
                                top3LongestWords.Add(word.Length, word);
                            }
                        }
                        numOfAddedElems++;
                    }
                    contentWidth = 0;
                    foreach (string word in top3LongestWords.Values)
                    {
                        int wordWidth = TextRenderer.MeasureText(word, dgvChat.Font).Width;
                        if (contentWidth < wordWidth)
                        {
                            contentWidth = wordWidth;
                        }
                    }
                }
                if (contentWidth > cell.Size.Width)
                {
                    //metroToolTip1.SetToolTip(dgvChat, splitStringOnMultiline((string)dgvChat[e.ColumnIndex, e.RowIndex].Value));
                    metroToolTip1.Show(splitStringOnMultiline(((string)cell.Value).Replace('\f', '\n').Trim()), this, Cursor.Position.X - this.PointToScreen(this.Location).X, Cursor.Position.Y - this.PointToScreen(this.Location).Y);
                }
            }
        }


        /// <summary>
        /// Comparer for comparing two keys, handling equality as beeing greater
        /// Use this Comparer e.g. with SortedLists or SortedDictionaries, that don't allow duplicate keys
        /// </summary>
        /// <typeparam name="TKey"></typeparam>
        public class DuplicateKeyComparer<TKey> : IComparer<TKey>
            where TKey : IComparable
        {
            public int Compare(TKey x, TKey y)
            {
                int result = x.CompareTo(y);

                if (result == 0)
                    return 1;   // Handle equality as beeing greater
                else
                    return result;
            }
        }

        private string splitStringOnMultiline(string s)
        {
            int maxNumOfCharsPerLine = 60;
            s = s.TrimStart();
            Regex regex = new Regex(String.Format("^(.{{0,{0}}})(\\s+|$)", maxNumOfCharsPerLine), RegexOptions.Multiline);
            int startingIndex = 0;
            int strLength = s.Length;
            StringBuilder sb = new StringBuilder();
            while (startingIndex < strLength)
            {
                Match m = regex.Match(s, startingIndex);
                if (m.Success)
                {
                    sb.AppendLine(m.Groups[1].Value);
                    startingIndex += m.Length;
                }
                else
                {
                    sb.Append(s.Substring(0, maxNumOfCharsPerLine)).AppendLine("-");
                    startingIndex += maxNumOfCharsPerLine;
                }
            }
            return sb.ToString();
        }

        private void dgvChat_CellMouseLeave(object sender, DataGridViewCellEventArgs e)
        {
            metroToolTip1.Hide(this);
        }

        private const int GWL_EXSTYLE = -20;
        private const int WS_EX_LAYERED = 0x80000;
        private const int LWA_ALPHA = 0x2;
        private const int LWA_COLORKEY = 0x1;

        [DllImport("user32.dll")]
        static extern bool SetLayeredWindowAttributes(IntPtr hwnd, uint crKey, byte bAlpha, uint dwFlags);
        [DllImport("user32.dll", SetLastError = true)]
        static extern int GetWindowLong(IntPtr hWnd, int nIndex);
        [DllImport("user32.dll")]
        static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);


        private void metroToolTip1_Popup(object sender, PopupEventArgs e)
        {
            //e.ToolTipSize = new Size(200, 100);
            var window = typeof(ToolTip).GetField("window", BindingFlags.Instance | BindingFlags.NonPublic).GetValue(metroToolTip1) as NativeWindow;

            var Handle = window.Handle;
            SetWindowLong(Handle, GWL_EXSTYLE, GetWindowLong(Handle, GWL_EXSTYLE) ^ WS_EX_LAYERED);
            SetLayeredWindowAttributes(Handle, 0, 128, LWA_ALPHA);
        }

        private void metroToolTip1_Draw(object sender, DrawToolTipEventArgs e)
        {
            e.Graphics.FillRectangle(new SolidBrush(metroToolTip1.BackColor), e.Bounds);
            e.Graphics.DrawString(e.ToolTipText, e.Font, Brushes.Black, e.Bounds);
        }
        */
    }
}
