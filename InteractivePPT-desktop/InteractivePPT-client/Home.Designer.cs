namespace InteractivePPT
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            this.lblUser = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.openPptButton = new System.Windows.Forms.Button();
            this.mySurveysDgv = new MetroFramework.Controls.MetroGrid();
            this.name = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.access_code = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.link_to_presentation = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.qr_code = new System.Windows.Forms.DataGridViewImageColumn();
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
            // openPptButton
            // 
            this.openPptButton.Location = new System.Drawing.Point(244, 236);
            this.openPptButton.Name = "openPptButton";
            this.openPptButton.Size = new System.Drawing.Size(102, 42);
            this.openPptButton.TabIndex = 4;
            this.openPptButton.Text = "Otvori prezentaciju";
            this.openPptButton.UseVisualStyleBackColor = true;
            this.openPptButton.Click += new System.EventHandler(this.openPptButton_Click);
            // 
            // mySurveysDgv
            // 
            this.mySurveysDgv.AllowUserToAddRows = false;
            this.mySurveysDgv.AllowUserToDeleteRows = false;
            this.mySurveysDgv.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.mySurveysDgv.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.mySurveysDgv.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.mySurveysDgv.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle1.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle1.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mySurveysDgv.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle1;
            this.mySurveysDgv.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.mySurveysDgv.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.name,
            this.access_code,
            this.link_to_presentation,
            this.qr_code});
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle2.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(136)))), ((int)(((byte)(136)))), ((int)(((byte)(136)))));
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.mySurveysDgv.DefaultCellStyle = dataGridViewCellStyle2;
            this.mySurveysDgv.EnableHeadersVisualStyles = false;
            this.mySurveysDgv.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.mySurveysDgv.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.mySurveysDgv.Location = new System.Drawing.Point(27, 80);
            this.mySurveysDgv.Name = "mySurveysDgv";
            this.mySurveysDgv.ReadOnly = true;
            this.mySurveysDgv.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle3.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle3.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle3.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle3.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mySurveysDgv.RowHeadersDefaultCellStyle = dataGridViewCellStyle3;
            this.mySurveysDgv.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.DisableResizing;
            this.mySurveysDgv.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.mySurveysDgv.Size = new System.Drawing.Size(598, 150);
            this.mySurveysDgv.TabIndex = 5;
            this.mySurveysDgv.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellContentClick_1);
            // 
            // name
            // 
            this.name.HeaderText = "Naziv prezentacije";
            this.name.Name = "name";
            this.name.ReadOnly = true;
            // 
            // access_code
            // 
            this.access_code.HeaderText = "Pristupni kod";
            this.access_code.Name = "access_code";
            this.access_code.ReadOnly = true;
            // 
            // link_to_presentation
            // 
            this.link_to_presentation.HeaderText = "Link na prezentaciju";
            this.link_to_presentation.Name = "link_to_presentation";
            this.link_to_presentation.ReadOnly = true;
            this.link_to_presentation.Visible = false;
            // 
            // qr_code
            // 
            this.qr_code.HeaderText = "Qr kod";
            this.qr_code.Name = "qr_code";
            this.qr_code.ReadOnly = true;
            this.qr_code.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.qr_code.SortMode = System.Windows.Forms.DataGridViewColumnSortMode.Automatic;
            // 
            // Home
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(643, 428);
            this.Controls.Add(this.mySurveysDgv);
            this.Controls.Add(this.openPptButton);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblUser);
            this.Name = "Home";
            this.Padding = new System.Windows.Forms.Padding(15, 49, 15, 16);
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
        private System.Windows.Forms.Button openPptButton;
        private MetroFramework.Controls.MetroGrid mySurveysDgv;
        private System.Windows.Forms.DataGridViewTextBoxColumn name;
        private System.Windows.Forms.DataGridViewTextBoxColumn access_code;
        private System.Windows.Forms.DataGridViewTextBoxColumn link_to_presentation;
        private System.Windows.Forms.DataGridViewImageColumn qr_code;
    }
}