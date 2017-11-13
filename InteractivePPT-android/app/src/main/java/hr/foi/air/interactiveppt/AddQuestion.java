package hr.foi.air.interactiveppt;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
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

    int optionId = -1;
    LinearLayout optionList;

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
                optionId++;
                et.setHint("Odgovor "+(optionId+1));
                et.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                optionList.addView(et);
            }
        });
        addOption.performClick();

        removeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionId != 0) {
                    EditText et = (EditText) optionList.getChildAt(optionId--);
                    optionList.removeView(et);
                }
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
                Question question = new Question(questionName, questionTypeCode, answerRequired);

                switch (ReflectionQtHelper.getInstance().getModeOfQuestionType(questionTypeCode, activity)) {
                    case 1:
                    case 3:
                        for (int i = 0; i < optionList.getChildCount(); i++) {
                            Option option = new Option();
                            option.setOptionText(((EditText) optionList.getChildAt(i)).getText().toString());
                            question.setOptions(option);
                        }
                        break;
                    case 2:
                        break;
                }

                String reasonsOfIncompletion;
                if (!(reasonsOfIncompletion = getReasonsWhyCurrentQuestionIsntComplete(question)).isEmpty()) {
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

    @NonNull
    @Override
    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();

        //bundle.putString("questionName", ((EditText)findViewById(R.id.questionText)).getText().toString());
        //bundle.putBoolean("questionRequired", ((CheckBox)findViewById(R.id.answerRequired)).isChecked());
        //bundle.putInt("questionTypeCode", ((Spinner)findViewById(R.id.spinner)).getSelectedItemPosition());
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < optionList.getChildCount(); i++) {
            answers.add(((EditText) optionList.getChildAt(i)).getText().toString());
        }
        bundle.putStringArrayList("answers", answers);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //((EditText)findViewById(R.id.questionText)).setText(savedInstanceState.getString("questionName"));
        //((CheckBox)findViewById(R.id.answerRequired)).setChecked(savedInstanceState.getBoolean("questionRequired"));
        //((Spinner)findViewById(R.id.spinner)).setSelection(savedInstanceState.getInt("questionTypeCode"));
        optionId -= optionList.getChildCount();
        optionList.removeAllViews();
        for (String answer : savedInstanceState.getStringArrayList("answers")) {
            EditText et = new EditText(this.getContext());
            et.setText(answer);
            et.setHint("Odgovor " + (++optionId + 1));
            optionList.addView(et);
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        ((CreateSurvey)activity).cdd = null;
    }
}
