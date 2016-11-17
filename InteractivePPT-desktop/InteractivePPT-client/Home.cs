using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Windows.Forms;
using System.Collections.Specialized;
using Newtonsoft.Json;
using com.google.zxing;
using com.google.zxing.qrcode;
using System.Threading;
using Microsoft.Office.Core;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
using System.Diagnostics;

namespace InteractivePPT
{
    public partial class Home : Form
    {
        private User user;
        Microsoft.Office.Interop.PowerPoint.Application pptApp = new Microsoft.Office.Interop.PowerPoint.Application();

        public Home(User u)
        {
            InitializeComponent();
            user = u;
        }

        private void Home_Load(object sender, EventArgs e)
        {
            string serializedUserSurveys = null;
            SurveyList mySurveyList = null;

            label1.Text = user.name;

            using (WebClient client = new WebClient())
            {
                try
                {
                    byte[] response =
                    client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                    {
                       { "request_type", "get_surveys" },
                       { "address", user.userName }
                    });
                    serializedUserSurveys = System.Text.Encoding.UTF8.GetString(response);
                }
                catch
                {
                    MessageBox.Show("Communication with server-side of this application could not be established");
                    return;
                }

            }

            if (serializedUserSurveys != null)
            {
                mySurveyList = JsonConvert.DeserializeObject<SurveyList>(serializedUserSurveys);
            }

            foreach (var survey in mySurveyList.data)
            {
                mySurveysDgv.Rows.Add(
                    survey.name,
                    survey.access_code,
                    survey.link_to_presentation,
                    (new QRCodeWriter()).encode(survey.access_code, BarcodeFormat.QR_CODE, 50, 50).ToBitmap()
                );
            }


        }

        private void Home_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void mySurveysDgv_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                using (Form form = new Form())
                {
                    int numOfPixels = Math.Min(Screen.PrimaryScreen.WorkingArea.Height, Screen.PrimaryScreen.WorkingArea.Width);

                    Bitmap img = (new QRCodeWriter()).encode(mySurveysDgv["access_code", e.RowIndex].Value.ToString(), BarcodeFormat.QR_CODE, numOfPixels, numOfPixels).ToBitmap();

                    form.StartPosition = FormStartPosition.CenterScreen;
                    form.Size = img.Size;

                    PictureBox pb = new PictureBox();
                    pb.Dock = DockStyle.Fill;
                    pb.Image = img;

                    form.Controls.Add(pb);
                    form.ShowDialog();
                }
            }
        }

        private void mySurveysDgv_CellMouseEnter(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                Cursor = Cursors.Hand;
            }
        }

        private void mySurveysDgv_CellMouseLeave(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 3 && e.RowIndex >= 0)
            {
                Cursor = Cursors.Default;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {

            OpenFileDialog ofd = new OpenFileDialog();
            ofd.ShowDialog();
            if(ofd.FileName != null)
            {
                var path = ofd.FileName;
                string powerPointFilePath = path;
                

                //pptApp = new Microsoft.Office.Interop.PowerPoint.Application();
                pptApp.Visible = Microsoft.Office.Core.MsoTriState.msoTrue;
                pptApp.Activate();

                Microsoft.Office.Interop.PowerPoint.Presentations ps = pptApp.Presentations;
                Microsoft.Office.Interop.PowerPoint.Presentation p = ps.Open(powerPointFilePath,
                            Microsoft.Office.Core.MsoTriState.msoFalse, Microsoft.Office.Core.MsoTriState.msoFalse, Microsoft.Office.Core.MsoTriState.msoTrue);
                
                

            }
            

            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            PowerPoint.SlideRange ppSR = pptApp.ActiveWindow.Selection.SlideRange;
            PowerPoint.Shape ppSharp = ppSR.Shapes.AddLabel(Microsoft.Office.Core.MsoTextOrientation.msoTextOrientationHorizontal, 0, 0, 200, 25);
            
            ppSharp.TextEffect.Text = "Hello";
            
        }
    }
}
