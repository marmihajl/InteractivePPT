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
    public partial class Home : Form
    {
        User user;

        public Home(User u)
        {
            InitializeComponent();
            user = u;
        }

        private void Home_Load(object sender, EventArgs e)
        {
            lblUser.Text = user.name;
        }
    }
}
