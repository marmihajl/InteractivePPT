package hr.air.interactiveppt;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by zeko868 on 26.12.2016..
 */

public class ToggleableRadioButton extends RadioButton {
    public ToggleableRadioButton(Context context) {
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