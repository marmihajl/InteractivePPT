package hr.foi.air.interactiveppt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import hr.foi.air.interactiveppt.entities.ListOfSurveys;
import hr.foi.air.interactiveppt.entities.SurveyWithQuestions;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

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

        new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                .getSurveyList(requestType, pptPath),
                new SendDataAndProcessResponseTask.PostActions() {
                    @Override
                    public void onSuccess(Object genericResponse) {
                        ListOfSurveys response = (ListOfSurveys) genericResponse;
                        displaySurveys(response);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_survey_list).setClickable(true);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(SurveyList.this, R.string.survey_fetch_error, Toast.LENGTH_LONG).show();
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_survey_list).setClickable(true);
                    }
                }
        );
    }

    private void displaySurveys(ListOfSurveys listOfSurveys) {

        ExpandableSurveyListAdapter adapter = null;

        OnProceedButtonClicked consumer = new OnProceedButtonClicked() {
            @Override
            public void onClick(String idSurvey) {
                findViewById(R.id.activity_survey_list).setClickable(false);
                findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);

                new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                        .getSurvey(idSurvey, "get_survey"),
                        new SendDataAndProcessResponseTask.PostActions() {
                            @Override
                            public void onSuccess(Object genericResponse) {
                                SurveyWithQuestions response = (SurveyWithQuestions) genericResponse;
                                Intent intent = new Intent(SurveyList.this, GetSurvey.class);
                                intent.putExtra("full_survey", new Gson().toJson(response));
                                intent.putExtra("id", userId);
                                findViewById(R.id.activity_survey_list).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(SurveyList.this, R.string.survey_fetch_error, Toast.LENGTH_LONG).show();
                                findViewById(R.id.activity_survey_list).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            }
                        }
                );
            }
        };

        adapter = new ExpandableSurveyListAdapter(this, listOfSurveys.surveys, consumer);
        surveysLV.setAdapter(adapter);
    }
}
