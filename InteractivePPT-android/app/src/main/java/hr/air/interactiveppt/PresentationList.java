package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import hr.air.interactiveppt.entities.ListOfPresentations;
import hr.air.interactiveppt.entities.Presentation;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

public class PresentationList extends AppCompatActivity {
    private ExpandableListView myPptsLV;
    private ExpandableListView subbedPptsLV;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_list);
        String requestType = "get_presentation_list";
        userId = getIntent().getStringExtra("id");

        myPptsLV = (ExpandableListView) findViewById(R.id.my_ppts_listview);
        subbedPptsLV = (ExpandableListView) findViewById(R.id.subbed_ppts_listview);

        CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                        .getPresentationList(userId, requestType),
                new BiConsumer<Call<ListOfPresentations>, Response<ListOfPresentations>>() {
                    @Override
                    public void accept(Call<ListOfPresentations> call, Response<ListOfPresentations> response) {
                        displayPresentations(response.body());
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                new BiConsumer<Call<ListOfPresentations>, Throwable>() {
                    @Override
                    public void accept(Call<ListOfPresentations> call, Throwable throwable) {
                        Toast.makeText(PresentationList.this,"Greška kod dobavljanja liste prezentacija", Toast.LENGTH_LONG).show();
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                true,
                getBaseContext()
        );

    }

    private void displayPresentations(ListOfPresentations presentations) {

        ExpandablePresentationListAdapter adapter = null;

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String accessCode) {
                CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                                .getPresentation(accessCode, "get_presentation"),
                        new BiConsumer<Call<String>, Response<String>>() {
                            @Override
                            public void accept(Call<String> call, Response<String> response) {
                                Intent intent = new Intent(PresentationList.this, ViewPresentation.class);
                                intent.putExtra("code",response.body());
                                startActivity(intent);

                            }
                        },
                        new BiConsumer<Call<String>, Throwable>() {
                            @Override
                            public void accept(Call<String> call, Throwable throwable) {
                                Toast.makeText(PresentationList.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                            }
                        },
                        true,
                        getBaseContext()
                );
            }
        };

        adapter = new ExpandablePresentationListAdapter(this, presentations.myPresentations, consumer);
        myPptsLV.setAdapter(adapter);

        adapter = new ExpandablePresentationListAdapter(this, presentations.subbedPresentations, consumer);
        subbedPptsLV.setAdapter(adapter);
    }
}
