package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.qrreader.QrReader;

public class GetCode extends AppCompatActivity {

    @BindView(R.id.upisiSifru)
    Button sifraButton;

    @BindView(R.id.qrRead)
    Button qrButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.upisiSifru)
    public void sifraButtonClick(View view){
        Intent intent= new Intent(this, InputCode.class);
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
                String message=data.getStringExtra("survey_code");
                TextView mTview= (TextView)findViewById(R.id.testView);
                mTview.setText(message);
                mTview.invalidate();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

