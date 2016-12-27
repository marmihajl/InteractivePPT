package hr.air.interactiveppt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.air.interactiveppt.entities.User;

public class Home extends AppCompatActivity {
    User user;

    @BindView(R.id.button_my_surveys)
    RelativeLayout mySurveysButton;

    @BindView(R.id.button_create_survey)
    RelativeLayout createSurveyButton;

    @BindView(R.id.button_load_survey)
    RelativeLayout loadSurveyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String fullName = intent.getStringExtra("fullName");

        user = new User(fullName,id);

        TextView textView = (TextView)findViewById(R.id.userName);
        textView.setText(user.getFullName());

        Button logOut = (Button)findViewById(R.id.logoutButton);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);
            }
        });

    }

    @OnClick(R.id.button_create_survey)
    public void createSurveyClick(View view){
        Intent intent = new Intent(this, CreateSurvey.class);
        intent.putExtra("id",user.getId());
        startActivity(intent);
    }

    @OnClick(R.id.button_load_survey)
    public void loadSurveyClick(View view){
        /*Intent intent = new Intent(this, GetCode.class);
        intent.putExtra("id",user.getId());
        startActivity(intent);*/
        Intent intent = new Intent(this, OpenPresentation.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_my_surveys)
    public void loadMySurveys(View view) {
        Intent intent = new Intent(this, PresentationList.class);
        intent.putExtra("id",user.getId());
        startActivity(intent);
    }

}
