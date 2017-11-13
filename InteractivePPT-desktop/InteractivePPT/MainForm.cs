using MetroFramework.Forms;
using System;
using System.Windows.Forms;

namespace InteractivePPT
{
    public partial class MainForm : MetroFramework.Forms.MetroForm
    {
        private User user;

        public MainForm(User u)
        {
            InitializeComponent();
            user = u;
        }

        private void Pocetna_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void Pocetna_Load(object sender, EventArgs e)
        {
            Home objForm = new Home(user);
            objForm.TopLevel = false;
            metroPanel1.Controls.Add(objForm);
            objForm.FormBorderStyle = FormBorderStyle.None;
            objForm.Dock = DockStyle.Fill;
            objForm.Show();
        }

        private void Pocetna_Resize(object sender, EventArgs e)
        {
            if (metroPanel1.Controls.Count > 2)
            {
                foreach (Control ctrl in metroPanel1.Controls) {
                    if (ctrl is MetroForm)
                    {
                        ctrl.Size = metroPanel1.Size;
                    }
                }
            }
        }
    }
}
