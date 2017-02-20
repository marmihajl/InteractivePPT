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
        public Pocetna(User u)
        {
            InitializeComponent();
            user = u;
        }

        private void metroButton1_Click(object sender, EventArgs e)
        {
            try
            {
                metroPanel1.Controls.Clear();
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

        private void Pocetna_Load(object sender, EventArgs e)
        {
            
        }
    }
}
