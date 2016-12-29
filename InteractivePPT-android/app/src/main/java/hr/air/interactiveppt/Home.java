package hr.air.interactiveppt;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.BiConsumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.air.interactiveppt.entities.User;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

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

        String token = PreferenceManager.getDefaultSharedPreferences(this).getString("FCM_TOKEN","");
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

        if(token != ""){
            CommunicationHandler.SendDataAndProcessResponse(
                    ServiceGenerator.createService(WebService.class).saveToken(
                            "save_token",
                            token,
                            user.id
                    ),
                    new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                        @Override
                        public void accept(Call<Boolean> call, Response<Boolean> response) {
                        }
                    },
                    new BiConsumer<Call<Boolean>, Throwable>() {
                        @Override
                        public void accept(Call<Boolean> sCall, Throwable throwable) {
                            Toast.makeText(Home.this,
                                    "Neuspjeh unos tokena u bazu!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    },
                    false,
                    getBaseContext()
            );
        }

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
        intent.putExtra("id",user.getId());
        startActivity(intent);
    }

    @OnClick(R.id.button_my_surveys)
    public void loadMySurveys(View view) {
        Intent intent = new Intent(this, PresentationList.class);
        intent.putExtra("id",user.getId());
        startActivity(intent);
    }

}
