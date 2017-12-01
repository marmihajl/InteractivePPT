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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle28 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle29 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle30 = new System.Windows.Forms.DataGridViewCellStyle();
            this.lblUser = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.openPptButton = new MetroFramework.Controls.MetroButton();
            this.mySurveysDgv = new MetroFramework.Controls.MetroGrid();
            this.name = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.access_code = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.link_to_presentation = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.qr_code = new System.Windows.Forms.DataGridViewImageColumn();
            this.refreshListButton = new MetroFramework.Controls.MetroButton();
            ((System.ComponentModel.ISupportInitialize)(this.mySurveysDgv)).BeginInit();
            this.SuspendLayout();
            // 
            // lblUser
            // 
            this.lblUser.AutoSize = true;
            this.lblUser.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.lblUser.ForeColor = System.Drawing.Color.White;
            this.lblUser.Location = new System.Drawing.Point(801, 11);
            this.lblUser.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(0, 25);
            this.lblUser.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(959, 31);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(413, 32);
            this.label1.TabIndex = 1;
            this.label1.Text = "label1";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // openPptButton
            // 
            this.openPptButton.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.openPptButton.ForeColor = System.Drawing.Color.Red;
            this.openPptButton.Location = new System.Drawing.Point(472, 532);
            this.openPptButton.Margin = new System.Windows.Forms.Padding(4);
            this.openPptButton.Name = "openPptButton";
            this.openPptButton.Size = new System.Drawing.Size(200, 50);
            this.openPptButton.TabIndex = 6;
            this.openPptButton.Text = Resources.strings.open_presentation;
            this.openPptButton.Theme = MetroFramework.MetroThemeStyle.Light;
            this.openPptButton.UseSelectable = true;
            this.openPptButton.Click += new System.EventHandler(this.openPptButton_Click);
            // 
            // mySurveysDgv
            // 
            this.mySurveysDgv.AllowUserToAddRows = false;
            this.mySurveysDgv.AllowUserToDeleteRows = false;
            this.mySurveysDgv.AllowUserToResizeRows = false;
            this.mySurveysDgv.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.mySurveysDgv.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.mySurveysDgv.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.mySurveysDgv.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.mySurveysDgv.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle28.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle28.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle28.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle28.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle28.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle28.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle28.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mySurveysDgv.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle28;
            this.mySurveysDgv.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.mySurveysDgv.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.name,
            this.access_code,
            this.link_to_presentation,
            this.qr_code});
            dataGridViewCellStyle29.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle29.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle29.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle29.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(136)))), ((int)(((byte)(136)))), ((int)(((byte)(136)))));
            dataGridViewCellStyle29.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle29.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle29.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.mySurveysDgv.DefaultCellStyle = dataGridViewCellStyle29;
            this.mySurveysDgv.EnableHeadersVisualStyles = false;
            this.mySurveysDgv.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.mySurveysDgv.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.mySurveysDgv.Location = new System.Drawing.Point(20, 95);
            this.mySurveysDgv.Margin = new System.Windows.Forms.Padding(4);
            this.mySurveysDgv.MultiSelect = false;
            this.mySurveysDgv.Name = "mySurveysDgv";
            this.mySurveysDgv.ReadOnly = true;
            this.mySurveysDgv.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle30.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle30.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle30.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle30.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle30.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle30.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle30.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.mySurveysDgv.RowHeadersDefaultCellStyle = dataGridViewCellStyle30;
            this.mySurveysDgv.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.DisableResizing;
            this.mySurveysDgv.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.mySurveysDgv.Size = new System.Drawing.Size(1352, 429);
            this.mySurveysDgv.TabIndex = 5;
            this.mySurveysDgv.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellContentClick);
            this.mySurveysDgv.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellDoubleClick);
            this.mySurveysDgv.CellMouseEnter += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellMouseEnter);
            this.mySurveysDgv.CellMouseLeave += new System.Windows.Forms.DataGridViewCellEventHandler(this.mySurveysDgv_CellMouseLeave);
            // 
            // name
            // 
            this.name.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.name.HeaderText = Resources.strings.presentation_name;
            this.name.Name = "name";
            this.name.ReadOnly = true;
            // 
            // access_code
            // 
            this.access_code.HeaderText = Resources.strings.access_code;
            this.access_code.Name = "access_code";
            this.access_code.ReadOnly = true;
            this.access_code.Width = 188;
            // 
            // link_to_presentation
            // 
            this.link_to_presentation.HeaderText = Resources.strings.link_to_presentation;
            this.link_to_presentation.Name = "link_to_presentation";
            this.link_to_presentation.ReadOnly = true;
            this.link_to_presentation.Visible = false;
            // 
            // qr_code
            // 
            this.qr_code.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.qr_code.FillWeight = 10F;
            this.qr_code.HeaderText = Resources.strings.qr_code;
            this.qr_code.Name = "qr_code";
            this.qr_code.ReadOnly = true;
            this.qr_code.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.qr_code.SortMode = System.Windows.Forms.DataGridViewColumnSortMode.Automatic;
            // 
            // refreshListButton
            // 
            this.refreshListButton.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.refreshListButton.ForeColor = System.Drawing.Color.Red;
            this.refreshListButton.Location = new System.Drawing.Point(725, 532);
            this.refreshListButton.Margin = new System.Windows.Forms.Padding(4);
            this.refreshListButton.Name = "refreshListButton";
            this.refreshListButton.Size = new System.Drawing.Size(200, 50);
            this.refreshListButton.TabIndex = 7;
            this.refreshListButton.Text = Resources.strings.refresh_presentation_list;
            this.refreshListButton.Theme = MetroFramework.MetroThemeStyle.Light;
            this.refreshListButton.UseSelectable = true;
            this.refreshListButton.Click += new System.EventHandler(this.refreshListButton_Click);
            // 
            // Home
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1396, 601);
            this.Controls.Add(this.refreshListButton);
            this.Controls.Add(this.openPptButton);
            this.Controls.Add(this.mySurveysDgv);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblUser);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Movable = false;
            this.Name = "Home";
            this.Padding = new System.Windows.Forms.Padding(20, 74, 20, 20);
            this.Resizable = false;
            this.ShadowType = MetroFramework.Forms.MetroFormShadowType.None;
            this.Text = Resources.strings.my_presentations;
            this.Theme = MetroFramework.MetroThemeStyle.Dark;
            this.Load += new System.EventHandler(this.Home_Load);
            ((System.ComponentModel.ISupportInitialize)(this.mySurveysDgv)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblUser;
        private System.Windows.Forms.Label label1;
        private MetroFramework.Controls.MetroButton openPptButton;
        private MetroFramework.Controls.MetroGrid mySurveysDgv;
        private System.Windows.Forms.DataGridViewTextBoxColumn name;
        private System.Windows.Forms.DataGridViewTextBoxColumn access_code;
        private System.Windows.Forms.DataGridViewTextBoxColumn link_to_presentation;
        private System.Windows.Forms.DataGridViewImageColumn qr_code;
        private MetroFramework.Controls.MetroButton refreshListButton;
    }
}