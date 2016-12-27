package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.function.BiConsumer;

import hr.air.interactiveppt.entities.Presentations;
import hr.air.interactiveppt.entities.SurveyWithQuestions;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

public class OpenPresentation extends AppCompatActivity {

    static public String requestType="get_presentation";
    Intent intent;
    String presentationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_presentation);

        intent = new Intent(this, ViewPresentation.class);


        Button button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.presentationCode);
                presentationCode = editText.getText().toString();

                CommunicationHandler.SendDataAndProcessResponse(ServiceGenerator.createService(WebService.class)
                                .getPresentation(presentationCode,requestType),
                        new BiConsumer<Call<Presentations>, Response<Presentations>>() {
                            @Override
                            public void accept(Call<Presentations> call, Response<Presentations> response) {
                                openPres(response.body());
                            }
                        },
                        new BiConsumer<Call<Presentations>, Throwable>() {
                            @Override
                            public void accept(Call<Presentations> call, Throwable throwable) {
                                Toast.makeText(OpenPresentation.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                            }
                        },
                        true,
                        getBaseContext()
                );


            }
        });
    }

    public void openPres(Presentations object){
        intent.putExtra("code",object.path);
        startActivity(intent);
    }
}
