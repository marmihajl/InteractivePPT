package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.function.BiConsumer;

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
                        new BiConsumer<Call<String>, Response<String>>() {
                            @Override
                            public void accept(Call<String> call, Response<String> response) {
                                openPres(response.body());
                            }
                        },
                        new BiConsumer<Call<String>, Throwable>() {
                            @Override
                            public void accept(Call<String> call, Throwable throwable) {
                                Toast.makeText(OpenPresentation.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                            }
                        },
                        true,
                        getBaseContext()
                );


            }
        });
    }

    public void openPres(String path){
        intent.putExtra("code",path);
        startActivity(intent);
    }
}
