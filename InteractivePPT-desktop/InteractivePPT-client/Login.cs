using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using InteractivePPT_client;
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
        }
        

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (webBrowser1.Url.ToString() == "https://www.facebook.com/")
            {
                timer1.Stop();
                webBrowser1.Navigate("https://www.facebook.com/settings");
                timer3.Start();
            }
            
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            timer2.Stop();
            timer1.Start();
        }

        private void timer3_Tick(object sender, EventArgs e)
        {
            timer3.Stop();
            foreach (HtmlElement element in webBrowser1.Document.All)
            {
                if (element.GetAttribute("classname") == "fbSettingsListItemContent fcg")
                {
                    userInfo.Add(element.InnerText);
                }
            }
            user.name = userInfo[0];
            user.userName = userInfo[1];
            user.contact = userInfo[2];
            string[] theCookies = System.IO.Directory.GetFiles(Environment.GetFolderPath(Environment.SpecialFolder.Cookies));

            foreach (string currentFile in theCookies)

            {

                try

                {

                    System.IO.File.Delete(currentFile);

                }

                catch (Exception ex) { MessageBox.Show(ex.ToString()); }

            }
            Home h = new Home(user);
            h.WindowState = FormWindowState.Maximized;
            h.Show();
            this.Close();
            f.Hide();
        }

        

        private void Login_Deactivate(object sender, EventArgs e)
        {
            f.Enabled = true;
        }
    }
}
