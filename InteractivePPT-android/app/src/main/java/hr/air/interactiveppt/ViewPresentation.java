package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.function.BiConsumer;

import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

public class ViewPresentation extends AppCompatActivity {

    WebView wv;
    String doc;
    PresentationWithSurveys presentation;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_presentation);


        Intent intent = getIntent();
        presentation = new Gson().fromJson(intent.getStringExtra("serialized_presentation"), PresentationWithSurveys.class);
        userId = intent.getStringExtra("id");
        final String pptPath = presentation.path;

        doc="<iframe src='http://docs.google.com/viewer?url=http://46.101.68.86/" + pptPath + "&embedded=true' width='100%' height='100%'  style='border: none;'></iframe>";


        wv = (WebView)findViewById(R.id.webview);
        InitPresentation.openPresentation(pptPath, wv);

        Button button = (Button)findViewById(R.id.sinc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitPresentation.refrashPresentation(pptPath);
            }
        });


        CommunicationHandler.SendDataAndProcessResponse(
                ServiceGenerator.createService(WebService.class).saveNotification(
                        "save_notification",
                        pptPath,
                        userId
                ),
                new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                    @Override
                    public void accept(Call<Boolean> call, Response<Boolean> response) {
                    }
                },
                new BiConsumer<Call<Boolean>, Throwable>() {
                    @Override
                    public void accept(Call<Boolean> call, Throwable throwable) {
                        Toast.makeText(ViewPresentation.this,
                                "Neuspjeh kod pokušaja pohrane šifre za notificiranje!",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                },
                false,
                getBaseContext()
        );


    }
}
