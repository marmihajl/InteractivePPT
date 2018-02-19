using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.Net;
using System.Collections.Specialized;
using System.Globalization;
using System.Threading;

namespace InteractivePPT
{
    public partial class Login : MetroFramework.Forms.MetroForm
    {
        User user = new User();
        List<string> userInfo = new List<string>();
        private const string mobileAppId = "app-id-370728603269382";
        private int numOfTicksTimer1 = 0;

        public Login()
        {
            InitializeComponent();

            webBrowser1.DocumentCompleted += WebBrowser1_DocumentCompleted;

            Dictionary<string, string> languages =  new Dictionary<string, string>()
            {
                { "en-US", "english" },
                { "hr-HR", "hrvatski (croatian)" }
            };

            languageSelectBox.ValueMember = "Key";
            languageSelectBox.DisplayMember = "Value";
            languageSelectBox.DataSource = new BindingSource(languages, null);

            if (languages.ContainsKey(CultureInfo.CurrentUICulture.Name))
            {
                languageSelectBox.SelectedValue = CultureInfo.CurrentUICulture.Name;
            }
        }

        private void WebBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            if (new Regex(@"^https://(mobile|m)\.facebook\.com/home\.php").IsMatch(webBrowser1.Url.ToString()))
            {
                webBrowser1.Navigate("https://web.facebook.com/settings?tab=applications");
            }
            else if (new Regex(@"^https://(www|web)\.facebook\.com/settings\?tab=applications$").IsMatch(webBrowser1.Url.ToString()))
            {
                timer1.Start();
            }
            else if (new Regex(@"^https://(mobile|m)\.facebook\.com/login/save-device/").IsMatch(webBrowser1.Url.ToString()))
            {
                webBrowser1.Document.GetElementsByTagName("a")[0].InvokeMember("click");
            }
            else if (new Regex(@"^https://(mobile|m)\.facebook\.com/login/").IsMatch(webBrowser1.Url.ToString()))
            {
                webBrowser1.Visible = true;
                loadingPicture.Visible = false;
                languageSelectBox.Visible = true;
            }
            else
            {
                // perform log-out
                foreach (HtmlElement a in webBrowser1.Document.GetElementsByTagName("a"))
                {
                    if (a.GetAttribute("href").StartsWith("https://m.facebook.com/logout.php"))
                    {
                        webBrowser1.Navigate(a.GetAttribute("href"));
                        return;
                    }
                }
                webBrowser1.Navigate(Resources.strings.fb_login_url);
            }
        }

        /// <summary>
        /// Selects mobile version of this application (if it has been used) after App Settings page on Facebook is fullfilled with user's used apps which come additionally via AJAX response after basic skeleton of page is being loaded
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer1_Tick(object sender, EventArgs e)
        {
            HtmlElement mobileAppThumbnail = webBrowser1.Document.GetElementById(mobileAppId);
            if (mobileAppThumbnail == null)
            {
                numOfTicksTimer1++;
                if (numOfTicksTimer1 == 3)
                {
                    timer1.Stop();
                    DeleteCookiesOfIntegratedWebBrowser();
                    MessageBox.Show(Resources.strings.mobile_app_not_used_before + " " + Resources.strings.application_will_shut_down);
                    Application.Exit();
                }
            }
            else
            {
                timer1.Stop();
                mobileAppThumbnail.GetElementsByTagName("a")[0].InvokeMember("click");
                timer2.Start();
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
            Regex regex = new Regex(@"\d{10,}");
            foreach (HtmlElement curElement in webBrowser1.Document.All)
            {
                if (curElement.GetAttribute("className") == "_s")
                {
                    //gets third child, then its last one and then last one of that one
                    Match regexMatch = regex.Match(curElement.Children[2].Children[curElement.Children[2].Children.Count - 1].Children[curElement.Children[2].Children[curElement.Children[2].Children.Count - 1].Children.Count - 1].InnerHtml);
                    string appUid = regexMatch.Groups[regexMatch.Groups.Count-1].Value.ToString();

                    using (WebClient client = new WebClient())
                    {
                        try
                        {
                            byte[] response =
                            client.UploadValues(Home.mainScriptUri, new NameValueCollection()
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
                            MessageBox.Show(Resources.strings.communication_with_server_not_established + " " + Resources.strings.application_will_shut_down);
                            Application.Exit();
                        }

                    }

                    DeleteCookiesOfIntegratedWebBrowser();

                    MainForm h = new MainForm(user);
                    h.WindowState = FormWindowState.Maximized;
                    h.Show();
                    this.Hide();

                    /*Home h = new Home(user);
                    h.WindowState = FormWindowState.Maximized;
                    h.MdiParent = f;
                    h.Show();
                    this.Close();*/
                    break;
                }
            }
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

        private void webBrowser1_Navigating(object sender, WebBrowserNavigatingEventArgs e)
        {
            webBrowser1.Visible = false;
            loadingPicture.Visible = true;
            languageSelectBox.Visible = false;
        }

        private void languageSelectBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (webBrowser1.Url != null)
            {
                Thread.CurrentThread.CurrentUICulture = CultureInfo.GetCultureInfo((string)languageSelectBox.SelectedValue);
                webBrowser1.Navigate(Resources.strings.fb_login_url);
            }
        }
    }
}
