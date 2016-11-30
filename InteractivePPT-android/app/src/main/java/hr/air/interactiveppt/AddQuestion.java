package hr.air.interactiveppt;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import hr.air.interactiveppt.entities.Option;
import hr.air.interactiveppt.entities.Question;

/**
 * Created by marin on 9.11.2016..
 */

public class AddQuestion extends Dialog {

    ArrayList<Question> questions;
    Activity activity;
    public AddQuestion(Activity a, ArrayList<Question> q) {
        super(a);
        activity = a;
        questions = q;
    }

    int optionId = 1;
    LinearLayout optionList;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_question);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Button addOption =(Button)findViewById(R.id.btnAddOption);
        Button removeOption = (Button)findViewById(R.id.btnRemoveOption);
        optionList = (LinearLayout)findViewById(R.id.optionList);

        addOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = new EditText(getContext());
                et.setId(optionId++);
                et.setHint("Odgovor "+(optionId+1));
                optionList.addView(et);
            }
        });

        removeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)optionList.getChildAt(optionId--);
                optionList.removeView(et);
            }
        });

        Button save = (Button)findViewById(R.id.btnSave);
        Button cancel = (Button)findViewById(R.id.btnCancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = 0;
                switch (((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString()){
                    case "Single choice":
                        type = 1;
                        break;
                    case "Multiple choice":
                        type = 2;
                        break;
                    case "Text edit":
                        type = 3;
                        break;
                }
                EditText editText = (EditText)findViewById(R.id.questionText);
                Question question = new Question(id, editText.getText().toString(),type);
                id++;
                for (int i = 0; i < optionList.getChildCount(); i++){
                    View view = optionList.getChildAt(i);
                    if(view instanceof EditText){
                        Option option = new Option();
                        option.setOptionText(((EditText) view).getText().toString());
                        question.setOptions(option);
                    }
                }
                questions.add(question);
                ((CreateSurvey)activity).loadData();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
