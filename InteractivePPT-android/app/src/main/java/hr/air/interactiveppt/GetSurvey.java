package hr.air.interactiveppt;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.function.BiConsumer;

import butterknife.ButterKnife;
import hr.air.interactiveppt.entities.Answer;
import hr.air.interactiveppt.entities.ListOfAnswers;
import hr.air.interactiveppt.entities.RetrieveSurvey;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Smrad on 2.12.2016..
 */

public class GetSurvey extends AppCompatActivity{

    static public String requestType="get_survey";
    public String access_code="";
    ArrayList<Answer> answers= new ArrayList<Answer>();
    public int idQuestion;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");

        access_code= getIntent().getExtras().getString("message");

        CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                        .getSurvey(access_code, requestType),
                new BiConsumer<Call<RetrieveSurvey>, Response<RetrieveSurvey>>() {
                    @Override
                    public void accept(Call<RetrieveSurvey> retriveSurveyCall, Response<RetrieveSurvey> retriveSurveyResponse) {
                        displaySurvey(retriveSurveyResponse.body());
                        //Toast.makeText(GetSurvey.this, "Anketa uspješno dobavljena", Toast.LENGTH_LONG).show();
                        //findViewById(R.id.activity_get_survey).setClickable(true);
                        //findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                new BiConsumer<Call<RetrieveSurvey>, Throwable>() {
                    @Override
                    public void accept(Call<RetrieveSurvey> retriveSurveyCall, Throwable throwable) {
                        Toast.makeText(GetSurvey.this,"Greška kod dobavljanja ankete", Toast.LENGTH_LONG).show();
                        findViewById(R.id.activity_get_survey).setClickable(true);
                        //findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                true,
                getBaseContext()
        );
    }//end onCreate

    public void displaySurvey(RetrieveSurvey object){

        ScrollView scrollView = new ScrollView(this);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.com_facebook_button_like_background_color_selected));

        LinearLayout lL = new LinearLayout(this);
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
            lL.addView(question);

            idQuestion=object.questions.get(i).getQuestionId();

            //options
            numberOfOptions=object.questions.get(i).getOptions().size();
            typeOfQuestion=object.questions.get(i).getQuestionType();
            switch (typeOfQuestion){
                case 1:
                    RadioGroup radioGroup = new RadioGroup(this);
                    radioGroup.setId(idQuestion);
                    for (int j=0; j < numberOfOptions; j++) {
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setText(object.questions.get(i).getOptions().get(j).getOptionText());
                        radioButton.setId(object.questions.get(i).getOptions().get(j).getId());
                        radioGroup.addView(radioButton);
                    }
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            boolean existsInArray = false;
                            ListIterator<Answer> iterator = answers.listIterator();
                            while (iterator.hasNext()) {
                                Answer temp = iterator.next();
                                if (temp.getIdQuest() == group.getId()) {
                                    iterator.remove();
                                    temp = new Answer(group.getId(), id, checkedId);
                                    answers.add(temp);
                                    existsInArray = true;
                                    break;
                                }
                            }
                            if (!existsInArray) {
                                Answer answer = new Answer(group.getId(), id, checkedId);
                                answers.add(answer);
                            }
                        }
                    });
                    lL.addView(radioGroup);
                    break;

                case 2:
                    for (int j=0; j < numberOfOptions; j++) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setText(object.questions.get(i).getOptions().get(j).getOptionText());
                        checkBox.setId(object.questions.get(i).getOptions().get(j).getId());
                        lL.addView(checkBox);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                ListIterator<Answer> iterator = answers.listIterator();
                                if(isChecked){
                                    Answer answer = new Answer(idQuestion,id,buttonView.getId());
                                    answers.add(answer);
                                }
                                else{
                                    while (iterator.hasNext()){
                                        Answer temp= iterator.next();
                                        if(temp.getIdOption() == buttonView.getId()){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                        });
                    }
                    break;
                case 3:
                    EditText editText= new EditText(this);
                    editText.setSingleLine(false);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    editText.setLines(5);
                    editText.setMaxLines(10);
                    lL.addView(editText);
                    InputFilter[] inputFilters = new InputFilter[1];
                    inputFilters[0] = new InputFilter.LengthFilter(50);
                    editText.setFilters(inputFilters);
            }
        }

        Button button= new Button(this);
        button.setText("Pošalji odgovore");
        button.setOnClickListener(onClick(button));
        lL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lL.setGravity(Gravity.CENTER);
        lL.addView(button);

        scrollView.addView(lL);
        relativeLayout.addView(scrollView);
        //setContentView(scrollView);
        setContentView(relativeLayout);
    }//end displaySurvey

    View.OnClickListener onClick(final Button button){
        return new View.OnClickListener(){
            public void onClick(View view){
                ListOfAnswers listOfAnswers= new ListOfAnswers(answers);
                sendAnswersToServer(listOfAnswers);
            }
        };
    }

    public void sendAnswersToServer(ListOfAnswers answers){
        String request= "submit_answers";

        CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                        .sendAnswersToServer(
                                answers,
                                request),
                new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                    @Override
                    public void accept(Call<Boolean> answerCall, Response<Boolean> answerResponse) {
                        Toast.makeText(GetSurvey.this,
                                "Odgovori uspješno poslani!",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                },
                new BiConsumer<Call<Boolean>, Throwable>() {
                    @Override
                    public void accept(Call<Boolean> answerCall, Throwable throwable) {
                        Toast.makeText(GetSurvey.this,
                                "Greška prilikom slanja odgovora",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                },
                true,
                getBaseContext()
        );
    }
}//end class
