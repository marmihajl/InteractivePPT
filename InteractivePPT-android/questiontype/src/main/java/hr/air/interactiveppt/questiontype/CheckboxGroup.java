package hr.air.interactiveppt.questiontype;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hr.air.interactiveppt.entities.Option;

/**
 * Created by zeko868 on 29.1.2017..
 */

public class CheckboxGroup extends LinearLayout implements QuestionTypeControl {

    public static final int code = 2;
    public static final int mode = QuestionTypeMode.DEFAULT_ONLY;
    public final String displayName;
    private int numOfOptions;

    private void initializeRadioButtons(Context context, ArrayList<Option> options) {
        numOfOptions = options.size();
        this.setOrientation(LinearLayout.VERTICAL);
        for (int i=0; i<numOfOptions; i++) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(options.get(i).getOptionText());
            checkBox.setId(i);
            this.addView(checkBox);
        }
    }

    public CheckboxGroup(Context context, ArrayList<Option> options) {
        super(context);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        initializeRadioButtons(context, options);
    }

    public CheckboxGroup(Context context, AttributeSet attrs, ArrayList<Option> options) {
        super(context, attrs);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        initializeRadioButtons(context, options);
    }

    public CheckboxGroup(Context context, AttributeSet attrs, int defStyleAttr, ArrayList<Option> options) {
        super(context, attrs, defStyleAttr);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        initializeRadioButtons(context, options);
    }

    public CheckboxGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, ArrayList<Option> options) {
        super(context, attrs, defStyleAttr, defStyleRes);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        initializeRadioButtons(context, options);
    }

    @Override
    public ArrayList<String> getSelected() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i=0; i<numOfOptions; i++) {
            if (((CheckBox)this.getChildAt(i)).isChecked()) {
                retVal.add(((CheckBox)this.getChildAt(i)).getText().toString());
            }
        }
        return retVal;
    }

    @Override
    public void leaveBlank() {
        for (int i=0; i<numOfOptions; i++) {
            ((CheckBox)this.getChildAt(i)).setChecked(false);
        }
    }

    @Override
    public boolean isBlank() {
        for (int i=0; i<numOfOptions; i++) {
            if (((CheckBox)this.getChildAt(i)).isChecked()) {
                return false;
            }
        }
        return true;
    }
}
