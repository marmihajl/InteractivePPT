using OVT.CustomControls;
using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Linq;

namespace InteractivePPT
{
    public partial class CommentFilter : Form
    {
        public SortedDictionary<int, List<string>> selectedAnswersPerSelectedQuestions = new SortedDictionary<int, List<string>>();
        private List<CheckedListBox> answersPerSelectedQuestionsCheckedLB = new List<CheckedListBox>();
        private bool initiallyExpandOnlyFirst;

        public CommentFilter(SortedDictionary<int, List<Answer>> answersPerSelectedQuestions, Dictionary<int, string> questionNamesPerIds)
        {
            InitializeComponent();

            initiallyExpandOnlyFirst = answersPerSelectedQuestions.Count > 3;
            panel1.SuspendLayout();
            this.SuspendLayout();
            foreach (var questionAnswers in answersPerSelectedQuestions.Reverse())
            {
                int questionId = questionAnswers.Key;
                CollapsiblePanel questionPanel = new CollapsiblePanel();
                questionPanel.Name = "questionPanel" + questionId;
                questionPanel.Collapse = false;
                questionPanel.Dock = DockStyle.Top;
                questionPanel.HeaderText = questionNamesPerIds[questionId];
                panel1.Controls.Add(questionPanel);

                CheckedListBox answersToShowListBox = new CheckedListBox();
                answersToShowListBox.Name = "checkedListBox" + questionId;

                BindingSource bs = new BindingSource();
                bs.DataSource = questionAnswers.Value;
                answersToShowListBox.DataSource = bs;
                answersToShowListBox.DisplayMember = "choice_name";
                answersToShowListBox.ValueMember = "choice_name";
                answersToShowListBox.SelectedIndex = -1;
                answersToShowListBox.UseCompatibleTextRendering = true;
                answersToShowListBox.Tag = questionId;
                answersToShowListBox.MouseWheel += (s, e) => panel1.PerformScroll(e);   // allows scrolling through panel's content even if mouse cursor is positioned over CheckedListBox control

                answersPerSelectedQuestionsCheckedLB.Insert(0, answersToShowListBox);

                answersToShowListBox.Dock = DockStyle.Fill;
                questionPanel.Controls.Add(answersToShowListBox);

                Panel helperPanelForPaddingOnTop = new Panel();
                helperPanelForPaddingOnTop.Height = questionPanel.HeaderHeight;
                helperPanelForPaddingOnTop.Dock = DockStyle.Top;
                questionPanel.Controls.Add(helperPanelForPaddingOnTop);
            }
            panel1.ResumeLayout(false);
            this.ResumeLayout(false);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            foreach (var questionAnswers in answersPerSelectedQuestionsCheckedLB) {
                selectedAnswersPerSelectedQuestions.Add((int)questionAnswers.Tag, questionAnswers.CheckedItems.OfType<Answer>().Select(x => x.choice_name).ToList());
            }
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void CommentFilter_Load(object sender, EventArgs e)
        {
            if (initiallyExpandOnlyFirst)
            {
                foreach (var questionAnswers in answersPerSelectedQuestionsCheckedLB)
                {
                    CollapsiblePanel questionPanel = (CollapsiblePanel)questionAnswers.Parent;
                    questionPanel.Height = questionAnswers.PreferredHeight + questionPanel.HeaderHeight - 3;
                    questionPanel.Collapse = true;
                }
                ((CollapsiblePanel)answersPerSelectedQuestionsCheckedLB[0].Parent).Collapse = false;
            }
            else
            {
                foreach (var questionAnswers in answersPerSelectedQuestionsCheckedLB)
                {
                    CollapsiblePanel questionPanel = (CollapsiblePanel)questionAnswers.Parent;
                    questionPanel.Height = questionAnswers.PreferredHeight + questionPanel.HeaderHeight - 3;
                }
            }
        }
    }
}
