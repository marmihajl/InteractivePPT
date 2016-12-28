package hr.air.interactiveppt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.function.BiConsumer;

import hr.air.interactiveppt.entities.ListOfPresentations;
import hr.air.interactiveppt.entities.Presentation;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

public class PresentationList extends AppCompatActivity {
    private RecyclerView myPptsRecycler;
    private RecyclerView subbedPptsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_list);
        String requestType = "get_presentation_list";
        String userId = getIntent().getStringExtra("id");

        myPptsRecycler = (RecyclerView) findViewById(R.id.my_ppts_recycler);
        subbedPptsRecycler = (RecyclerView) findViewById(R.id.subbed_ppts_recycler);

        CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                        .getPresentationList(userId, requestType),
                new BiConsumer<Call<ListOfPresentations>, Response<ListOfPresentations>>() {
                    @Override
                    public void accept(Call<ListOfPresentations> call, Response<ListOfPresentations> response) {
                        displayPresentations(response.body());
                        findViewById(R.id.activity_presentation_list).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                new BiConsumer<Call<ListOfPresentations>, Throwable>() {
                    @Override
                    public void accept(Call<ListOfPresentations> call, Throwable throwable) {
                        Toast.makeText(PresentationList.this,"Greška kod dobavljanja liste prezentacija", Toast.LENGTH_LONG).show();
                        findViewById(R.id.activity_presentation_list).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                true,
                getBaseContext()
        );

    }

    private void displayPresentations(ListOfPresentations presentations) {
        Toast.makeText(PresentationList.this,"Podaci o Vašim prezentacijama su uspješno učitani! Primjer naziva ppta iz vaše prezentacije: " + presentations.myPresentations.get(0).presentationName + "\r\nDalje treba popuniti Layoute s ovog activityja", Toast.LENGTH_LONG).show();
        /*
        List<ExpandablePresentationItem> presentationItemList = new ArrayList<ExpandablePresentationItem>();

        if(presentations.myPresentations.size() > 0) {
            for (Presentation presentation : presentations.myPresentations) {
                presentationItemList.add(new ExpandablePresentationItem(presentation));
            }
        }
        if(myPptsRecycler != null) {
            PresentationRecyclerAdapter adapter = new PresentationRecyclerAdapter(this, presentationItemList);
            myPptsRecycler.setAdapter(adapter);
            myPptsRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
        */
    }
}
