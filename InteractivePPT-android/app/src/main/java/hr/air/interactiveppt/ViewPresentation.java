package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class ViewPresentation extends AppCompatActivity {

    WebView wv;
    String doc;
    String presentation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_presentation);


        Intent intent = getIntent();
        presentation = intent.getStringExtra("code");

        doc="<iframe src='http://docs.google.com/viewer?url=http://46.101.68.86/"+presentation+"&embedded=true' width='100%' height='100%'  style='border: none;'></iframe>";


        wv = (WebView)findViewById(R.id.webview);
        InitPresentation.openPresentation(presentation,wv);

        Button button = (Button)findViewById(R.id.sinc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitPresentation.refrashPresentation(presentation);
            }
        });




    }
}
