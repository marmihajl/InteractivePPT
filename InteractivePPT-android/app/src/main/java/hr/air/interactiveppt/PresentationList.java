package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import hr.air.interactiveppt.entities.ListOfPresentations;
import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;

public class PresentationList extends AppCompatActivity {
    private ExpandableListView myPptsLV;
    private ExpandableListView subbedPptsLV;
    private String userId;
    private String text = "close";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_list);
        findViewById(R.id.activity_presentation_list).setClickable(false);
        String requestType = "get_presentation_list";
        userId = getIntent().getStringExtra("id");

        myPptsLV = (ExpandableListView) findViewById(R.id.my_ppts_listview);
        subbedPptsLV = (ExpandableListView) findViewById(R.id.subbed_ppts_listview);

        new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                .getPresentationList(userId, requestType),
                new SendDataAndProcessResponseTask.PostActions() {
                    @Override
                    public void onSuccess(Object genericResponse) {
                        ListOfPresentations response = (ListOfPresentations) genericResponse;
                        displayPresentations(response);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_presentation_list).setClickable(true);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(PresentationList.this,"Greška kod dobavljanja liste prezentacija", Toast.LENGTH_LONG).show();
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_presentation_list).setClickable(true);
                    }
                }
        );
    }

    private void displayPresentations(ListOfPresentations presentations) {

        ExpandablePresentationListAdapter adapter = null;

        OnProceedButtonClicked consumer = new OnProceedButtonClicked() {
            @Override
            public void onClick(String accessCode) {
                findViewById(R.id.activity_presentation_list).setClickable(false);
                findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);

                new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                        .getPresentation(accessCode, "get_presentation"),
                        new SendDataAndProcessResponseTask.PostActions() {
                            @Override
                            public void onSuccess(Object genericResponse) {
                                PresentationWithSurveys response = (PresentationWithSurveys) genericResponse;
                                if (response != null) {
                                    Intent intent = new Intent(PresentationList.this, ViewPresentation.class);
                                    intent.putExtra("id", userId);
                                    intent.putExtra("manual_open", text);
                                    intent.putExtra("serialized_presentation", new Gson().toJson(response));
                                    findViewById(R.id.activity_presentation_list).setClickable(true);
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    startActivity(intent);
                                }
                                else {
                                    findViewById(R.id.activity_input_code).setClickable(true);
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    Toast.makeText(PresentationList.this,"Prezentacija s navedenim pristupnim kodom više ne postoji!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure() {
                                findViewById(R.id.activity_presentation_list).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                Toast.makeText(PresentationList.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        };

        adapter = new ExpandablePresentationListAdapter(this, presentations.myPresentations, consumer);
        myPptsLV.setAdapter(adapter);

        adapter = new ExpandablePresentationListAdapter(this, presentations.subbedPresentations, consumer);
        subbedPptsLV.setAdapter(adapter);
    }
}
