package hr.air.interactiveppt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import hr.air.interactiveppt.entities.ListOfPresentations;
import hr.air.interactiveppt.entities.ListOfSurveys;
import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.entities.SurveyWithQuestions;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

public class SurveyList extends AppCompatActivity {
    private ExpandableListView surveysLV;
    private String userId;
    private String pptPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        findViewById(R.id.activity_survey_list).setClickable(false);
        String requestType = "get_survey_list";
        userId = getIntent().getStringExtra("id");
        pptPath = getIntent().getStringExtra("ppt_path");

        surveysLV = (ExpandableListView) findViewById(R.id.survey_listview);

        CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                        .getSurveyList(requestType, pptPath),
                new BiConsumer<Call<ListOfSurveys>, Response<ListOfSurveys>>() {
                    @Override
                    public void accept(Call<ListOfSurveys> call, Response<ListOfSurveys> response) {
                        displaySurveys(response.body());
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_survey_list).setClickable(true);
                    }
                },
                new BiConsumer<Call<ListOfSurveys>, Throwable>() {
                    @Override
                    public void accept(Call<ListOfSurveys> call, Throwable throwable) {
                        Toast.makeText(SurveyList.this,"Greška kod dobavljanja liste anketa", Toast.LENGTH_LONG).show();
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_survey_list).setClickable(true);
                    }
                },
                true,
                getBaseContext()
        );
    }

    private void displaySurveys(ListOfSurveys listOfSurveys) {

        ExpandableSurveyListAdapter adapter = null;

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String idSurvey) {
                findViewById(R.id.activity_survey_list).setClickable(false);
                findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);

                CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                                .getSurvey(idSurvey, "get_survey"),
                        new BiConsumer<Call<SurveyWithQuestions>, Response<SurveyWithQuestions>>() {
                            @Override
                            public void accept(Call<SurveyWithQuestions> call, Response<SurveyWithQuestions> response) {
                                Intent intent = new Intent(SurveyList.this, GetSurvey.class);
                                intent.putExtra("full_survey", new Gson().toJson(response.body()));
                                intent.putExtra("id", userId);
                                findViewById(R.id.activity_survey_list).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                startActivity(intent);
                            }
                        },
                        new BiConsumer<Call<SurveyWithQuestions>, Throwable>() {
                            @Override
                            public void accept(Call<SurveyWithQuestions> call, Throwable throwable) {
                                Toast.makeText(SurveyList.this,"Greška kod dobavljanja ankete!", Toast.LENGTH_LONG).show();
                                findViewById(R.id.activity_survey_list).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            }
                        },
                        true,
                        getBaseContext()
                );
            }
        };

        adapter = new ExpandableSurveyListAdapter(this, listOfSurveys.surveys, consumer);
        surveysLV.setAdapter(adapter);
    }
}
