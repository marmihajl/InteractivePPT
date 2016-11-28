namespace InteractivePPT
{
    partial class Presentation
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.questionList = new System.Windows.Forms.CheckedListBox();
            this.btnAddGraph = new System.Windows.Forms.Button();
            this.radioLine = new System.Windows.Forms.RadioButton();
            this.radioPie = new System.Windows.Forms.RadioButton();
            this.radioBar = new System.Windows.Forms.RadioButton();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(63, 17);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(121, 21);
            this.comboBox1.TabIndex = 1;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(13, 20);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(44, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "Anketa:";
            // 
            // questionList
            // 
            this.questionList.FormattingEnabled = true;
            this.questionList.Location = new System.Drawing.Point(16, 55);
            this.questionList.Name = "questionList";
            this.questionList.Size = new System.Drawing.Size(309, 199);
            this.questionList.TabIndex = 3;
            // 
            // btnAddGraph
            // 
            this.btnAddGraph.Location = new System.Drawing.Point(109, 352);
            this.btnAddGraph.Name = "btnAddGraph";
            this.btnAddGraph.Size = new System.Drawing.Size(75, 23);
            this.btnAddGraph.TabIndex = 4;
            this.btnAddGraph.Text = "Dodaj graf";
            this.btnAddGraph.UseVisualStyleBackColor = true;
            this.btnAddGraph.Click += new System.EventHandler(this.btnAddGraph_Click);
            // 
            // radioLine
            // 
            this.radioLine.AutoSize = true;
            this.radioLine.Image = global::InteractivePPT.Properties.Resources.linechart;
            this.radioLine.Location = new System.Drawing.Point(237, 271);
            this.radioLine.Name = "radioLine";
            this.radioLine.Size = new System.Drawing.Size(57, 36);
            this.radioLine.TabIndex = 7;
            this.radioLine.TabStop = true;
            this.radioLine.UseVisualStyleBackColor = true;
            // 
            // radioPie
            // 
            this.radioPie.AutoSize = true;
            this.radioPie.Image = global::InteractivePPT.Properties.Resources.pie_chart__1_;
            this.radioPie.Location = new System.Drawing.Point(158, 271);
            this.radioPie.Name = "radioPie";
            this.radioPie.Size = new System.Drawing.Size(57, 36);
            this.radioPie.TabIndex = 6;
            this.radioPie.TabStop = true;
            this.radioPie.UseVisualStyleBackColor = true;
            // 
            // radioBar
            // 
            this.radioBar.AutoSize = true;
            this.radioBar.Image = global::InteractivePPT.Properties.Resources.barChart3;
            this.radioBar.Location = new System.Drawing.Point(84, 271);
            this.radioBar.Name = "radioBar";
            this.radioBar.Size = new System.Drawing.Size(57, 36);
            this.radioBar.TabIndex = 5;
            this.radioBar.TabStop = true;
            this.radioBar.UseVisualStyleBackColor = true;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 283);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(61, 13);
            this.label2.TabIndex = 8;
            this.label2.Text = "Vrsta grafa:";
            // 
            // Presentation
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.ClientSize = new System.Drawing.Size(371, 433);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.radioLine);
            this.Controls.Add(this.radioPie);
            this.Controls.Add(this.radioBar);
            this.Controls.Add(this.btnAddGraph);
            this.Controls.Add(this.questionList);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.comboBox1);
            this.Name = "Presentation";
            this.Text = "Presentation";
            this.Load += new System.EventHandler(this.Presentation_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.CheckedListBox questionList;
        private System.Windows.Forms.Button btnAddGraph;
        private System.Windows.Forms.RadioButton radioBar;
        private System.Windows.Forms.RadioButton radioPie;
        private System.Windows.Forms.RadioButton radioLine;
        private System.Windows.Forms.Label label2;
    }
}