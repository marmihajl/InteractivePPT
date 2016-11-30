package hr.air.interactiveppt;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.BiConsumer;

import hr.air.interactiveppt.entities.User;
import hr.air.interactiveppt.entities.responses.ProcessingResultResponse;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private TextView text;
    private ImageView profileImgView;
    private LoginButton loginButton;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.errorText);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                setUserInfo(object);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();

                if (AccessToken.getCurrentAccessToken() == null) {
                    return; // already logged out
                }



            }

            @Override
            public void onCancel() {


                text.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException error) {


                text.setText("Login attempt failed.");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void openHome(){


        Intent intent = new Intent(MainActivity.this,Home.class);
        intent.putExtra("id",user.getId());
        intent.putExtra("fullName",user.getFullName());
        startActivity(intent);
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
        finish();
    }

    private void setUserInfo(JSONObject object) {

        try {
            String id = object.getString("id");
            String fullName = object.getString("name");
            user = new User(fullName, id);
            registerUserIntoWebservice(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void registerUserIntoWebservice(User u) {
        CommunicationHandler.SendDataAndProcessResponse(
                ServiceGenerator.createService(WebService.class).registerUser(
                        "register_user",
                        u.id,
                        u.fullName
                ),
                new BiConsumer<Call<ProcessingResultResponse>, Response<ProcessingResultResponse>>() {
                    @Override
                    public void accept(Call<ProcessingResultResponse> call, Response<ProcessingResultResponse> response) {
                        openHome();
                    }
                },
                new BiConsumer<Call<ProcessingResultResponse>, Throwable>() {
                    @Override
                    public void accept(Call<ProcessingResultResponse> sCall, Throwable throwable) {
                        ;
                    }
                },
                false,
                getBaseContext()
        );
    }
}
