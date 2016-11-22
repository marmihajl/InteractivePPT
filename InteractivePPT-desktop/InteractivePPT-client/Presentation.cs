using System;
using System.Collections.Specialized;
using System.Net;
using System.Windows.Forms;
using PowerPoint = Microsoft.Office.Interop.PowerPoint;
namespace InteractivePPT
{
    public partial class Presentation : Form
    {
        PowerPoint.Application pptApp = new PowerPoint.Application();
        string path;
        PowerPoint.Presentation p = null;

        public Presentation(string path)
        {
            InitializeComponent();
            this.path = path;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            PowerPoint.SlideRange ppSR = pptApp.ActiveWindow.Selection.SlideRange;
            PowerPoint.Shape ppSharp = ppSR.Shapes.AddLabel(Microsoft.Office.Core.MsoTextOrientation.msoTextOrientationHorizontal, 0, 0, 200, 25);

            ppSharp.TextEffect.Text = "Hello";
            p.Save();
            string name = path.Substring(path.LastIndexOf('\\') + 1);
            using (WebClient client = new WebClient())
            {
                try
                {
                    byte[] response =
                    client.UploadValues("http://46.101.68.86/interactivePPT-server.php", new NameValueCollection()
                    {
                       { "request_type", "delete_file" },
                       { "file", name }
                    });
                }
                catch
                {
                    MessageBox.Show("Communication with server-side of this application could not be established");
                    return;
                }

            }

            FileClass.uploadFile(path);
        }

        private void Presentation_Load(object sender, EventArgs e)
        {
            
            pptApp.Visible = Microsoft.Office.Core.MsoTriState.msoTrue;
            pptApp.Activate();

            PowerPoint.Presentations ps = pptApp.Presentations;
            p = ps.Open(path,
                        Microsoft.Office.Core.MsoTriState.msoFalse, Microsoft.Office.Core.MsoTriState.msoFalse, Microsoft.Office.Core.MsoTriState.msoTrue);
        }
    }
}
