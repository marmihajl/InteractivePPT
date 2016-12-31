package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import retrofit2.Call;
import retrofit2.Response;

public class InputCode extends AppCompatActivity {
    @BindView(R.id.lozinkaAnketa)EditText mEditText;
    @BindView(R.id.posaljiSifruButton)Button button;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);
        findViewById(R.id.loading_panel).setVisibility(View.GONE);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
    }

    @OnClick(R.id.posaljiSifruButton)
    public void onButtonClick(View view){
        final String accessCode = mEditText.getText().toString();
        findViewById(R.id.activity_input_code).setClickable(false);
        findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
        CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                        .getPresentation(accessCode, "get_presentation"),
                new BiConsumer<Call<PresentationWithSurveys>, Response<PresentationWithSurveys>>() {
                    @Override
                    public void accept(Call<PresentationWithSurveys> call, Response<PresentationWithSurveys> response) {
                        if (response.body() != null) {
                            Intent intent = new Intent(InputCode.this, ViewPresentation.class);
                            intent.putExtra("id", id);
                            intent.putExtra("serialized_presentation", new Gson().toJson(response.body()));
                            findViewById(R.id.activity_input_code).setClickable(true);
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            startActivity(intent);
                        }
                        else {
                            findViewById(R.id.activity_input_code).setClickable(true);
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            Toast.makeText(InputCode.this,"Prezentacija s navedenim pristupnim kodom ne postoji!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new BiConsumer<Call<PresentationWithSurveys>, Throwable>() {
                    @Override
                    public void accept(Call<PresentationWithSurveys> call, Throwable throwable) {
                        findViewById(R.id.activity_input_code).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        Toast.makeText(InputCode.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                    }
                },
                true,
                getBaseContext()
        );
    }
}
