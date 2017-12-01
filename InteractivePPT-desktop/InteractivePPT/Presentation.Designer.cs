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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle15 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle16 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle17 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle18 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle20 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle21 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle19 = new System.Windows.Forms.DataGridViewCellStyle();
            this.label1 = new System.Windows.Forms.Label();
            this.comboBox1 = new MetroFramework.Controls.MetroComboBox();
            this.btnAddGraph = new MetroFramework.Controls.MetroButton();
            this.label4 = new System.Windows.Forms.Label();
            this.graphPosition = new MetroFramework.Controls.MetroComboBox();
            this.audienceTabControl = new MetroFramework.Controls.MetroTabControl();
            this.tabReplies = new System.Windows.Forms.TabPage();
            this.dgvReplies = new MetroFramework.Controls.MetroGrid();
            this.Column1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Column2 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.tabChat = new System.Windows.Forms.TabPage();
            this.dgvChat = new MetroFramework.Controls.MetroGrid();
            this.dataGridViewTextBoxColumn3 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn4 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn5 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.artifactInsertionTabControl = new MetroFramework.Controls.MetroTabControl();
            this.tabChart = new System.Windows.Forms.TabPage();
            this.numOfGroupsNUD = new System.Windows.Forms.NumericUpDown();
            this.groupRemainingOptsCB = new MetroFramework.Controls.MetroCheckBox();
            this.radioBar = new System.Windows.Forms.RadioButton();
            this.radioLine = new System.Windows.Forms.RadioButton();
            this.radioPie = new System.Windows.Forms.RadioButton();
            this.questionList = new System.Windows.Forms.CheckedListBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.tabComments = new System.Windows.Forms.TabPage();
            this.questionListForComments = new System.Windows.Forms.CheckedListBox();
            this.audienceTabControl.SuspendLayout();
            this.tabReplies.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvReplies)).BeginInit();
            this.tabChat.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvChat)).BeginInit();
            this.artifactInsertionTabControl.SuspendLayout();
            this.tabChart.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numOfGroupsNUD)).BeginInit();
            this.tabComments.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.ForeColor = System.Drawing.SystemColors.ControlLightLight;
            this.label1.Location = new System.Drawing.Point(33, 86);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(56, 17);
            this.label1.TabIndex = 2;
            this.label1.Text = Resources.strings.survey;
            // 
            // comboBox1
            // 
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.ItemHeight = 24;
            this.comboBox1.Location = new System.Drawing.Point(102, 78);
            this.comboBox1.Margin = new System.Windows.Forms.Padding(4);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(358, 30);
            this.comboBox1.TabIndex = 11;
            this.comboBox1.UseSelectable = true;
            this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // btnAddGraph
            // 
            this.btnAddGraph.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnAddGraph.Location = new System.Drawing.Point(142, 484);
            this.btnAddGraph.Margin = new System.Windows.Forms.Padding(4);
            this.btnAddGraph.Name = "btnAddGraph";
            this.btnAddGraph.Size = new System.Drawing.Size(171, 28);
            this.btnAddGraph.TabIndex = 13;
            this.btnAddGraph.Text = global::InteractivePPT.Resources.strings.add_artifact;
            this.btnAddGraph.Theme = MetroFramework.MetroThemeStyle.Light;
            this.btnAddGraph.UseSelectable = true;
            this.btnAddGraph.Click += new System.EventHandler(this.btnAddGraph_Click);
            // 
            // label4
            // 
            this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label4.AutoSize = true;
            this.label4.ForeColor = System.Drawing.SystemColors.ControlLightLight;
            this.label4.Location = new System.Drawing.Point(41, 434);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(119, 17);
            this.label4.TabIndex = 15;
            this.label4.Text = Resources.strings.artifact_location;
            // 
            // graphPosition
            // 
            this.graphPosition.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.graphPosition.FormattingEnabled = true;
            this.graphPosition.ItemHeight = 24;
            this.graphPosition.Location = new System.Drawing.Point(192, 425);
            this.graphPosition.Margin = new System.Windows.Forms.Padding(4);
            this.graphPosition.Name = "graphPosition";
            this.graphPosition.Size = new System.Drawing.Size(220, 30);
            this.graphPosition.TabIndex = 16;
            this.graphPosition.UseSelectable = true;
            // 
            // audienceTabControl
            // 
            this.audienceTabControl.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.audienceTabControl.Controls.Add(this.tabReplies);
            this.audienceTabControl.Controls.Add(this.tabChat);
            this.audienceTabControl.Location = new System.Drawing.Point(485, 124);
            this.audienceTabControl.Name = "audienceTabControl";
            this.audienceTabControl.SelectedIndex = 0;
            this.audienceTabControl.Size = new System.Drawing.Size(451, 346);
            this.audienceTabControl.SizeMode = System.Windows.Forms.TabSizeMode.Fixed;
            this.audienceTabControl.TabIndex = 18;
            this.audienceTabControl.UseSelectable = true;
            // 
            // tabReplies
            // 
            this.tabReplies.Controls.Add(this.dgvReplies);
            this.tabReplies.Location = new System.Drawing.Point(4, 38);
            this.tabReplies.Name = "tabReplies";
            this.tabReplies.Size = new System.Drawing.Size(443, 304);
            this.tabReplies.TabIndex = 0;
            this.tabReplies.Text = global::InteractivePPT.Resources.strings.reply_requests;
            this.tabReplies.Enter += new System.EventHandler(this.tabReplies_Enter);
            // 
            // dgvReplies
            // 
            this.dgvReplies.AllowUserToAddRows = false;
            this.dgvReplies.AllowUserToDeleteRows = false;
            this.dgvReplies.AllowUserToResizeRows = false;
            this.dgvReplies.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.dgvReplies.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgvReplies.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.dgvReplies.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle15.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle15.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle15.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle15.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle15.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle15.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle15.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvReplies.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle15;
            this.dgvReplies.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvReplies.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Column1,
            this.Column2});
            dataGridViewCellStyle16.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle16.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle16.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle16.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(136)))), ((int)(((byte)(136)))), ((int)(((byte)(136)))));
            dataGridViewCellStyle16.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle16.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle16.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvReplies.DefaultCellStyle = dataGridViewCellStyle16;
            this.dgvReplies.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvReplies.EnableHeadersVisualStyles = false;
            this.dgvReplies.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.dgvReplies.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.dgvReplies.Location = new System.Drawing.Point(0, 0);
            this.dgvReplies.MultiSelect = false;
            this.dgvReplies.Name = "dgvReplies";
            this.dgvReplies.ReadOnly = true;
            this.dgvReplies.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle17.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle17.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle17.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle17.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle17.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle17.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle17.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvReplies.RowHeadersDefaultCellStyle = dataGridViewCellStyle17;
            this.dgvReplies.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.DisableResizing;
            this.dgvReplies.RowTemplate.Height = 24;
            this.dgvReplies.ScrollBars = System.Windows.Forms.ScrollBars.Horizontal;
            this.dgvReplies.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvReplies.Size = new System.Drawing.Size(443, 304);
            this.dgvReplies.TabIndex = 21;
            this.dgvReplies.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvReplies_CellClick);
            // 
            // Column1
            // 
            this.Column1.HeaderText = global::InteractivePPT.Resources.strings.user_id;
            this.Column1.Name = "Column1";
            this.Column1.ReadOnly = true;
            // 
            // Column2
            // 
            this.Column2.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.Column2.HeaderText = global::InteractivePPT.Resources.strings.fullname;
            this.Column2.Name = "Column2";
            this.Column2.ReadOnly = true;
            // 
            // tabChat
            // 
            this.tabChat.Controls.Add(this.dgvChat);
            this.tabChat.Location = new System.Drawing.Point(4, 38);
            this.tabChat.Name = "tabChat";
            this.tabChat.Size = new System.Drawing.Size(443, 304);
            this.tabChat.TabIndex = 1;
            this.tabChat.Text = global::InteractivePPT.Resources.strings.chat;
            this.tabChat.Enter += new System.EventHandler(this.tabChat_Enter);
            // 
            // dgvChat
            // 
            this.dgvChat.AllowUserToAddRows = false;
            this.dgvChat.AllowUserToDeleteRows = false;
            this.dgvChat.AllowUserToResizeRows = false;
            this.dgvChat.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.dgvChat.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.dgvChat.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dgvChat.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.dgvChat.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle18.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle18.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle18.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle18.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle18.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle18.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle18.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvChat.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle18;
            this.dgvChat.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvChat.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.dataGridViewTextBoxColumn3,
            this.dataGridViewTextBoxColumn4,
            this.dataGridViewTextBoxColumn5});
            dataGridViewCellStyle20.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle20.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle20.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle20.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(136)))), ((int)(((byte)(136)))), ((int)(((byte)(136)))));
            dataGridViewCellStyle20.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle20.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle20.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvChat.DefaultCellStyle = dataGridViewCellStyle20;
            this.dgvChat.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvChat.EnableHeadersVisualStyles = false;
            this.dgvChat.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.dgvChat.GridColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            this.dgvChat.Location = new System.Drawing.Point(0, 0);
            this.dgvChat.MultiSelect = false;
            this.dgvChat.Name = "dgvChat";
            this.dgvChat.ReadOnly = true;
            this.dgvChat.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            dataGridViewCellStyle21.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle21.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(174)))), ((int)(((byte)(219)))));
            dataGridViewCellStyle21.Font = new System.Drawing.Font("Segoe UI", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            dataGridViewCellStyle21.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(255)))));
            dataGridViewCellStyle21.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(198)))), ((int)(((byte)(247)))));
            dataGridViewCellStyle21.SelectionForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(17)))), ((int)(((byte)(17)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle21.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvChat.RowHeadersDefaultCellStyle = dataGridViewCellStyle21;
            this.dgvChat.RowHeadersWidthSizeMode = System.Windows.Forms.DataGridViewRowHeadersWidthSizeMode.DisableResizing;
            this.dgvChat.RowTemplate.Height = 24;
            this.dgvChat.ScrollBars = System.Windows.Forms.ScrollBars.Horizontal;
            this.dgvChat.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvChat.Size = new System.Drawing.Size(443, 304);
            this.dgvChat.TabIndex = 22;
            // 
            // dataGridViewTextBoxColumn3
            // 
            this.dataGridViewTextBoxColumn3.HeaderText = global::InteractivePPT.Resources.strings.user_id;
            this.dataGridViewTextBoxColumn3.Name = "dataGridViewTextBoxColumn3";
            this.dataGridViewTextBoxColumn3.ReadOnly = true;
            // 
            // dataGridViewTextBoxColumn4
            // 
            this.dataGridViewTextBoxColumn4.HeaderText = global::InteractivePPT.Resources.strings.fullname;
            this.dataGridViewTextBoxColumn4.Name = "dataGridViewTextBoxColumn4";
            this.dataGridViewTextBoxColumn4.ReadOnly = true;
            // 
            // dataGridViewTextBoxColumn5
            // 
            this.dataGridViewTextBoxColumn5.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            dataGridViewCellStyle19.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dataGridViewTextBoxColumn5.DefaultCellStyle = dataGridViewCellStyle19;
            this.dataGridViewTextBoxColumn5.HeaderText = global::InteractivePPT.Resources.strings.message_content;
            this.dataGridViewTextBoxColumn5.Name = "dataGridViewTextBoxColumn5";
            this.dataGridViewTextBoxColumn5.ReadOnly = true;
            // 
            // artifactInsertionTabControl
            // 
            this.artifactInsertionTabControl.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.artifactInsertionTabControl.Controls.Add(this.tabChart);
            this.artifactInsertionTabControl.Controls.Add(this.tabComments);
            this.artifactInsertionTabControl.Location = new System.Drawing.Point(16, 124);
            this.artifactInsertionTabControl.Name = "artifactInsertionTabControl";
            this.artifactInsertionTabControl.SelectedIndex = 0;
            this.artifactInsertionTabControl.Size = new System.Drawing.Size(450, 284);
            this.artifactInsertionTabControl.SizeMode = System.Windows.Forms.TabSizeMode.Fixed;
            this.artifactInsertionTabControl.TabIndex = 19;
            this.artifactInsertionTabControl.UseSelectable = true;
            // 
            // tabChart
            // 
            this.tabChart.BackColor = System.Drawing.Color.White;
            this.tabChart.Controls.Add(this.numOfGroupsNUD);
            this.tabChart.Controls.Add(this.groupRemainingOptsCB);
            this.tabChart.Controls.Add(this.radioBar);
            this.tabChart.Controls.Add(this.radioLine);
            this.tabChart.Controls.Add(this.radioPie);
            this.tabChart.Controls.Add(this.questionList);
            this.tabChart.Controls.Add(this.label2);
            this.tabChart.Controls.Add(this.label3);
            this.tabChart.Location = new System.Drawing.Point(4, 38);
            this.tabChart.Name = "tabChart";
            this.tabChart.Size = new System.Drawing.Size(442, 242);
            this.tabChart.TabIndex = 0;
            this.tabChart.Text = global::InteractivePPT.Resources.strings.charts;
            // 
            // numOfGroupsNUD
            // 
            this.numOfGroupsNUD.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.numOfGroupsNUD.Location = new System.Drawing.Point(236, 167);
            this.numOfGroupsNUD.Name = "numOfGroupsNUD";
            this.numOfGroupsNUD.Size = new System.Drawing.Size(42, 22);
            this.numOfGroupsNUD.TabIndex = 15;
            this.numOfGroupsNUD.ValueChanged += new System.EventHandler(this.numOfGroupsNUD_ValueChanged);
            // 
            // groupRemainingOptsCB
            // 
            this.groupRemainingOptsCB.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.groupRemainingOptsCB.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.groupRemainingOptsCB.Location = new System.Drawing.Point(257, 164);
            this.groupRemainingOptsCB.Name = "groupRemainingOptsCB";
            this.groupRemainingOptsCB.Size = new System.Drawing.Size(180, 27);
            this.groupRemainingOptsCB.TabIndex = 21;
            this.groupRemainingOptsCB.Text = global::InteractivePPT.Resources.strings.group_remaining_options;
            this.groupRemainingOptsCB.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.groupRemainingOptsCB.UseSelectable = true;
            // 
            // radioBar
            // 
            this.radioBar.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.radioBar.AutoSize = true;
            this.radioBar.Checked = true;
            this.radioBar.Image = global::InteractivePPT.Properties.Resources.bar_chart;
            this.radioBar.Location = new System.Drawing.Point(122, 198);
            this.radioBar.Margin = new System.Windows.Forms.Padding(4);
            this.radioBar.Name = "radioBar";
            this.radioBar.Size = new System.Drawing.Size(60, 36);
            this.radioBar.TabIndex = 10;
            this.radioBar.TabStop = true;
            this.radioBar.UseVisualStyleBackColor = true;
            // 
            // radioLine
            // 
            this.radioLine.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.radioLine.AutoSize = true;
            this.radioLine.Image = global::InteractivePPT.Properties.Resources.line_chart;
            this.radioLine.Location = new System.Drawing.Point(326, 198);
            this.radioLine.Margin = new System.Windows.Forms.Padding(4);
            this.radioLine.Name = "radioLine";
            this.radioLine.Size = new System.Drawing.Size(60, 36);
            this.radioLine.TabIndex = 12;
            this.radioLine.UseVisualStyleBackColor = true;
            // 
            // radioPie
            // 
            this.radioPie.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.radioPie.AutoSize = true;
            this.radioPie.Image = global::InteractivePPT.Properties.Resources.pie_chart;
            this.radioPie.Location = new System.Drawing.Point(220, 198);
            this.radioPie.Margin = new System.Windows.Forms.Padding(4);
            this.radioPie.Name = "radioPie";
            this.radioPie.Size = new System.Drawing.Size(60, 36);
            this.radioPie.TabIndex = 11;
            this.radioPie.UseVisualStyleBackColor = true;
            // 
            // questionList
            // 
            this.questionList.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.questionList.FormattingEnabled = true;
            this.questionList.HorizontalScrollbar = true;
            this.questionList.Location = new System.Drawing.Point(16, 11);
            this.questionList.Margin = new System.Windows.Forms.Padding(4);
            this.questionList.Name = "questionList";
            this.questionList.Size = new System.Drawing.Size(411, 157);
            this.questionList.TabIndex = 9;
            this.questionList.UseCompatibleTextRendering = true;
            // 
            // label2
            // 
            this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label2.AutoSize = true;
            this.label2.ForeColor = System.Drawing.Color.Black;
            this.label2.Location = new System.Drawing.Point(23, 205);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(77, 17);
            this.label2.TabIndex = 13;
            this.label2.Text = Resources.strings.chart_type;
            // 
            // label3
            // 
            this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label3.Location = new System.Drawing.Point(-4, 168);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(189, 16);
            this.label3.TabIndex = 20;
            this.label3.Text = Resources.strings.number_of_options_to_show;
            // 
            // tabComments
            // 
            this.tabComments.BackColor = System.Drawing.Color.White;
            this.tabComments.Controls.Add(this.questionListForComments);
            this.tabComments.Location = new System.Drawing.Point(4, 38);
            this.tabComments.Name = "tabComments";
            this.tabComments.Size = new System.Drawing.Size(438, 242);
            this.tabComments.TabIndex = 1;
            this.tabComments.Text = global::InteractivePPT.Resources.strings.comments;
            // 
            // questionListForComments
            // 
            this.questionListForComments.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.questionListForComments.FormattingEnabled = true;
            this.questionListForComments.HorizontalScrollbar = true;
            this.questionListForComments.Location = new System.Drawing.Point(14, 17);
            this.questionListForComments.Margin = new System.Windows.Forms.Padding(4);
            this.questionListForComments.Name = "questionListForComments";
            this.questionListForComments.Size = new System.Drawing.Size(411, 208);
            this.questionListForComments.TabIndex = 10;
            this.questionListForComments.UseCompatibleTextRendering = true;
            // 
            // Presentation
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(966, 533);
            this.Controls.Add(this.artifactInsertionTabControl);
            this.Controls.Add(this.audienceTabControl);
            this.Controls.Add(this.graphPosition);
            this.Controls.Add(this.btnAddGraph);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label4);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Movable = false;
            this.Name = "Presentation";
            this.Padding = new System.Windows.Forms.Padding(27, 74, 27, 25);
            this.Resizable = false;
            this.ShadowType = MetroFramework.Forms.MetroFormShadowType.None;
            this.Text = Resources.strings.presentation_management;
            this.Theme = MetroFramework.MetroThemeStyle.Dark;
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Presentation_FormClosing);
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Presentation_FormClosed);
            this.Load += new System.EventHandler(this.Presentation_Load);
            this.audienceTabControl.ResumeLayout(false);
            this.tabReplies.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvReplies)).EndInit();
            this.tabChat.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvChat)).EndInit();
            this.artifactInsertionTabControl.ResumeLayout(false);
            this.tabChart.ResumeLayout(false);
            this.tabChart.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numOfGroupsNUD)).EndInit();
            this.tabComments.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label label1;
        private MetroFramework.Controls.MetroComboBox comboBox1;
        private MetroFramework.Controls.MetroButton btnAddGraph;
        private System.Windows.Forms.Label label4;
        private MetroFramework.Controls.MetroComboBox graphPosition;
        private MetroFramework.Controls.MetroTabControl audienceTabControl;
        private System.Windows.Forms.TabPage tabReplies;
        private MetroFramework.Controls.MetroGrid dgvReplies;
        private System.Windows.Forms.TabPage tabChat;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Column2;
        private MetroFramework.Controls.MetroGrid dgvChat;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn3;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn4;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn5;
        private MetroFramework.Controls.MetroTabControl artifactInsertionTabControl;
        private System.Windows.Forms.TabPage tabChart;
        private System.Windows.Forms.RadioButton radioBar;
        private System.Windows.Forms.RadioButton radioLine;
        private System.Windows.Forms.RadioButton radioPie;
        private System.Windows.Forms.CheckedListBox questionList;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TabPage tabComments;
        private System.Windows.Forms.CheckedListBox questionListForComments;
        private System.Windows.Forms.NumericUpDown numOfGroupsNUD;
        private MetroFramework.Controls.MetroCheckBox groupRemainingOptsCB;
        private System.Windows.Forms.Label label3;
    }
}