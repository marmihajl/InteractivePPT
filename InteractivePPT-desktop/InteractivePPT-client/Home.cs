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
using System.IO;
using System.Security.Cryptography;
using MetroFramework.Controls;

namespace InteractivePPT
{
    public partial class Home : MetroFramework.Forms.MetroForm
    {
        MetroPanel panel;
        private User user;
        PowerPoint.Application pptApp = new PowerPoint.Application();
        SurveyList mySurveyList = null;
        private const string serverRootDirectoryUri = "http://46.101.68.86/";

        public Home(User u, MetroPanel mp)
        {
            InitializeComponent();
            user = u;
            panel = mp;
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
                int startPosOfPptName = survey.link_to_presentation.IndexOf('-') + 1;
                mySurveysDgv.Rows.Add(
                    survey.link_to_presentation.Substring(startPosOfPptName, endPosOfPptName - startPosOfPptName),
                    survey.access_code,
                    serverRootDirectoryUri + survey.link_to_presentation,
                    (new QRCodeWriter()).encode(survey.access_code, BarcodeFormat.QR_CODE, 50, 50).ToBitmap()
                );
            }


        }

        private void Home_FormClosed(object sender, FormClosedEventArgs e)
        {
            Pocetna.homeOpen = false;
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
        

        private void AskUserHowToHandleCollisions(string localUriOfFile, string remoteUriOfFile)
        {
            switch (MessageBox.Show("U odabranom direktoriju već postoji istoimena prezentacija. Odgovorite potvrdno ako želite prebrisati lokalnu s onom udaljenom, negativno ako želite udaljenu prebrisati lokalnom ili odustanite ako ne znate što učiniti", "Moguća postojanost novije verzije prezentacije", MessageBoxButtons.YesNoCancel))
            {
                case DialogResult.Yes:
                    using (WebClient webClient = new WebClient())
                    {
                        webClient.DownloadFile(remoteUriOfFile, localUriOfFile);
                    }
                    break;
                case DialogResult.No:
                    FileClass.uploadFile(localUriOfFile, user.uid);
                    break;
                case DialogResult.Cancel:
                    return;
            }
        }

        private int GetFileSizeOfRemoteFile(string remoteUriOfFile)
        {
            System.Net.WebRequest req = System.Net.HttpWebRequest.Create(remoteUriOfFile);
            req.Method = "HEAD";
            using (System.Net.WebResponse resp = req.GetResponse())
            {
                int ContentLength;
                if (int.TryParse(resp.Headers.Get("Content-Length"), out ContentLength))
                {
                    return ContentLength;
                }
                else
                {
                    return -1;
                }
            }
        }

        private void mySurveysDgv_CellContentClick_1(object sender, DataGridViewCellEventArgs e)
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

        private void openPptButton_Click_1(object sender, EventArgs e)
        {
            if (mySurveysDgv.SelectedRows.Count == 0)
            {
                return;
            }
            try
            {
                FolderBrowserDialog fbd = new FolderBrowserDialog();
                if (fbd.ShowDialog() == DialogResult.OK)
                {
                    string remoteUriOfFile = mySurveysDgv.SelectedRows[0].Cells["link_to_presentation"].Value.ToString();
                    string localUriOfFile = fbd.SelectedPath + '\\' + remoteUriOfFile.Substring(remoteUriOfFile.LastIndexOf("/") + 1);
                    if (localUriOfFile.Length > 255)
                    {
                        MessageBox.Show("Odaberite kraću putanju jer broj znakova odabrane putanje (uključujući i sâm naziv datoteke koja bi trebala biti pohranjena u odabrani direktorij) prelazi 255 znakova!");
                        return;
                    }
                    if (File.Exists(localUriOfFile))
                    {
                        if (GetFileSizeOfRemoteFile(remoteUriOfFile) != new FileInfo(localUriOfFile).Length)
                        {
                            AskUserHowToHandleCollisions(localUriOfFile, remoteUriOfFile);
                        }
                        else
                        {
                            string remoteFileChecksum;
                            using (WebClient client = new WebClient())
                            {
                                byte[] response =
                                client.UploadValues(serverRootDirectoryUri + "interactivePPT-server.php", new NameValueCollection()
                                {
                                    { "request_type", "get_file_checksum" },
                                    { "path",  "ppt/" + remoteUriOfFile.Substring(remoteUriOfFile.LastIndexOf('/')+1) }
                                });
                                remoteFileChecksum = System.Text.Encoding.UTF8.GetString(response);
                            }
                            using (var md5 = MD5.Create())
                            using (var stream = File.OpenRead(localUriOfFile))
                            {
                                if (BitConverter.ToString(md5.ComputeHash(stream)).Replace("-", String.Empty).ToLower() != remoteFileChecksum)
                                {
                                    AskUserHowToHandleCollisions(localUriOfFile, remoteUriOfFile);
                                }
                            }
                        }
                    }
                    else
                    {
                        using (WebClient webClient = new WebClient())
                        {
                            webClient.DownloadFile(remoteUriOfFile, localUriOfFile);
                        }
                    }
                    Pocetna.homeOpen = false;

                    try
                    {
                        this.Hide();
                        Presentation objForm = new Presentation(localUriOfFile, mySurveyList.data.Where(x => x.access_code == mySurveysDgv.SelectedRows[0].Cells["access_code"].Value.ToString() && x.id != null).ToList(), user.uid);
                        objForm.TopLevel = false;
                        panel.Controls.Add(objForm);
                        objForm.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
                        objForm.Dock = DockStyle.Fill;
                        objForm.Show();
                        
                    }
                    catch
                    {

                    }
                    /* Presentation p = new Presentation(localUriOfFile, mySurveyList.data.Where(x => x.access_code == mySurveysDgv.SelectedRows[0].Cells["access_code"].Value.ToString() && x.id != null).ToList(), user.uid);
                     p.Show();*/
                }
            }
            catch (Exception ex)
            {
                ;
            }
        }
    }
}
