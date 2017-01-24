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
    public partial class ComentarFilter : Form
    {
        TextItemsList items;
        Presentation presentation;
        public ComentarFilter(TextItemsList item, Presentation p)
        {
            InitializeComponent();
            items = item;
            presentation = p;

            BindingSource bs = new BindingSource();
            bs.DataSource = items.results;
            comentars.DataSource = bs;
            comentars.DisplayMember = "choice_name";
            comentars.ValueMember = "choice_name";
            comentars.SelectedIndex = -1;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            for (int i = 0; i < comentars.Items.Count; i++)
            {
                TextItems currentItem = (TextItems)comentars.Items[i];
                if (comentars.GetItemCheckState(i) == CheckState.Checked)
                {
                    Presentation.chooseItem.results.Add(currentItem);
                }
                
                
            }
            Presentation.make = true;
            presentation.Show();
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            presentation.Show();
            this.Close();
        }
    }
}
