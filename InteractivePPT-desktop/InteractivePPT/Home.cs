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
        private User user;
        PowerPoint.Application pptApp = new PowerPoint.Application();
        SurveyList mySurveyList = null;
        public const string serverHostname = "165.227.174.7";   // eg. 31.5.16.51 or interactive-ppt.com
        public const string serverRootDirectoryUri = "http://" + serverHostname + "/";
        public const string mainScriptUri = serverRootDirectoryUri + "interactivePPT-server.php";

        public Home(User u)
        {
            InitializeComponent();
            user = u;
            this.ControlBox = false;
            label1.Text = user.name;
        }

        private void Home_Load(object sender, EventArgs e)
        {
            LoadMyPpts();
        }

        private void LoadMyPpts()
        {
            using (WebClient client = new WebClient())
            {
                client.UploadValuesCompleted += Client_UploadValuesCompleted;
                client.UploadValuesAsync(new Uri(mainScriptUri), new NameValueCollection()
                {
                    { "request_type", "get_surveys" },
                    { "app_uid", user.uid }
                });
            }
        }

        private void Client_UploadValuesCompleted(object sender, UploadValuesCompletedEventArgs e)
        {
            if (e.Cancelled || e.Error != null)
            {
                MessageBox.Show("Communication with server-side of this application could not be established");
            }
            else
            {
                string serializedUserSurveys = System.Text.Encoding.UTF8.GetString(e.Result);
                if (serializedUserSurveys != null)
                {
                    mySurveyList = JsonConvert.DeserializeObject<SurveyList>(serializedUserSurveys);
                }
                mySurveysDgv.Invoke((MethodInvoker)delegate
                {
                    mySurveysDgv.Rows.Clear();
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
                });
            }
            this.Refresh();
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
                    FileUploader.UploadFile(localUriOfFile, user.uid);
                    break;
                case DialogResult.Cancel:
                    return;
            }
        }

        private int GetFileSizeOfRemoteFile(string remoteUriOfFile)
        {
            WebRequest req = HttpWebRequest.Create(remoteUriOfFile);
            req.Method = "HEAD";
            using (WebResponse resp = req.GetResponse())
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

        private void mySurveysDgv_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                using (Form form = new Form())
                {
                    int numOfPixels = Math.Min(Screen.PrimaryScreen.WorkingArea.Height, Screen.PrimaryScreen.WorkingArea.Width);

                    Bitmap img = (new QRCodeWriter()).encode((string)mySurveysDgv["access_code", e.RowIndex].Value, BarcodeFormat.QR_CODE, numOfPixels, numOfPixels).ToBitmap();

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

        private void OpenSelectedPpt()
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
                    string remoteUriOfFile = (string)mySurveysDgv.SelectedRows[0].Cells["link_to_presentation"].Value;
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
                                client.UploadValues(mainScriptUri, new NameValueCollection()
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

                    Presentation objForm = new Presentation(localUriOfFile, mySurveyList.data.Where(x => x.access_code == (string)mySurveysDgv.SelectedRows[0].Cells["access_code"].Value && x.id != null).ToList(), user.uid);
                    objForm.TopLevel = false;
                    MetroPanel panel = (MetroPanel)this.Parent;
                    panel.Controls.Add(objForm);
                    panel.Controls.SetChildIndex(objForm, 2);
                    objForm.FormBorderStyle = FormBorderStyle.None;
                    objForm.Dock = DockStyle.Fill;
                    objForm.Show();
                }
            }
            catch
            {
            }
        }

        private void openPptButton_Click(object sender, EventArgs e)
        {
            OpenSelectedPpt();
        }

        private void mySurveysDgv_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex >= 0 && e.ColumnIndex != 3)
            {
                OpenSelectedPpt();
            }
        }

        private void refreshListButton_Click(object sender, EventArgs e)
        {
            LoadMyPpts();
        }
    }
}
