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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle5 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle6 = new System.Windows.Forms.DataGridViewCellStyle();
            this.label1 = new System.Windows.Forms.Label();
            this.questionList = new System.Windows.Forms.CheckedListBox();
            this.radioLine = new System.Windows.Forms.RadioButton();
            this.radioPie = new System.Windows.Forms.RadioButton();
            this.radioBar = new System.Windows.Forms.RadioButton();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.comboBox1 = new MetroFramework.Controls.MetroComboBox();
            this.dgvReplice = new MetroFramework.Controls.MetroGrid();
            this.User = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.btnAddGraph = new MetroFramework.Controls.MetroButton();
            ((System.ComponentModel.ISupportInitialize)(this.dgvReplice)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.ForeColor = System.Drawing.SystemColors.ControlLightLight;
            this.label1.Location = new System.Drawing.Point(25, 66);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(44, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "Anketa:";
            // 
            // questionList
            // 
            this.questionList.FormattingEnabled = true;
            this.questionList.Location = new System.Drawing.Point(28, 101);
            this.questionList.Name = "questionList";
            this.questionList.Size = new System.Drawing.Size(309, 124);
            this.questionList.TabIndex = 3;
            // 
            // radioLine
            // 
            this.radioLine.AutoSize = true;
            this.radioLine.Image = global::InteractivePPT.Properties.Resources.linechart;
            this.radioLine.Location = new System.Drawing.Point(254, 231);
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
            this.radioPie.Location = new System.Drawing.Point(175, 231);
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
            this.radioBar.Location = new System.Drawing.Point(101, 231);
            this.radioBar.Name = "radioBar";
            this.radioBar.Size = new System.Drawing.Size(57, 36);
            this.radioBar.TabIndex = 5;
            this.radioBar.TabStop = true;
            this.radioBar.UseVisualStyleBackColor = true;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.ForeColor = System.Drawing.SystemColors.ControlLightLight;
            this.label2.Location = new System.Drawing.Point(29, 243);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(61, 13);
            this.label2.TabIndex = 8;
            this.label2.Text = "Vrsta grafa:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.ForeColor = System.Drawing.SystemColors.ControlLightLight;
            this.label3.Location = new System.Drawing.Point(393, 82);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(59, 13);
            this.label3.TabIndex = 10;
            this.label3.Text = "Replicions:";
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.ItemHeight = 23;
            this.comboBox1.Location = new System.Drawing.Point(75, 63);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(201, 29);
            this.comboBox1.TabIndex = 11;
            this.comboBox1.UseSelectable = true;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged_1);
            // 
            // dgvReplice
            // 
            this.dgvReplice.AllowUserToAddRows = false;
            this.dgvReplice.AllowUserToDeleteRows = false;
            this.dgvReplice.AllowUserToResizeRows = false;
            this.dgvReplice.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.dgvReplice.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgvReplice.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.dgvReplice.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle4.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle4.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle4.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvReplice.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle4;
            this.dgvReplice.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvReplice.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.User,
            this.id});
            dataGridViewCellStyle5.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle5.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle5.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(136)))), ((int)(((byte)(136)))), ((int)(((byte)(136)))));
            dataGridViewCellStyle5.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle5.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle5.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvReplice.DefaultCellStyle = dataGridViewCellStyle5;
            this.dgvReplice.EnableHeadersVisualStyles = false;
            this.dgvReplice.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.dgvReplice.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.dgvReplice.Location = new System.Drawing.Point(396, 101);
            this.dgvReplice.Name = "dgvReplice";
            this.dgvReplice.ReadOnly = true;
            this.dgvReplice.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle6.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle6.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle6.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle6.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle6.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle6.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle6.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvReplice.RowHeadersDefaultCellStyle = dataGridViewCellStyle6;
            this.dgvReplice.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.DisableResizing;
            this.dgvReplice.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvReplice.Size = new System.Drawing.Size(244, 150);
            this.dgvReplice.TabIndex = 12;
            this.dgvReplice.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.metroGrid1_CellClick);
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
            // btnAddGraph
            // 
            this.btnAddGraph.Location = new System.Drawing.Point(121, 273);
            this.btnAddGraph.Name = "btnAddGraph";
            this.btnAddGraph.Size = new System.Drawing.Size(75, 23);
            this.btnAddGraph.TabIndex = 13;
            this.btnAddGraph.Text = "Dodaj graf";
            this.btnAddGraph.Theme = MetroFramework.MetroThemeStyle.Light;
            this.btnAddGraph.UseSelectable = true;
            this.btnAddGraph.Click += new System.EventHandler(this.btnAddGraph_Click_1);
            // 
            // Presentation
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(683, 433);
            this.Controls.Add(this.btnAddGraph);
            this.Controls.Add(this.dgvReplice);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.radioLine);
            this.Controls.Add(this.radioPie);
            this.Controls.Add(this.radioBar);
            this.Controls.Add(this.questionList);
            this.Controls.Add(this.label1);
            this.Name = "Presentation";
            this.Text = "Presentation";
            this.Theme = MetroFramework.MetroThemeStyle.Dark;
            this.Activated += new System.EventHandler(this.Presentation_Activated);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Presentation_FormClosing);
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Presentation_FormClosed);
            this.Load += new System.EventHandler(this.Presentation_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvReplice)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.CheckedListBox questionList;
        private System.Windows.Forms.RadioButton radioBar;
        private System.Windows.Forms.RadioButton radioPie;
        private System.Windows.Forms.RadioButton radioLine;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private MetroFramework.Controls.MetroComboBox comboBox1;
        private MetroFramework.Controls.MetroGrid dgvReplice;
        private System.Windows.Forms.DataGridViewTextBoxColumn User;
        private System.Windows.Forms.DataGridViewTextBoxColumn id;
        private MetroFramework.Controls.MetroButton btnAddGraph;
    }
}