package hr.air.interactiveppt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.BiConsumer;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

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

        LinearLayout lL = new LinearLayout(this);
        lL.setOrientation(LinearLayout.VERTICAL);
        //name
        TextView surveyName = new TextView(GetSurvey.this);
        surveyName.setText(object.name);
        lL.addView(surveyName);

        //description
        TextView surveyDescription= new TextView(GetSurvey.this);
        surveyDescription.setText(object.description);
        lL.addView(surveyDescription);

        //questions
        int numberOfQuestions= object.questions.size();
        int numberOfOptions=0;
        int typeOfQuestion=0;

        for (int i=0 ; i < numberOfQuestions; i++){
            TextView question = new TextView(this);
            question.setText(object.questions.get(i).getQuestionText());
            lL.addView(question);
            //options
            numberOfOptions=object.questions.get(i).getOptions().size();
            typeOfQuestion=object.questions.get(i).getQuestionType();
            switch (typeOfQuestion){
                case 1:
                    for (int j=0; j < numberOfOptions; j++) {
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setText(object.questions.get(i).getOptions().get(j).getOptionText());
                        lL.addView(radioButton);
                        // TODO: 5.12.2016. implementirati da može biti samo 1 radio button označen u jedno vrijeme  
                    }
                    break;
                case 2:
                    for (int j=0; j < numberOfOptions; j++) {
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setText(object.questions.get(i).getOptions().get(j).getOptionText());
                        lL.addView(checkBox);
                    }
                    break;
                case 3:
                    EditText editText= new EditText(this);
                    editText.setSingleLine(false);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    editText.setLines(5);
                    editText.setMaxLines(10);
                    lL.addView(editText);
                    // TODO: 5.12.2016. dodati input filter za ograničavanje broja znakova 
            }
        }

        Button button= new Button(this);
        button.setText("Pošalji odgovore");
        lL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lL.setGravity(Gravity.CENTER);
        lL.addView(button);
        // TODO: 5.12.2016. implementirati slanje podataka na server 

        scrollView.addView(lL);

        setContentView(scrollView);
    }//end displaySurvey
}//end class
