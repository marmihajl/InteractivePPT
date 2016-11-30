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

    int answerId = 1;
    LinearLayout answerList;
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


        Button addAnswer =(Button)findViewById(R.id.btnAddAnswer);
        Button removeAnswer = (Button)findViewById(R.id.btnRemoveAnswer);
        answerList = (LinearLayout)findViewById(R.id.answerList);

        addAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = new EditText(getContext());
                et.setId(answerId++);
                et.setHint("Odgovor "+(answerId+1));
                answerList.addView(et);
            }
        });

        removeAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)answerList.getChildAt(answerId--);
                answerList.removeView(et);
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
                for (int i = 0; i < answerList.getChildCount(); i++){
                    View view = answerList.getChildAt(i);
                    if(view instanceof EditText){
                        Option answer = new Option();
                        answer.setOptionText(((EditText) view).getText().toString());
                        question.setOptions(answer);
                    }
                }
                questions.add(question);
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
