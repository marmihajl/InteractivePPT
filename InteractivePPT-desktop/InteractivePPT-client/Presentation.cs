using Newtonsoft.Json;
using System;
using System.Collections.Specialized;
using System.Net;
using System.Windows.Forms;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
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

        public Presentation(string path, SurveyList surveyList)
        {
            InitializeComponent();
            this.path = path;
            this.surveyList = surveyList;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            PowerPoint.SlideRange ppSR = pptApp.ActiveWindow.Selection.SlideRange;
            PowerPoint.Shape ppSharp = ppSR.Shapes.AddLabel(Microsoft.Office.Core.MsoTextOrientation.msoTextOrientationHorizontal, 0, 0, 200, 25);
            int slide = currentSlide();
            ppSharp.TextEffect.Text = "Hello";
            p.Save();
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

            FileClass.uploadFile(path);
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
            comboBox1.ValueMember = "access_code";
            comboBox1.SelectedIndex = -1;
        }
        

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (comboBox1.SelectedIndex != -1)
            {
                
                string choosedSurvey = comboBox1.SelectedValue.ToString();
                using (WebClient client = new WebClient())
                {
                    try
                    {
                        byte[] response =
                        client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                        {
                       { "request_type", "get_questions" },
                       { "access_code", choosedSurvey }
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
                        }
                        
                    }
                }
            }

        }

        public int currentSlide()
        {
            PowerPoint.Slide slideIndex = pptApp.ActiveWindow.View.Slide;
            return slideIndex.SlideIndex;
        }

        public void addSlide()
        {
            PowerPoint.Slide slide = new PowerPoint.Slide();
            
            slide = p.Slides.AddSlide(currentSlide()+1, p.SlideMaster.CustomLayouts[0]);
        }
    }
}
