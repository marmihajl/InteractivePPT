package hr.air.interactiveppt.questiontype;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;

import java.util.ArrayList;

import hr.air.interactiveppt.entities.Option;

/**
 * Created by zeko868 on 29.1.2017..
 */

public class EditText extends android.widget.EditText implements QuestionTypeControl {

    public static final int code = 3;
    public static final int mode = QuestionTypeMode.SPECIFIC_ONLY;
    public final String displayName;

    private void setDefaultStyles() {
        this.setSingleLine(false);
        this.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        this.setLines(5);
        this.setMaxLines(10);
        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = new InputFilter.LengthFilter(100);
        this.setFilters(inputFilters);
    }

    public EditText(Context context, ArrayList<Option> options) {
        super(context);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        setDefaultStyles();
    }

    public EditText(Context context, AttributeSet attrs, ArrayList<Option> options) {
        super(context, attrs);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        setDefaultStyles();
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr, ArrayList<Option> options) {
        super(context, attrs, defStyleAttr);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        setDefaultStyles();
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, ArrayList<Option> options) {
        super(context, attrs, defStyleAttr, defStyleRes);
        displayName = getResources().getStringArray(R.array.questionTypesDisplayNames)[code-1];
        setDefaultStyles();
    }

    @Override
    public ArrayList<String> getSelected() {
        ArrayList<String> retVal = new ArrayList<String>();
        if (!this.getText().toString().isEmpty()) {
            retVal.add(this.getText().toString());
        }
        return retVal;
    }

    @Override
    public void leaveBlank() {
        this.setText("");
    }

    @Override
    public boolean isBlank() {
        return this.getText().toString().isEmpty();
    }
}
