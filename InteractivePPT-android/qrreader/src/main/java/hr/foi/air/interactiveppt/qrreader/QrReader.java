package hr.foi.air.interactiveppt.qrreader;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Smrad on 10.11.2016..
 */

public class QrReader extends AppCompatActivity implements ZXingScannerView.ResultHandler{
        private ZXingScannerView mScannerView;
        private String surveyCode;
        String id;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mScannerView = new ZXingScannerView(this);
            setContentView(mScannerView);

            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
            id = getIntent().getStringExtra("id");

            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    surveyCode= "";
                    Intent intent=new Intent();
                    intent.putExtra("survey_code",surveyCode);
                    setResult(2,intent);
                    finish();
                }

            }.start();
        }

        @Override
        public void onPause() {
            super.onPause();
            mScannerView.stopCamera();
        }

        @Override
        public void handleResult(Result rawResult) {

            Log.e("handler", rawResult.getText());
            Log.e("handler", rawResult.getBarcodeFormat().toString());

            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Scan Result");
            builder.setMessage(rawResult.getText());
            AlertDialog alert1 = builder.create();
            alert1.show();*/
            surveyCode= rawResult.getText();
            Intent intent=new Intent();
            intent.putExtra("survey_code",surveyCode);
            setResult(2,intent);
            finish();
        }
}
