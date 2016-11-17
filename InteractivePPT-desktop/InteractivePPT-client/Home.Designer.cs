﻿namespace InteractivePPT
{
    partial class Home
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
            this.lblUser = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.mySurveysDgv = new System.Windows.Forms.DataGridView();
            this.name = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.access_code = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.link_to_presentation = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.qr_code = new System.Windows.Forms.DataGridViewImageColumn();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.mySurveysDgv)).BeginInit();
            this.SuspendLayout();
            // 
            // lblUser
            // 
            this.lblUser.AutoSize = true;
            this.lblUser.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.lblUser.ForeColor = System.Drawing.Color.White;
            this.lblUser.Location = new System.Drawing.Point(601, 9);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(0, 20);
            this.lblUser.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(502, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(51, 20);
            this.label1.TabIndex = 1;
            this.label1.Text = "label1";
            // 
            // mySurveysDgv
            // 
            this.mySurveysDgv.AllowUserToAddRows = false;
            this.mySurveysDgv.AllowUserToDeleteRows = false;
            this.mySurveysDgv.AllowUserToOrderColumns = true;
            this.mySurveysDgv.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.mySurveysDgv.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.mySurveysDgv.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.mySurveysDgv.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.name,
            this.access_code,
            this.link_to_presentation,
            this.qr_code});
            this.mySurveysDgv.Location = new System.Drawing.Point(35, 46);
            this.mySurveysDgv.Margin = new System.Windows.Forms.Padding(2);
            this.mySurveysDgv.MultiSelect = false;
            this.mySurveysDgv.Name = "mySurveysDgv";
            this.mySurveysDgv.ReadOnly = true;
            this.mySurveysDgv.RowTemplate.Height = 24;
            this.mySurveysDgv.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.CellSelect;
            this.mySurveysDgv.Size = new System.Drawing.Size(563, 184);
            this.mySurveysDgv.TabIndex = 3;
            this.mySurveysDgv.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellContentClick);
            this.mySurveysDgv.CellMouseEnter += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellMouseEnter);
            this.mySurveysDgv.CellMouseLeave += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellMouseLeave);
            // 
            // name
            // 
            this.name.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.DisplayedCells;
            this.name.HeaderText = "Survey name";
            this.name.Name = "name";
            this.name.ReadOnly = true;
            this.name.Width = 87;
            // 
            // access_code
            // 
            this.access_code.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.DisplayedCells;
            this.access_code.HeaderText = "Access code";
            this.access_code.Name = "access_code";
            this.access_code.ReadOnly = true;
            this.access_code.Width = 87;
            // 
            // link_to_presentation
            // 
            this.link_to_presentation.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.link_to_presentation.HeaderText = "Link to presentation";
            this.link_to_presentation.Name = "link_to_presentation";
            this.link_to_presentation.ReadOnly = true;
            // 
            // qr_code
            // 
            this.qr_code.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.AllCells;
            this.qr_code.HeaderText = "Qr code";
            this.qr_code.Name = "qr_code";
            this.qr_code.ReadOnly = true;
            this.qr_code.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.qr_code.SortMode = System.Windows.Forms.DataGridViewColumnSortMode.Automatic;
            this.qr_code.Width = 65;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(239, 327);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(102, 42);
            this.button1.TabIndex = 4;
            this.button1.Text = "Odabir prezentacije";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // Home
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.ClientSize = new System.Drawing.Size(643, 428);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.mySurveysDgv);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblUser);
            this.Name = "Home";
            this.Text = "Home";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Home_FormClosed);
            this.Load += new System.EventHandler(this.Home_Load);
            ((System.ComponentModel.ISupportInitialize)(this.mySurveysDgv)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblUser;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DataGridView mySurveysDgv;
        private System.Windows.Forms.DataGridViewTextBoxColumn name;
        private System.Windows.Forms.DataGridViewTextBoxColumn access_code;
        private System.Windows.Forms.DataGridViewTextBoxColumn link_to_presentation;
        private System.Windows.Forms.DataGridViewImageColumn qr_code;
        private System.Windows.Forms.Button button1;
    }
}