package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.function.BiConsumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import hr.foi.air.qrreader.QrReader;
import retrofit2.Call;
import retrofit2.Response;

public class GetCode extends AppCompatActivity {

    @BindView(R.id.upisiSifru)
    Button sifraButton;

    @BindView(R.id.qrRead)
    Button qrButton;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);
        findViewById(R.id.loading_panel).setVisibility(View.GONE);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
    }

    @OnClick(R.id.upisiSifru)
    public void sifraButtonClick(View view){
        Intent intent= new Intent(this, InputCode.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @OnClick(R.id.qrRead)
    public void qrButtonClick(View view){
        Intent intent= new Intent(this, QrReader.class);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != RESULT_CANCELED){
            if(requestCode==2){
                final String accessCode = data.getStringExtra("survey_code");
                findViewById(R.id.activity_get_code).setClickable(false);
                findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
                CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                                .getPresentation(accessCode, "get_presentation"),
                        new BiConsumer<Call<PresentationWithSurveys>, Response<PresentationWithSurveys>>() {
                            @Override
                            public void accept(Call<PresentationWithSurveys> call, Response<PresentationWithSurveys> response) {
                                Intent intent=new Intent(GetCode.this, ViewPresentation.class);
                                intent.putExtra("id", id);
                                intent.putExtra("serialized_presentation", new Gson().toJson(response.body()));
                                findViewById(R.id.activity_get_code).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                startActivity(intent);
                            }
                        },
                        new BiConsumer<Call<PresentationWithSurveys>, Throwable>() {
                            @Override
                            public void accept(Call<PresentationWithSurveys> call, Throwable throwable) {
                                findViewById(R.id.activity_get_code).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                Toast.makeText(GetCode.this,"Greška kod dobavljanja ankete", Toast.LENGTH_LONG).show();
                            }
                        },
                        true,
                        getBaseContext()
                );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

