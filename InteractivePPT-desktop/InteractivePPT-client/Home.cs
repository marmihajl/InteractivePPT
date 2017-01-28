using System;
using System.Drawing;
using System.Net;
using System.Windows.Forms;
using System.Collections.Specialized;
using Newtonsoft.Json;
using com.google.zxing;
using com.google.zxing.qrcode;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using System.Linq;

namespace InteractivePPT
{
    public partial class Home : Form
    {
        private User user;
        PowerPoint.Application pptApp = new PowerPoint.Application();
        static string path;
        SurveyList mySurveyList = null;
        private const string serverRootDirectoryUri = "http://46.101.68.86/";

        public Home(User u)
        {
            InitializeComponent();
            user = u;
        }

        private void Home_Load(object sender, EventArgs e)
        {
            string serializedUserSurveys = null;
            

            label1.Text = user.name;

            using (WebClient client = new WebClient())
            {
                try
                {
                    byte[] response =
                    client.UploadValues(serverRootDirectoryUri + "interactivePPT-server.php", new NameValueCollection()
                    {
                       { "request_type", "get_surveys" },
                       { "app_uid", user.uid }
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
                mySurveyList = JsonConvert.DeserializeObject<SurveyList>(serializedUserSurveys);
            }

            foreach (Survey survey in mySurveyList.data.GroupBy(x => x.access_code).Select(x => x.First()))
            {
                int endPosOfPptName = survey.link_to_presentation.LastIndexOf(".ppt");
                if (endPosOfPptName == -1)
                {
                    endPosOfPptName = survey.link_to_presentation.LastIndexOf(".pptx");
                }
                mySurveysDgv.Rows.Add(
                    survey.link_to_presentation.Substring(4, endPosOfPptName),
                    survey.access_code,
                    serverRootDirectoryUri + survey.link_to_presentation,
                    (new QRCodeWriter()).encode(survey.access_code, BarcodeFormat.QR_CODE, 50, 50).ToBitmap()
                );
            }


        }

        private void Home_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void mySurveysDgv_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                using (Form form = new Form())
                {
                    int numOfPixels = Math.Min(Screen.PrimaryScreen.WorkingArea.Height, Screen.PrimaryScreen.WorkingArea.Width);

                    Bitmap img = (new QRCodeWriter()).encode(mySurveysDgv["access_code", e.RowIndex].Value.ToString(), BarcodeFormat.QR_CODE, numOfPixels, numOfPixels).ToBitmap();

                    form.StartPosition = FormStartPosition.CenterScreen;
                    form.Size = img.Size;

                    PictureBox pb = new PictureBox();
                    pb.Dock = DockStyle.Fill;
                    pb.Image = img;

                    form.Controls.Add(pb);
                    form.ShowDialog();
                }
            }
        }

        private void mySurveysDgv_CellMouseEnter(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                Cursor = Cursors.Hand;
            }
        }

        private void mySurveysDgv_CellMouseLeave(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                Cursor = Cursors.Default;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                OpenFileDialog ofd = new OpenFileDialog();
                ofd.ShowDialog();
                if (ofd.FileName != null)
                {
                    path = ofd.FileName;
                    FileClass.uploadFile(path, user.uid);

                    Presentation p = new Presentation(path, mySurveyList, user.uid);
                    p.Show();

                }
            }
            catch
            {

            }
        }

    }
}
