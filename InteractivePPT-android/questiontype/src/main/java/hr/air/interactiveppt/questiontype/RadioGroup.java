package hr.air.interactiveppt.questiontype;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

import hr.air.deselectablerb.DeselectableRb;
import hr.air.interactiveppt.entities.Option;

/**
 * Created by zeko868 on 29.1.2017..
 */

public class RadioGroup extends android.widget.RadioGroup implements QuestionTypeControl {

    public static final int code = 1;
    public static final int mode = QuestionTypeMode.DEFAULT_ONLY;
    public final String displayName;

    private void initializeRadioButtons(Context context, ArrayList<Option> options) {
        int numOfOptions = options.size();
        for (int i=0; i<numOfOptions; i++) {
            DeselectableRb deselectableRb = new DeselectableRb(context);
            deselectableRb.setText(options.get(i).getOptionText());
            deselectableRb.setId(i);
            this.addView(deselectableRb);
        }
    }

    public RadioGroup(Context context, ArrayList<Option> options) {
        super(context);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        initializeRadioButtons(context, options);
    }

    public RadioGroup(Context context, AttributeSet attrs, ArrayList<Option> options) {
        super(context, attrs);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        initializeRadioButtons(context, options);
    }

    @Override
    public ArrayList<String> getSelected() {
        ArrayList<String> retVal = new ArrayList<>();
        if (!this.isBlank()) {
            retVal.add(((DeselectableRb)this.getChildAt(this.getCheckedRadioButtonId())).getText().toString());
        }
        return retVal;
    }

    @Override
    public void leaveBlank() {
        if (!this.isBlank()) {
            ((DeselectableRb)this.getChildAt(this.getCheckedRadioButtonId())).toggle();
        }
    }

    @Override
    public boolean isBlank() {
        return this.getCheckedRadioButtonId() == -1;
    }
}
