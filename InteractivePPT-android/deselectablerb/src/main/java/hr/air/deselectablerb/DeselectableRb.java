package hr.air.deselectablerb;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by zeko868 on 26.12.2016..
 */

public class DeselectableRb extends RadioButton {
    public DeselectableRb(Context context) {
        super(context);
    }

    @Override
    public void toggle() {
        if(isChecked()) {
            if(getParent() instanceof RadioGroup) {
                ((RadioGroup)getParent()).clearCheck();
            }
        } else {
            setChecked(true);
        }
    }
}