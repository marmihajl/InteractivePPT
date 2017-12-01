package hr.foi.air.interactiveppt;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import hr.foi.air.interactiveppt.entities.Answer;
import hr.foi.air.interactiveppt.entities.ListOfAnswers;
import hr.foi.air.interactiveppt.entities.SurveyWithQuestions;
import hr.foi.air.interactiveppt.questiontype.QuestionTypeControl;
import hr.foi.air.interactiveppt.questiontype.RadioGroup;
import hr.foi.air.interactiveppt.questiontype.ReflectionQtHelper;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

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
        int typeOfQuestion=0;

        for (int i=0 ; i < numberOfQuestions; i++){
            TextView question = new TextView(this);
            question.setText(String.valueOf(i+1) + ") " + object.questions.get(i).getQuestionText());
            question.setTextColor(Color.BLACK);
            TextView requiredSign = new TextView(this);
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
            typeOfQuestion=object.questions.get(i).getQuestionType();

            View inputControl = (View) ReflectionQtHelper.getInstance().instatiateControl(typeOfQuestion, this, object.questions.get(i).getOptions());
            inputControl.setId(idQuestion);
            lL.addView(inputControl);
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

        Button button2= new Button(this);
        button2.setText("Obriši sve");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(getIntent());
                
            }
        });
        LinearLayout.LayoutParams buttonLayoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        buttonLayoutParams2.setMargins(30, 5, 30, 20);
        button2.setLayoutParams(buttonLayoutParams2);
        lL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lL.setGravity(Gravity.CENTER);
        lL.addView(button2);

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
                    Toast.makeText(GetSurvey.this, R.string.survey_not_filled_error, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private ListOfAnswers getAllDefinedAnswers() {
        ArrayList<Answer> answers = new ArrayList<>();
        int numOfQuestions = (lL.getChildCount() - 3) / 2;  //in children are surveyName, surveyDesc, submitButton and N pairs of question names and question option groups
        for (int i=0; i < numOfQuestions; i++) {
            LinearLayout ithQuestion = ((LinearLayout)lL.getChildAt(2 + 2*i));
            Object ithOptionGroup = lL.getChildAt(2 + 2*i + 1);
            TextView requiredSign = ((TextView)ithQuestion.getChildAt(1));
            boolean requiredQuestion = requiredSign.getText()=="*";
            int questionId = ((View)ithOptionGroup).getId();

            QuestionTypeControl inputControl = (QuestionTypeControl) ithOptionGroup;
            for (String selectedOption : inputControl.getSelected()) {
                if (!selectedOption.isEmpty()) {
                    answers.add(new Answer(questionId, selectedOption));
                }
            }

            if (requiredQuestion && inputControl.isBlank()) {
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
                                    R.string.answers_sent_successfully,
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(GetSurvey.this,
                                    R.string.answers_already_submitted_error,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(GetSurvey.this,
                                R.string.send_answers_error,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}//end class
