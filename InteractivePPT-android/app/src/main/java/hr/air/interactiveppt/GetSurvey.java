package hr.air.interactiveppt;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import hr.air.interactiveppt.entities.Answer;
import hr.air.interactiveppt.entities.ListOfAnswers;
import hr.air.interactiveppt.entities.SurveyWithQuestions;
import hr.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;

/**
 * Created by Smrad on 2.12.2016..
 */

public class GetSurvey extends AppCompatActivity{

    private int idQuestion;
    String idUser;
    LinearLayout lL;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_survey);
        idUser = getIntent().getStringExtra("id");
        String serializedSurveyWithQuestions = getIntent().getStringExtra("full_survey");
        SurveyWithQuestions survey = new Gson().fromJson(serializedSurveyWithQuestions, SurveyWithQuestions.class);
        displaySurvey(survey);
}//end onCreate

    public void displaySurvey(SurveyWithQuestions object){

        ScrollView scrollView = new ScrollView(this);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.com_facebook_button_like_background_color_selected));

        lL = new LinearLayout(this);
        lL.setOrientation(LinearLayout.VERTICAL);
        //name
        TextView surveyName = new TextView(GetSurvey.this);
        surveyName.setText(object.name);
        surveyName.setTextColor(Color.BLACK);
        surveyName.setTextSize(16);
        surveyName.setTypeface(Typeface.DEFAULT_BOLD);
        lL.addView(surveyName);

        //description
        TextView surveyDescription= new TextView(GetSurvey.this);
        surveyDescription.setText(object.description);
        surveyDescription.setTextColor(Color.BLACK);
        surveyDescription.setTextSize(16);
        lL.addView(surveyDescription);

        //questions
        int numberOfQuestions= object.questions.size();
        int numberOfOptions=0;
        int typeOfQuestion=0;

        for (int i=0 ; i < numberOfQuestions; i++){
            TextView question = new TextView(this);
            question.setText(object.questions.get(i).getQuestionText());
            question.setTextColor(Color.BLACK);
            TextView requiredSign = new TextView(this);
            requiredSign.setId(object.questions.get(i).getQuestionType());
            if (object.questions.get(i).getRequiredAnswer()==1) {
                requiredSign.setText("*");
                requiredSign.setTextColor(Color.RED);
            }
            LinearLayout questionWithRequiredSign = new LinearLayout(this);
            questionWithRequiredSign.setOrientation(LinearLayout.HORIZONTAL);
            questionWithRequiredSign.addView(question);
            questionWithRequiredSign.addView(requiredSign);
            lL.addView(questionWithRequiredSign);

            idQuestion=object.questions.get(i).getQuestionId();

            //options
            numberOfOptions=object.questions.get(i).getOptions().size();
            typeOfQuestion=object.questions.get(i).getQuestionType();
            switch (typeOfQuestion){
                case 1:
                    final RadioGroup radioGroup = new RadioGroup(this);
                    radioGroup.setId(idQuestion);
                    for (int j=0; j < numberOfOptions; j++) {
                        RadioButton radioButton = object.questions.get(i).getRequiredAnswer()==1 ? new RadioButton(this) : new hr.air.interactiveppt.ToggleableRadioButton(this);
                        radioButton.setText(object.questions.get(i).getOptions().get(j).getOptionText());
                        radioButton.setId(j);
                        radioGroup.addView(radioButton);
                    }
                    lL.addView(radioGroup);
                    break;
                case 2:
                    LinearLayout checkBoxGroup = new LinearLayout(this);
                    checkBoxGroup.setOrientation(LinearLayout.VERTICAL);
                    checkBoxGroup.setId(idQuestion);
                    for (int j=0; j < numberOfOptions; j++) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setText(object.questions.get(i).getOptions().get(j).getOptionText());
                        checkBox.setId(j);
                        checkBoxGroup.addView(checkBox);
                    }
                    lL.addView(checkBoxGroup);
                    break;
                case 3:
                    EditText editText= new EditText(this);
                    editText.setId(idQuestion);
                    editText.setSingleLine(false);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    editText.setLines(5);
                    editText.setMaxLines(10);
                    lL.addView(editText);
                    InputFilter[] inputFilters = new InputFilter[1];
                    inputFilters[0] = new InputFilter.LengthFilter(100);
                    editText.setFilters(inputFilters);
            }
        }

        Button button= new Button(this);
        button.setText("Pošalji odgovore");
        button.setOnClickListener(onClick(button));
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        buttonLayoutParams.setMargins(30, 5, 30, 20);
        button.setLayoutParams(buttonLayoutParams);
        lL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lL.setGravity(Gravity.CENTER);
        lL.addView(button);

        scrollView.addView(lL);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        relativeLayout.addView(scrollView);
        //setContentView(scrollView);
        setContentView(relativeLayout);
    }//end displaySurvey

    View.OnClickListener onClick(final Button button){
        return new View.OnClickListener(){
            public void onClick(View view){
                ListOfAnswers listOfAnswers = getAllDefinedAnswers();
                if (listOfAnswers != null) {
                    sendAnswersToServer(listOfAnswers);
                }
                else {
                    Toast.makeText(GetSurvey.this, "Nije moguće predati odgovore dok nije odgovoreno na sva obvezna pitanja!", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private ListOfAnswers getAllDefinedAnswers() {
        ArrayList<Answer> answers = new ArrayList<>();
        int numOfQuestions = (lL.getChildCount() - 3) / 2;  //in children are surveyName, surveyDesc, submitButton and N pairs of question names and question option groups
        for (int i=0; i < numOfQuestions; i++) {
            short numOfProvidedOptions = 0;
            LinearLayout ithQuestion = ((LinearLayout)lL.getChildAt(2 + 2*i));
            Object ithOptionGroup = lL.getChildAt(2 + 2*i + 1);
            TextView requiredSign = ((TextView)ithQuestion.getChildAt(1));
            boolean requiredQuestion = requiredSign.getText()=="*";
            int questionType = requiredSign.getId();
            int questionId = ((View)ithOptionGroup).getId();
            switch (questionType) {
                case 1:
                    int selectedOptionId = ((RadioGroup)ithOptionGroup).getCheckedRadioButtonId();
                    if (selectedOptionId != -1) {
                        answers.add(new Answer(questionId, ((RadioButton) ((RadioGroup) ithOptionGroup).getChildAt(selectedOptionId)).getText().toString()));
                        numOfProvidedOptions++;
                    }
                    break;
                case 2:
                    LinearLayout checkBoxGroup = ((LinearLayout)ithOptionGroup);
                    int numOfOptions = checkBoxGroup.getChildCount();
                    for (int j=0; j < numOfOptions; j++) {
                        if (((CheckBox)checkBoxGroup.getChildAt(j)).isChecked()) {
                            answers.add(new Answer(questionId, ((CheckBox)checkBoxGroup.getChildAt(j)).getText().toString()));
                            numOfProvidedOptions++;
                        }
                    }
                    break;
                case 3:
                    EditText textEditField = ((EditText)ithOptionGroup);
                    if (!textEditField.getText().toString().isEmpty()) {
                        answers.add(new Answer(questionId, textEditField.getText().toString()));
                        numOfProvidedOptions++;
                    }
                    break;
                default:
                    break;
            }
            if (requiredQuestion && numOfProvidedOptions == 0) {
                return null;
            }
        }
        return new ListOfAnswers(answers, idUser);
    }

    private void sendAnswersToServer(ListOfAnswers answers){
        String request= "submit_answers";

        new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                        .sendAnswers(
                                new Gson().toJson(answers), // i've no f***ing idea why the heck i have to serialize it manually
                                request),
                new SendDataAndProcessResponseTask.PostActions() {

                    @Override
                    public void onSuccess(Object genericResponse) {
                        boolean response = (boolean) genericResponse;
                        if (response) {
                            Toast.makeText(GetSurvey.this,
                                    "Odgovori uspješno poslani!",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(GetSurvey.this,
                                    "Već ste prethodno predali odgovore na ovu anketu!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(GetSurvey.this,
                                "Greška prilikom slanja odgovora",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}//end class
