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
            this.components = new System.ComponentModel.Container();
            this.webBrowser1 = new System.Windows.Forms.WebBrowser();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.timer2 = new System.Windows.Forms.Timer(this.components);
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
            this.webBrowser1.Location = new System.Drawing.Point(20, 65);
            this.webBrowser1.Margin = new System.Windows.Forms.Padding(4);
            this.webBrowser1.MinimumSize = new System.Drawing.Size(27, 25);
            this.webBrowser1.Name = "webBrowser1";
            this.webBrowser1.Size = new System.Drawing.Size(880, 509);
            this.webBrowser1.TabIndex = 0;
            this.webBrowser1.Url = new System.Uri("https://m.facebook.com", System.UriKind.Absolute);
            this.webBrowser1.Visible = false;
            this.webBrowser1.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowser1_Navigating);
            // 
            // timer1
            // 
            this.timer1.Interval = 3000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // timer2
            // 
            this.timer2.Interval = 2000;
            this.timer2.Tick += new System.EventHandler(this.timer2_Tick);
            // 
            // loadingPicture
            // 
            this.loadingPicture.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.loadingPicture.Image = global::InteractivePPT.Properties.Resources.loading;
            this.loadingPicture.Location = new System.Drawing.Point(20, 65);
            this.loadingPicture.Margin = new System.Windows.Forms.Padding(4);
            this.loadingPicture.Name = "loadingPicture";
            this.loadingPicture.Size = new System.Drawing.Size(880, 500);
            this.loadingPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.loadingPicture.TabIndex = 1;
            this.loadingPicture.TabStop = false;
            // 
            // languageSelectBox
            // 
            this.languageSelectBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.languageSelectBox.FontSize = MetroFramework.MetroComboBoxSize.Small;
            this.languageSelectBox.FormattingEnabled = true;
            this.languageSelectBox.ItemHeight = 21;
            this.languageSelectBox.Location = new System.Drawing.Point(711, 28);
            this.languageSelectBox.Name = "languageSelectBox";
            this.languageSelectBox.Size = new System.Drawing.Size(188, 27);
            this.languageSelectBox.TabIndex = 2;
            this.languageSelectBox.UseSelectable = true;
            this.languageSelectBox.Visible = false;
            this.languageSelectBox.SelectedIndexChanged += new System.EventHandler(this.languageSelectBox_SelectedIndexChanged);
            // 
            // Login
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(920, 589);
            this.Controls.Add(this.languageSelectBox);
            this.Controls.Add(this.loadingPicture);
            this.Controls.Add(this.webBrowser1);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Login";
            this.Text = "INTERACTIVE PRESENTATION";
            this.TextAlign = MetroFramework.Forms.MetroFormTextAlign.Center;
            ((System.ComponentModel.ISupportInitialize)(this.loadingPicture)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.WebBrowser webBrowser1;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Timer timer2;
        private System.Windows.Forms.PictureBox loadingPicture;
        private MetroFramework.Controls.MetroComboBox languageSelectBox;
    }
}