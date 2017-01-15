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

import java.util.Map;

import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;

public class ViewPresentation extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_presentation);

        Intent intent = getIntent();

        String manual = getIntent().getStringExtra("manual_open");

        final PresentationWithSurveys presentation = new Gson().fromJson(intent.getStringExtra("serialized_presentation"), PresentationWithSurveys.class);

        userId = intent.getStringExtra("id");

        WebView wv = (WebView)findViewById(R.id.webview);

        openPresentation(wv, presentation.path);

        Button requestReplyButton = (Button)findViewById(R.id.requestReply);
        requestReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendDataAndProcessResponseTask(
                        ServiceGenerator.createService(WebService.class).saveReplyRequest(
                                "save_interested_user",
                                presentation.path,
                                userId
                        ),
                        new SendDataAndProcessResponseTask.PostActions() {
                            @Override
                            public void onSuccess(Object genericResponse) {
                                boolean response = (boolean) genericResponse;
                                if (response) {
                                    Toast.makeText(ViewPresentation.this,
                                            "Uspješno ste poslali zahtjev za repliciranjem!",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                                else {
                                    Toast.makeText(ViewPresentation.this,
                                            "Molimo Vas za strpljenje! Vaš prethodno poslani zahtjev za repliciranjem još uvijek čeka da bude pogledan od prezentatora",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(ViewPresentation.this,
                                        "Neuspjeh kod slanja zahtjeva za repliciranjem! Pokušajte kasnije..",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
            }
        });

        Button openSurveyListButton = (Button)findViewById(R.id.openSurvey);
        openSurveyListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPresentation.this, SurveyList.class);
                intent.putExtra("id", userId);
                intent.putExtra("ppt_path", presentation.path);
                startActivity(intent);
            }});

        if(manual.equals("open")){
            new SendDataAndProcessResponseTask(
                    ServiceGenerator.createService(WebService.class).saveSubscription(
                            "save_subscription",
                            presentation.path,
                            userId
                    ),
                    new SendDataAndProcessResponseTask.PostActions() {
                        @Override
                        public void onSuccess(Object response) {

                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(ViewPresentation.this,
                                    "Neuspjeh kod pokušaja pohrane šifre za notificiranje!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        }

    }

    private void openPresentation(final WebView wv, final String pptPath) {

        int delayInMs = android.os.Build.VERSION.SDK_INT < 19 ? 2000 : 500;

        wv.postDelayed(new Runnable() {

            @Override
            public void run() {
                String uriToPpt = "http://docs.google.com/viewer?url=http://46.101.68.86/" + pptPath + "&embedded=true";
                wv.loadUrl(uriToPpt);
                wv.setVisibility(WebView.VISIBLE);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.getSettings().setAllowFileAccess(true);
                wv.getSettings().setPluginState(WebSettings.PluginState.ON);
            }
        }, delayInMs);
    }
}
