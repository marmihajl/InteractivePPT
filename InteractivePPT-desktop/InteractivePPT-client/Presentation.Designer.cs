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
            this.dgvReplice = new System.Windows.Forms.DataGridView();
            this.User = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.label3 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dgvReplice)).BeginInit();
            this.SuspendLayout();
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(84, 21);
            this.comboBox1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(160, 24);
            this.comboBox1.TabIndex = 1;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(17, 25);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(56, 17);
            this.label1.TabIndex = 2;
            this.label1.Text = "Anketa:";
            // 
            // questionList
            // 
            this.questionList.FormattingEnabled = true;
            this.questionList.Location = new System.Drawing.Point(21, 68);
            this.questionList.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.questionList.Name = "questionList";
            this.questionList.Size = new System.Drawing.Size(411, 242);
            this.questionList.TabIndex = 3;
            // 
            // btnAddGraph
            // 
            this.btnAddGraph.Location = new System.Drawing.Point(145, 433);
            this.btnAddGraph.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnAddGraph.Name = "btnAddGraph";
            this.btnAddGraph.Size = new System.Drawing.Size(100, 28);
            this.btnAddGraph.TabIndex = 4;
            this.btnAddGraph.Text = "Dodaj graf";
            this.btnAddGraph.UseVisualStyleBackColor = true;
            this.btnAddGraph.Click += new System.EventHandler(this.btnAddGraph_Click);
            // 
            // radioLine
            // 
            this.radioLine.AutoSize = true;
            this.radioLine.Image = global::InteractivePPT.Properties.Resources.linechart;
            this.radioLine.Location = new System.Drawing.Point(316, 334);
            this.radioLine.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.radioLine.Name = "radioLine";
            this.radioLine.Size = new System.Drawing.Size(60, 36);
            this.radioLine.TabIndex = 7;
            this.radioLine.TabStop = true;
            this.radioLine.UseVisualStyleBackColor = true;
            // 
            // radioPie
            // 
            this.radioPie.AutoSize = true;
            this.radioPie.Image = global::InteractivePPT.Properties.Resources.pie_chart__1_;
            this.radioPie.Location = new System.Drawing.Point(211, 334);
            this.radioPie.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.radioPie.Name = "radioPie";
            this.radioPie.Size = new System.Drawing.Size(60, 36);
            this.radioPie.TabIndex = 6;
            this.radioPie.TabStop = true;
            this.radioPie.UseVisualStyleBackColor = true;
            // 
            // radioBar
            // 
            this.radioBar.AutoSize = true;
            this.radioBar.Image = global::InteractivePPT.Properties.Resources.barChart3;
            this.radioBar.Location = new System.Drawing.Point(112, 334);
            this.radioBar.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.radioBar.Name = "radioBar";
            this.radioBar.Size = new System.Drawing.Size(60, 36);
            this.radioBar.TabIndex = 5;
            this.radioBar.TabStop = true;
            this.radioBar.UseVisualStyleBackColor = true;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(16, 348);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(82, 17);
            this.label2.TabIndex = 8;
            this.label2.Text = "Vrsta grafa:";
            // 
            // dgvReplice
            // 
            this.dgvReplice.AllowUserToAddRows = false;
            this.dgvReplice.AllowUserToDeleteRows = false;
            this.dgvReplice.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvReplice.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.User,
            this.id});
            this.dgvReplice.Location = new System.Drawing.Point(473, 68);
            this.dgvReplice.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.dgvReplice.MultiSelect = false;
            this.dgvReplice.Name = "dgvReplice";
            this.dgvReplice.ReadOnly = true;
            this.dgvReplice.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvReplice.Size = new System.Drawing.Size(335, 245);
            this.dgvReplice.TabIndex = 9;
            this.dgvReplice.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvReplice_CellClick);
            // 
            // User
            // 
            this.User.HeaderText = "User";
            this.User.Name = "User";
            this.User.ReadOnly = true;
            // 
            // id
            // 
            this.id.HeaderText = "ID";
            this.id.Name = "id";
            this.id.ReadOnly = true;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(508, 44);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(77, 17);
            this.label3.TabIndex = 10;
            this.label3.Text = "Replicions:";
            // 
            // Presentation
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.ClientSize = new System.Drawing.Size(805, 533);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.dgvReplice);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.radioLine);
            this.Controls.Add(this.radioPie);
            this.Controls.Add(this.radioBar);
            this.Controls.Add(this.btnAddGraph);
            this.Controls.Add(this.questionList);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.comboBox1);
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "Presentation";
            this.Text = "Presentation";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Presentation_FormClosing);
            this.Load += new System.EventHandler(this.Presentation_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvReplice)).EndInit();
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
        private System.Windows.Forms.DataGridView dgvReplice;
        private System.Windows.Forms.DataGridViewTextBoxColumn User;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.DataGridViewTextBoxColumn id;
    }
}