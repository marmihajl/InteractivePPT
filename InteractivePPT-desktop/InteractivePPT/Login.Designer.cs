namespace InteractivePPT
{
    partial class Login
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
            this.webBrowser1 = new System.Windows.Forms.WebBrowser();
            this.loadingPicture = new System.Windows.Forms.PictureBox();
            this.languageSelectBox = new MetroFramework.Controls.MetroComboBox();
            ((System.ComponentModel.ISupportInitialize)(this.loadingPicture)).BeginInit();
            this.SuspendLayout();
            // 
            // webBrowser1
            // 
            this.webBrowser1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.webBrowser1.Location = new System.Drawing.Point(15, 53);
            this.webBrowser1.MinimumSize = new System.Drawing.Size(20, 20);
            this.webBrowser1.Name = "webBrowser1";
            this.webBrowser1.Size = new System.Drawing.Size(660, 414);
            this.webBrowser1.TabIndex = 0;
            this.webBrowser1.Url = new System.Uri(Resources.strings.fb_login_url);
            this.webBrowser1.Visible = false;
            this.webBrowser1.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowser1_Navigating);
            // 
            // loadingPicture
            // 
            this.loadingPicture.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.loadingPicture.Image = global::InteractivePPT.Properties.Resources.loading;
            this.loadingPicture.Location = new System.Drawing.Point(15, 53);
            this.loadingPicture.Name = "loadingPicture";
            this.loadingPicture.Size = new System.Drawing.Size(660, 406);
            this.loadingPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.loadingPicture.TabIndex = 1;
            this.loadingPicture.TabStop = false;
            // 
            // languageSelectBox
            // 
            this.languageSelectBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.languageSelectBox.FontSize = MetroFramework.MetroComboBoxSize.Small;
            this.languageSelectBox.FormattingEnabled = true;
            this.languageSelectBox.ItemHeight = 19;
            this.languageSelectBox.Location = new System.Drawing.Point(533, 23);
            this.languageSelectBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
            this.languageSelectBox.Name = "languageSelectBox";
            this.languageSelectBox.Size = new System.Drawing.Size(142, 25);
            this.languageSelectBox.TabIndex = 2;
            this.languageSelectBox.UseSelectable = true;
            this.languageSelectBox.Visible = false;
            this.languageSelectBox.SelectedIndexChanged += new System.EventHandler(this.languageSelectBox_SelectedIndexChanged);
            // 
            // Login
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(690, 479);
            this.Controls.Add(this.languageSelectBox);
            this.Controls.Add(this.loadingPicture);
            this.Controls.Add(this.webBrowser1);
            this.Name = "Login";
            this.Padding = new System.Windows.Forms.Padding(15, 49, 15, 16);
            this.Text = "INTERACTIVE PRESENTATION";
            this.TextAlign = MetroFramework.Forms.MetroFormTextAlign.Center;
            ((System.ComponentModel.ISupportInitialize)(this.loadingPicture)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.WebBrowser webBrowser1;
        private System.Windows.Forms.PictureBox loadingPicture;
        private MetroFramework.Controls.MetroComboBox languageSelectBox;
    }
}