using System;
using System.Windows.Forms;

namespace InteractivePPT
{
    public partial class CommentFilter : Form
    {
        TextItemsList items;
        Presentation presentation;
        public CommentFilter(TextItemsList item, Presentation p)
        {
            InitializeComponent();
            items = item;
            presentation = p;

            BindingSource bs = new BindingSource();
            bs.DataSource = items.results;
            comments.DataSource = bs;
            comments.DisplayMember = "choice_name";
            comments.ValueMember = "choice_name";
            comments.SelectedIndex = -1;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            for (int i = 0; i < comments.Items.Count; i++)
            {
                TextItems currentItem = (TextItems)comments.Items[i];
                if (comments.GetItemCheckState(i) == CheckState.Checked)
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
