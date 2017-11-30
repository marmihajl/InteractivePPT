using System.Windows.Forms;

namespace InteractivePPT
{
    public partial class PanelWithPerformScrollMethod : Panel
    {
        public void PerformScroll(MouseEventArgs e)
        {
            base.OnMouseWheel(e);
        }

    }
}
