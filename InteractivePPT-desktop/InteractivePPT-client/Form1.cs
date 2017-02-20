using InteractivePPT;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace InteractivePPT_client
{
    public partial class Form1 : MetroFramework.Forms.MetroForm
    {
        public Form1()
        {
            InitializeComponent();
            this.WindowState = FormWindowState.Maximized;
        }
        

        private void pictureBox1_Click(object sender, EventArgs e)
        {
            Login login = new Login(this);
            login.MdiParent = this;
            login.WindowState = FormWindowState.Maximized;
            pictureBox1.Visible = false;
            login.Show();
        }

        private void metroButton1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}
