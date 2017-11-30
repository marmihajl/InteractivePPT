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
using System.Runtime.InteropServices;
using System.Linq;
using System.Globalization;

namespace InteractivePPT
{
    public partial class Presentation : MetroFramework.Forms.MetroForm
    {
        PowerPoint.Application pptApp = new PowerPoint.Application();
        string path;
        PowerPoint.Presentation p = null;
        List<Survey> surveyList = null;
        QuestionList myQuestionList = null;
        int move = 1;
        string userUid;
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

            SortedDictionary<int, string> chartInsertionPositions = new SortedDictionary<int, string>();
            chartInsertionPositions.Add(1, "sljedeći slajd");
            chartInsertionPositions.Add(2, "tag: naziv pitanja");
            graphPosition.DataSource = new BindingSource(chartInsertionPositions, null);
            graphPosition.DisplayMember = "Value";
            graphPosition.ValueMember = "Key";

            groupRemainingOptsCB.Visible = false; // as default value of numOfGroupsNUD element is 0

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
                if (!ushort.TryParse(Encoding.UTF8.GetString(response), out port))
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
                responseData = Encoding.UTF8.GetString(data, 0, bytes).TrimEnd('\n').Replace("\f", "\n");
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

        private void AddSlide(List<Answer> answers, string question)
        {
            PowerPoint.Slides slides;
            PowerPoint._Slide slide;
            slides = p.Slides;

            if ((int)graphPosition.SelectedValue == 1)
            {
                slideIndex = GetIndexOfCurrentSlide() + move++;
            }
            else
            {
                int numOfSlides = p.Slides.Count;
                XlChartType chartType;
                if (radioPie.Checked)
                {
                    chartType = XlChartType.xlPie;
                }
                else if (radioLine.Checked)
                {
                    chartType = XlChartType.xlLine;
                }
                else
                {
                    chartType = XlChartType.xlBarClustered;
                }
                slideIndex = 0;
                for (int i = 0; i < numOfSlides && slideIndex == 0; i++)
                {
                    foreach (var it in p.Slides[i + 1].Shapes)
                    {
                        var shape = (PowerPoint.Shape)it;
                        try
                        {
                            if (question == shape.Chart.ChartTitle.Text && shape.Chart.ChartType == chartType)
                            {
                                slideIndex = i + 1;
                                break;
                            }
                        }
                        catch
                        {
                        }
                    }
                }
            }
            
            if (slideIndex == 0)
            {
                DisplayWindowInForeground(this.Handle);
                MessageBox.Show(String.Format("Za sada na prezentaciji ne postoji graf s pitanjem \"{0}\". Pozicionirajte se na slajd iza kojeg želite dodati slajd s grafikonom, odaberite u aplikaciji opciju \"sljedeći slajd\" za mjesto grafa te ponovno dodajte graf", question), "Ne postoji grafikon za odabrano pitanje u prezentaciji");
                throw new Exception();
            }
            slide = slides.AddSlide(slideIndex, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);
            //var chart = slide.Shapes.AddChart(XlChartType.xlBarClustered, 10f, 10f, 900f, 400f).Chart;
            PowerPoint.Chart chart = null;

            if (radioBar.Checked == true) {
                chart = slide.Shapes.AddChart(XlChartType.xlBarClustered, 10f, 10f, 900f, 400f).Chart;
                chart.HasLegend = false;
            }

            else if (radioLine.Checked == true) {
                chart = slide.Shapes.AddChart(XlChartType.xlLine, 10f, 10f, 900f, 400f).Chart;
                chart.HasLegend = true;
            }

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
            string lastColumnName;

            int numOfGroupsToShow = decimal.ToInt32(numOfGroupsNUD.Value);
            bool groupRemainingOpts;
            if (groupRemainingOptsCB.Visible)
            {
                groupRemainingOpts = groupRemainingOptsCB.Checked;
            }
            else
            {
                groupRemainingOpts = false;
            }
            if (radioLine.Checked)
            {
                if (answers.Any())
                {
                    bool timeComponentOnly;
                    List<string> usedOptionNames = answers.Select(x => x.choice_name).Distinct().ToList();
                    Dictionary<string, int> answersNumPerOption = new Dictionary<string, int>();
                    List<string> columnNames = new List<string>();
                    int optionNum = 1;
                    if (numOfGroupsToShow == 0)
                    {
                        foreach (string optionName in usedOptionNames)
                        {
                            string columnName = GetExcelColumnName(optionNum + 1);
                            dataSheet.Cells.Range[columnName + index].Value2 = "'" + optionName;
                            columnNames.Add(columnName);
                            answersNumPerOption[optionName] = 0;
                            optionNum++;
                        }
                        index++;
                        List<string>.Enumerator columnNamesEnumerator;
                        DateTime previousDateTime = ConvertUnixTimeStampToDateTime(answers.First().num);
                        DateTime previousDate = previousDateTime.Date;
                        timeComponentOnly = answers.All(x => ConvertUnixTimeStampToDateTime(x.num).Date == previousDate);
                        if (timeComponentOnly)
                        {
                            TimeSpan previousTime = previousDateTime.TimeOfDay;

                            dataSheet.Cells.Range["A" + index].Value2 = new DateTime(previousTime.Ticks).AddMinutes(-1).ToOADate();
                            foreach (string columnName in columnNames)
                            {
                                dataSheet.Cells.Range[columnName + index].Value2 = 0;
                            }
                            index++;
                            foreach (Answer a in answers)
                            {
                                DateTime answerDateTime = ConvertUnixTimeStampToDateTime(a.num);
                                if (previousTime != answerDateTime.TimeOfDay)
                                {
                                    dataSheet.Cells.Range["A" + index].Value2 = new DateTime(previousTime.Ticks).ToOADate();
                                    columnNamesEnumerator = columnNames.GetEnumerator();
                                    foreach (int answerNum in answersNumPerOption.Values)
                                    {
                                        columnNamesEnumerator.MoveNext();
                                        dataSheet.Cells.Range[columnNamesEnumerator.Current + index].Value2 = answerNum;
                                    }
                                    index++;
                                    previousTime = answerDateTime.TimeOfDay;
                                }
                                answersNumPerOption[a.choice_name] = answersNumPerOption[a.choice_name] + 1;
                            }
                            dataSheet.Cells.Range["A" + index].Value2 = new DateTime(previousTime.Ticks).ToOADate();
                        }
                        else
                        {
                            dataSheet.Cells.Range["A" + index].Value2 = previousDate.ToOADate();
                            foreach (string columnName in columnNames)
                            {
                                dataSheet.Cells.Range[columnName + index].Value2 = 0;
                            }
                            index++;
                            foreach (Answer a in answers)
                            {
                                DateTime answerDateTime = ConvertUnixTimeStampToDateTime(a.num);
                                if (previousDate != answerDateTime.Date)
                                {
                                    dataSheet.Cells.Range["A" + index].Value2 = previousDate.ToOADate();
                                    columnNamesEnumerator = columnNames.GetEnumerator();
                                    foreach (int answerNum in answersNumPerOption.Values)
                                    {
                                        columnNamesEnumerator.MoveNext();
                                        dataSheet.Cells.Range[columnNamesEnumerator.Current + index].Value2 = answerNum;
                                    }
                                    index++;
                                    previousDate = answerDateTime.Date;
                                }
                                answersNumPerOption[a.choice_name] = answersNumPerOption[a.choice_name] + 1;
                            }
                            dataSheet.Cells.Range["A" + index].Value2 = previousDate.ToOADate();
                        }
                        columnNamesEnumerator = columnNames.GetEnumerator();
                        foreach (int answerNum in answersNumPerOption.Values)
                        {
                            columnNamesEnumerator.MoveNext();
                            dataSheet.Cells.Range[columnNamesEnumerator.Current + index].Value2 = answerNum;
                        }
                        index++;

                        lastColumnName = columnNames.Last();
                    }
                    else
                    {
                        int realNumOfGroups = usedOptionNames.Count;
                        if (realNumOfGroups < numOfGroupsToShow)
                        {
                            numOfGroupsToShow = realNumOfGroups;
                        }
                        foreach (string optionName in usedOptionNames)
                        {
                            answersNumPerOption[optionName] = 0;
                        }
                        answers.ForEach(x => answersNumPerOption[x.choice_name] = answersNumPerOption[x.choice_name] + 1);
                        List<string> sortedOptionNamesByOccurrences = answersNumPerOption.OrderByDescending(x => x.Value).Select(x => x.Key).ToList();

                        answersNumPerOption.Clear();
                        usedOptionNames.Clear();

                        foreach (string optionName in sortedOptionNamesByOccurrences.Take(numOfGroupsToShow))
                        {
                            string columnName = GetExcelColumnName(optionNum + 1);
                            dataSheet.Cells.Range[columnName + index].Value2 = "'" + optionName;
                            columnNames.Add(columnName);
                            answersNumPerOption.Add(optionName, 0);
                            usedOptionNames.Add(optionName);
                            optionNum++;
                        }
                        if (groupRemainingOpts)
                        {
                            string columnName = GetExcelColumnName(optionNum + 1);
                            dataSheet.Cells.Range[columnName + index].Value2 = "'" + "Ostalo";
                            columnNames.Add(columnName);
                            answersNumPerOption.Add("", 0); // sum of occurrences of other options will be in dictionary as entry with key "" (empty string) since regular option cannot be empty
                            optionNum++;
                        }
                        index++;

                        List<string>.Enumerator columnNamesEnumerator;
                        DateTime previousDateTime = ConvertUnixTimeStampToDateTime(answers.First().num);
                        DateTime previousDate = previousDateTime.Date;
                        timeComponentOnly = answers.All(x => ConvertUnixTimeStampToDateTime(x.num).Date == previousDate);
                        if (timeComponentOnly)
                        {
                            TimeSpan previousTime = previousDateTime.TimeOfDay;

                            dataSheet.Cells.Range["A" + index].Value2 = new DateTime(previousTime.Ticks).AddMinutes(-1).ToOADate();
                            foreach (string columnName in columnNames)
                            {
                                dataSheet.Cells.Range[columnName + index].Value2 = 0;
                            }
                            index++;
                            foreach (Answer a in answers)
                            {
                                DateTime answerDateTime = ConvertUnixTimeStampToDateTime(a.num);
                                if (previousTime != answerDateTime.TimeOfDay)
                                {
                                    dataSheet.Cells.Range["A" + index].Value2 = new DateTime(previousTime.Ticks).ToOADate();
                                    columnNamesEnumerator = columnNames.GetEnumerator();
                                    foreach (int answerNum in answersNumPerOption.Values)
                                    {
                                        columnNamesEnumerator.MoveNext();
                                        dataSheet.Cells.Range[columnNamesEnumerator.Current + index].Value2 = answerNum;
                                    }
                                    index++;
                                    previousTime = answerDateTime.TimeOfDay;
                                }
                                if (answersNumPerOption.ContainsKey(a.choice_name))
                                {
                                    answersNumPerOption[a.choice_name] = answersNumPerOption[a.choice_name] + 1;
                                }
                                else
                                {
                                    if (groupRemainingOpts)
                                    {
                                        answersNumPerOption[""] = answersNumPerOption[""] + 1;
                                    }
                                }
                            }
                            dataSheet.Cells.Range["A" + index].Value2 = new DateTime(previousTime.Ticks).ToOADate();
                        }
                        else
                        {
                            dataSheet.Cells.Range["A" + index].Value2 = previousDate.ToOADate();
                            foreach (string columnName in columnNames)
                            {
                                dataSheet.Cells.Range[columnName + index].Value2 = 0;
                            }
                            index++;
                            foreach (Answer a in answers)
                            {
                                DateTime answerDateTime = ConvertUnixTimeStampToDateTime(a.num);
                                if (previousDate != answerDateTime.Date)
                                {
                                    dataSheet.Cells.Range["A" + index].Value2 = previousDate.ToOADate();
                                    columnNamesEnumerator = columnNames.GetEnumerator();
                                    foreach (int answerNum in answersNumPerOption.Values)
                                    {
                                        columnNamesEnumerator.MoveNext();
                                        dataSheet.Cells.Range[columnNamesEnumerator.Current + index].Value2 = answerNum;
                                    }
                                    index++;
                                    previousDate = answerDateTime.Date;
                                }
                                if (answersNumPerOption.ContainsKey(a.choice_name))
                                {
                                    answersNumPerOption[a.choice_name] = answersNumPerOption[a.choice_name] + 1;
                                }
                                else
                                {
                                    if (groupRemainingOpts)
                                    {
                                        answersNumPerOption[""] = answersNumPerOption[""] + 1;
                                    }
                                }
                            }
                            dataSheet.Cells.Range["A" + index].Value2 = previousDate.ToOADate();
                        }

                        columnNamesEnumerator = columnNames.GetEnumerator();
                        foreach (int answerNum in answersNumPerOption.Values)
                        {
                            columnNamesEnumerator.MoveNext();
                            dataSheet.Cells.Range[columnNamesEnumerator.Current + index].Value2 = answerNum;
                        }
                        index++;

                        lastColumnName = columnNames.Last();
                    }
                    dataSheet.Cells.Range["A:A"].NumberFormat = (timeComponentOnly ? CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern : CultureInfo.CurrentCulture.DateTimeFormat.ShortDatePattern.TrimEnd('.'));
                }
                else
                {
                    lastColumnName = "A";
                }
            }
            else
            {
                dataSheet.Cells.Range["A" + index].Value2 = "";
                dataSheet.Cells.Range["B" + index].Value2 = "";
                index++;
                int numOfGroups = answers.Count;
                if (numOfGroupsToShow == 0 || numOfGroupsToShow > numOfGroups)
                {
                    numOfGroupsToShow = numOfGroups;
                }
                for (int i=0; i<numOfGroupsToShow; i++)
                {
                    Answer a = answers[i];
                    dataSheet.Cells.Range["A" + index].Value2 = "'" + a.choice_name;
                    dataSheet.Cells.Range["B" + index].Value2 = a.num;
                    index++;
                }
                if (groupRemainingOpts) {
                    int sumOfOtherOptionsOccurrences = 0;
                    for (int i = numOfGroupsToShow; i<numOfGroups; i++)
                    {
                        sumOfOtherOptionsOccurrences += answers[i].num;
                    }
                    dataSheet.Cells.Range["A" + index].Value2 = "'" + "Ostalo";
                    dataSheet.Cells.Range["B" + index].Value2 = sumOfOtherOptionsOccurrences;
                    index++;
                }
                lastColumnName = "B";
            }

            chart.SetSourceData(String.Format("'{0}'!A1:{1}", dataSheet.Name, lastColumnName + (index - 1)), EXCEL.XlRowCol.xlColumns);

            chart.HasTitle = true;
            chart.ChartTitle.Font.Italic = true;
            chart.ChartTitle.Text = question;
            chart.ChartTitle.Font.Size = 12;
            chart.ChartTitle.Font.Color = Color.Black.ToArgb();

            chart.Refresh();
            workbook.Close();

            if ((int)graphPosition.SelectedValue == 2)
            {
                p.Slides[slideIndex + 1].Delete();
            }
            
            p.Save();

            foreach (Process clsProcess in Process.GetProcesses())
                if (clsProcess.ProcessName.Equals("EXCEL"))  
                    clsProcess.Kill();
        }

        private DateTime ConvertUnixTimeStampToDateTime(double unixTimeStamp)
        {
            // Unix timestamp is seconds past epoch
            System.DateTime dtDateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp).ToLocalTime();
            return dtDateTime;
        }

        private string GetExcelColumnName(int columnNumber)
        {
            int dividend = columnNumber;
            string columnName = String.Empty;
            int modulo;

            while (dividend > 0)
            {
                modulo = (dividend - 1) % 26;
                columnName = Convert.ToChar(65 + modulo).ToString() + columnName;
                dividend = (int)((dividend - modulo) / 26);
            }

            return columnName;
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
                    MessageBox.Show("An error has occurred while trying to override previous presentation on remote server");
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

        public void AddTextSlide(string questionName, List<string> appropriateChoiceNames)
        {
            PowerPoint.Slides slides;
            PowerPoint._Slide slide;
            slides = p.Slides;

            if ((int)graphPosition.SelectedValue == 1)
            {
                slideIndex = GetIndexOfCurrentSlide() + move++;
            }
            else
            {
                int numOfSlides = p.Slides.Count;
                slideIndex = 0;
                for (int i = 0; i < numOfSlides && slideIndex == 0; i++)
                {
                    slide = p.Slides[i];
                    try
                    {
                        if (questionName == slide.Shapes[1].TextFrame.TextRange.Text)
                        {
                            slideIndex = i + 1;
                            break;
                        }
                    }
                    catch
                    {
                    }
                }
            }

            if (slideIndex == 0)
            {
                DisplayWindowInForeground(this.Handle);
                MessageBox.Show(String.Format("Za sada na prezentaciji ne postoji slajd s komentarima na pitanje \"{0}\". Pozicionirajte se na slajd iza kojeg želite dodati slajd s komentarima, odaberite u aplikaciji opciju \"sljedeći slajd\" za mjesto komentara te ponovno dodajte komentare", questionName), "Ne postoji odabrano pitanje s komentarima u prezentaciji");
                throw new Exception();
            }
            slide = slides.AddSlide(GetIndexOfCurrentSlide() + move++, p.SlideMaster.CustomLayouts[PowerPoint.PpSlideLayout.ppLayoutText]);

            slide.Shapes[1].TextFrame.TextRange.Text = questionName;
            slide.Shapes[2].TextFrame.TextRange.Text = string.Join("\n", appropriateChoiceNames);

            if ((int)graphPosition.SelectedValue == 2)
            {
                p.Slides[slideIndex + 1].Delete();
            }

            p.Save();
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (comboBox1.SelectedIndex != -1)
            {
                string chosenSurvey = comboBox1.SelectedValue.ToString();
                string serializedQuestions = null;
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
                        serializedQuestions = Encoding.UTF8.GetString(response);
                    }
                    catch
                    {
                        MessageBox.Show("Communication with server-side of this application could not be established");
                        return;
                    }
                }

                if (serializedQuestions != null)
                {
                    myQuestionList = JsonConvert.DeserializeObject<QuestionList>(serializedQuestions);
                }

                ((ListBox)questionList).DataSource = myQuestionList.questions;
                ((ListBox)questionListForComments).DataSource = myQuestionList.questions.Where(x => x.Question_type_idQuestion_type == 3).ToList();

                ((ListBox)questionList).DisplayMember = ((ListBox)questionListForComments).DisplayMember = "name";
            }
        }

        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool ShowWindow(IntPtr hWnd, ShowWindowEnum flags);
        
        [DllImport("user32.dll")]
        private static extern int SetForegroundWindow(IntPtr hwnd);

        private enum ShowWindowEnum
        {
            Hide = 0,
            ShowNormal = 1, ShowMinimized = 2, ShowMaximized = 3,
            Maximize = 3, ShowNormalNoActivate = 4, Show = 5,
            Minimize = 6, ShowMinNoActivate = 7, ShowNoActivate = 8,
            Restore = 9, ShowDefault = 10, ForceMinimized = 11
        };

        private void DisplayWindowInForeground(IntPtr handle)
        {
            if (handle == IntPtr.Zero)
            {
                ShowWindow(handle, ShowWindowEnum.Restore);
            }
            SetForegroundWindow(handle);
        }

        private void btnAddGraph_Click(object sender, EventArgs e)
        {
            bool addChart = artifactInsertionTabControl.SelectedTab == tabChart;
            CheckedListBox myQuestionList = addChart ? questionList : questionListForComments;
            if (myQuestionList.CheckedItems.OfType<Question>().Any())
            {
                Dictionary<int, string> questionNamesPerIds = new Dictionary<int, string>();
                bool pptChanged = false;
                if (addChart)
                {
                    DisplayWindowInForeground(new IntPtr(pptApp.HWND));

                    string serializedAnswers = null;
                    NameValueCollection postParameters = new NameValueCollection()
                    {
                        { "request_type", radioLine.Checked ? "get_individual_results" : "get_aggregated_results" }
                    };
                    foreach (Question q in myQuestionList.CheckedItems)
                    {
                        int qId = q.idQuestions;
                        postParameters.Add("questions[]", qId.ToString());
                        questionNamesPerIds[qId] = q.name;
                    }
                    using (WebClient client = new WebClient())
                    {
                        try
                        {
                            byte[] response = client.UploadValues(Home.mainScriptUri, postParameters);
                            serializedAnswers = Encoding.UTF8.GetString(response);
                        }
                        catch
                        {
                            MessageBox.Show("Communication with server-side of this application could not be established");
                            return;
                        }
                    }
                    if (serializedAnswers != null)
                    {
                        try
                        {
                            foreach (var answerListPerQuestion in JsonConvert.DeserializeObject<Dictionary<int, List<Answer>>>(serializedAnswers))
                            {
                                try
                                {
                                    AddSlide(answerListPerQuestion.Value, questionNamesPerIds[answerListPerQuestion.Key]);
                                    pptChanged = true;
                                }
                                catch
                                {
                                }
                            }
                        }
                        catch
                        {
                            return;
                        }
                    }
                }
                else
                {
                    string serializedTextResults = null;
                    NameValueCollection postParameters = new NameValueCollection()
                    {
                        { "request_type", "get_aggregated_results" }
                    };
                    if (myQuestionList.CheckedItems.Count != 0)
                    {
                        foreach (Question q in myQuestionList.CheckedItems)
                        {
                            int qId = q.idQuestions;
                            postParameters.Add("questions[]", qId.ToString());
                            questionNamesPerIds[qId] = q.name;
                        }
                    }
                    else
                    {
                        return;
                    }
                    using (WebClient client = new WebClient())
                    {
                        try
                        {
                            byte[] response = client.UploadValues(Home.mainScriptUri, postParameters);
                            serializedTextResults = Encoding.UTF8.GetString(response);
                        }
                        catch
                        {
                            MessageBox.Show("Communication with server-side of this application could not be established");
                            return;
                        }
                    }
                    if (serializedTextResults != null)
                    {
                        CommentFilter cf = new CommentFilter(JsonConvert.DeserializeObject<SortedDictionary<int, List<Answer>>>(serializedTextResults), questionNamesPerIds);
                        cf.ShowDialog();
                        if (cf.DialogResult == DialogResult.OK)
                        {
                            foreach (var selectedAnswers in cf.selectedAnswersPerSelectedQuestions)
                            {
                                try
                                {
                                    AddTextSlide(questionNamesPerIds[selectedAnswers.Key], selectedAnswers.Value);
                                    pptChanged = true;
                                }
                                catch
                                {
                                }
                            }
                        }
                        else
                        {
                            return;
                        }
                    }
                }
                DisplayWindowInForeground(this.Handle);
                move = 1;
                if (pptChanged)
                {
                    UpdatePresentation();
                }
            }
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

        private void numOfGroupsNUD_ValueChanged(object sender, EventArgs e)
        {
            if (((NumericUpDown)sender).Value == 0)
            {
                groupRemainingOptsCB.Visible = false;
            }
            else
            {
                groupRemainingOptsCB.Visible = true;
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
