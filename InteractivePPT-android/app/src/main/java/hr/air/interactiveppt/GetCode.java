package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import hr.foi.air.qrreader.QrReader;

public class GetCode extends AppCompatActivity {

    @BindView(R.id.upisiSifru)
    Button sifraButton;

    @BindView(R.id.qrRead)
    Button qrButton;

    String id;
    String text = "open";

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
                new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                                .getPresentation(accessCode, "get_presentation"),
                        new SendDataAndProcessResponseTask.PostActions() {
                            @Override
                            public void onSuccess(Object responseObject) {
                                PresentationWithSurveys response = (PresentationWithSurveys) responseObject;
                                if (response != null) {
                                    Intent intent = new Intent(GetCode.this, ViewPresentation.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("manual_open", text);
                                    intent.putExtra("serialized_presentation", new Gson().toJson(response));
                                    findViewById(R.id.activity_get_code).setClickable(true);
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    startActivity(intent);
                                }
                                else {
                                    findViewById(R.id.activity_input_code).setClickable(true);
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    Toast.makeText(GetCode.this,"Prezentacija s navedenim pristupnim kodom ne postoji!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure() {
                                findViewById(R.id.activity_get_code).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                Toast.makeText(GetCode.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

