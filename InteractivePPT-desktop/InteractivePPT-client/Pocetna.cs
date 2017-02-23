using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace InteractivePPT
{
    public partial class Pocetna : MetroFramework.Forms.MetroForm
    {
        private User user;
        public static bool homeOpen = false;

        public Pocetna(User u)
        {
            InitializeComponent();
            user = u;
        }

        private void metroButton1_Click(object sender, EventArgs e)
        {
            if (!homeOpen)
            {
                homeOpen = true;

                try
                {
                    Home objForm = new Home(user, metroPanel1);
                    objForm.TopLevel = false;
                    metroPanel1.Controls.Add(objForm);
                    objForm.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
                    objForm.Dock = DockStyle.Fill;
                    objForm.Show();
                    
                }
                catch
                {

                }

            }
        }

        private void Pocetna_Load(object sender, EventArgs e)
        {
            metroPanel1.Width = this.Width - 196;
            metroPanel1.Height = this.Height - 87;
        }

        private void Pocetna_Resize(object sender, EventArgs e)
        {
            metroPanel1.Width = this.Width - 196;
            metroPanel1.Height = this.Height - 87;
        }

        private void Pocetna_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }
    }
}
