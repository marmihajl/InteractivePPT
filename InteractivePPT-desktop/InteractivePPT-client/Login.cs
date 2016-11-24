using System;
using System.Collections.Generic;
using System.Windows.Forms;
using InteractivePPT_client;
using System.Threading;
using System.Text.RegularExpressions;
using System.Net;
using System.Collections.Specialized;

namespace InteractivePPT
{
    public partial class Login : Form
    {
        Form1 f;
        User user = new User();
        List<string> userInfo = new List<string>();
        public Login(Form1 forma)
        {
            InitializeComponent();
            f = forma;

            webBrowser1.DocumentCompleted += WebBrowser1_DocumentCompleted;
        }

        private void WebBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            if (new Regex(@"^(https://((mobile)|(m))\.facebook\.com/home\.php).*$").IsMatch(webBrowser1.Url.ToString()))
            {
                webBrowser1.Visible = false;
                loadingPicture.Visible = true;
                webBrowser1.Navigate("https://web.facebook.com/settings?tab=applications");
            }
            else if (new Regex(@"^(https://((www)|(web))\.facebook\.com/settings\?tab=applications)$").IsMatch(webBrowser1.Url.ToString()))
            {
                timer1.Start();
            }
        }

        /// <summary>
        /// Selects mobile version of this application (if it has been used) after App Settings page on Facebook is fullfilled with user's used apps which come additionally via AJAX response after basic skeleton of page is being loaded
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer1_Tick(object sender, EventArgs e)
        {
            timer1.Stop();
            HtmlElement mobileAppThumbnail = webBrowser1.Document.GetElementById("app-id-370728603269382");
            if (mobileAppThumbnail == null)
            {
                DeleteCookiesOfIntegratedWebBrowser();
                MessageBox.Show("It seems like you've never used mobile version of this application before! Application will now shut down..");
                Application.Exit();
                return;
            }
            else
            {
                foreach (HtmlElement curElement in mobileAppThumbnail.All)
                {
                    if (curElement.GetAttribute("className") == "_4n9u ellipsis")
                    {
                        curElement.Parent.InvokeMember("click");
                        timer2.Start();
                        break;
                    }
                }
            }
        }

        /// <summary>
        /// Extracts UserID of mobile version of this application on its dialog in App Settings on Facebook after dialog is successfully loaded (it loads after AJAX response with its data is received)
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer2_Tick(object sender, EventArgs e)
        {
            timer2.Stop();
            Regex regex = new Regex(@"^(Your .+ User ID is (\d+)\. If you contact the developer of this app for support, they may need this number to help better address your question or concern\. <a href=""/help/149151751822041"" target=""_blank"">Learn More</a>)$");
            foreach (HtmlElement curElement in webBrowser1.Document.All)
            {
                if (curElement.GetAttribute("className") == "_s")
                {
                    //gets third child, then its last one and then last one of that one
                    string appUid = regex.Match(curElement.Children[2].Children[curElement.Children[2].Children.Count - 1].Children[curElement.Children[2].Children[curElement.Children[2].Children.Count - 1].Children.Count - 1].InnerHtml).Groups[2].Value.ToString();

                    using (WebClient client = new WebClient())
                    {
                        try
                        {
                            byte[] response =
                            client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                            {
                               { "request_type", "get_user_info" },
                               { "app_uid", appUid }
                            });

                            user.uid = appUid;
                            user.name = System.Text.Encoding.UTF8.GetString(response);
                        }
                        catch
                        {
                            DeleteCookiesOfIntegratedWebBrowser();
                            MessageBox.Show("Communication with server-side of this application could not be established! Application will now shut down..");
                            Application.Exit();
                            return;
                        }

                    }

                    DeleteCookiesOfIntegratedWebBrowser();

                    Home h = new Home(user);
                    h.WindowState = FormWindowState.Maximized;
                    h.Show();
                    this.Close();
                    f.Hide();

                    break;
                }
            }
        }

        private void Login_Deactivate(object sender, EventArgs e)
        {
            f.Enabled = true;
        }

        private void DeleteCookiesOfIntegratedWebBrowser()
        {
            string[] theCookies = System.IO.Directory.GetFiles(Environment.GetFolderPath(Environment.SpecialFolder.Cookies));

            foreach (string currentFile in theCookies)
            {
                try
                {
                    System.IO.File.Delete(currentFile);
                }
                catch (Exception ex) {
                    MessageBox.Show(ex.ToString());
                }
            }
        }
    }
}
