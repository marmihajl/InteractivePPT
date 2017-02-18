package hr.foi.air.interactiveppt;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import hr.foi.air.interactiveppt.entities.Option;
import hr.foi.air.interactiveppt.entities.Question;
import hr.foi.air.interactiveppt.questiontype.ReflectionQtHelper;

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

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.questionTypesDisplayNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Button addOption =(Button)findViewById(R.id.btnAddOption);
        Button removeOption = (Button)findViewById(R.id.btnRemoveOption);
        optionList = (LinearLayout)findViewById(R.id.optionList);

        addOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = new EditText(getContext());
                if (android.os.Build.VERSION.SDK_INT < 11) {    //because lower API versions do not support Alpha channels/transparency
                    et.setBackgroundColor(activity.getResources().getColor(R.color.colorAddQuestionBackground));
                }
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
                int questionTypeCode = spinner.getSelectedItemPosition() + 1;

                String questionName = ((EditText)findViewById(R.id.questionText)).getText().toString();
                int answerRequired = ((CheckBox)findViewById(R.id.answerRequired)).isChecked() ? 1 : 0;
                Question question = new Question(id, questionName, questionTypeCode, answerRequired);
                id++;

                switch (ReflectionQtHelper.getInstance().getModeOfQuestionType(questionTypeCode, activity)) {
                    case 1:
                    case 3:
                        for (int i = 0; i < optionList.getChildCount(); i++) {
                            View view = optionList.getChildAt(i);
                            if (view instanceof EditText) {
                                Option option = new Option();
                                option.setOptionText(((EditText) view).getText().toString());
                                question.setOptions(option);
                            }
                        }
                        break;
                    case 2:
                        break;
                }

                String reasonsOfIncompletion;
                if ((reasonsOfIncompletion = getReasonsWhyCurrentQuestionIsntComplete(question)) != "") {
                    Toast.makeText(getContext(), "Neuspjeh kod kreiranja:" + reasonsOfIncompletion, Toast.LENGTH_LONG).show();
                    return;
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int questionTypeCode = position + 1;
                switch (ReflectionQtHelper.getInstance().getModeOfQuestionType(questionTypeCode, activity)) {
                    case 2:
                        optionList.setVisibility(View.INVISIBLE);
                        (findViewById(R.id.pumpOrDumpOptions)).setVisibility(View.INVISIBLE);
                        break;
                    default:
                        optionList.setVisibility(View.VISIBLE);
                        (findViewById(R.id.pumpOrDumpOptions)).setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getReasonsWhyCurrentQuestionIsntComplete(Question question) {
        String reasonsOfIncompletion = "";
        if (question.getQuestionText().isEmpty()) {
            reasonsOfIncompletion += "\nTekst pitanja ne može biti prazan!";
        }
        if (question.getQuestionType() != 3) {
            if (question.options.size() == 0) {
                reasonsOfIncompletion += "\nZa odabrani tip pitanja trebaju biti definirane opcije!";
            }
            else {
                for (Option option : question.options) {
                    if (option.getOptionText().isEmpty()) {
                        reasonsOfIncompletion += "\nPostoje definirane opcije bez teksta!";
                        break;
                    }
                }
            }
        }
        return reasonsOfIncompletion;
    }

}
