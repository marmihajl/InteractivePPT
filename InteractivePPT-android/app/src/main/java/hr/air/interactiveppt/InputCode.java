package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputCode extends AppCompatActivity {
    @BindView(R.id.lozinkaAnketa)EditText mEditText;
    @BindView(R.id.posaljiSifruButton)Button button;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
    }

    @OnClick(R.id.posaljiSifruButton)
    public void onButtonClick(View view){
        Intent intent= new Intent(this, GetSurvey.class);
        intent.putExtra("id",id);
        intent.putExtra("message",mEditText.getText().toString());
        startActivity(intent);
    }
}
