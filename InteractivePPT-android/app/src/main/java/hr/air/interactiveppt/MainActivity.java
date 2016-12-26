package hr.air.interactiveppt;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.BiConsumer;

import butterknife.BindView;
import butterknife.OnClick;
import hr.air.interactiveppt.entities.User;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private TextView text;
    User user;

    @BindView(R.id.default_login_button)
    LoginButton defaultLoginButton;

    @BindView(R.id.visible_login_button)
    Button visibleLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        findViewById(R.id.loading_panel).setVisibility(View.GONE);
        text = (TextView)findViewById(R.id.errorText);
        defaultLoginButton = (LoginButton) findViewById(R.id.default_login_button);
        defaultLoginButton.addTextChangedListener(textWatcher);
        visibleLoginButton.setText(defaultLoginButton.getText());

        defaultLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

    @OnClick(R.id.visible_login_button)
    protected void authenticateViaFacebook(View v) {
        defaultLoginButton.performClick();
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
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/", null, HttpMethod.DELETE, new GraphRequest
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
                new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                    @Override
                    public void accept(Call<Boolean> call, Response<Boolean> response) {
                        toggleVisibilityAtLoading(false);
                        openHome();
                    }
                },
                new BiConsumer<Call<Boolean>, Throwable>() {
                    @Override
                    public void accept(Call<Boolean> sCall, Throwable throwable) {
                        Toast.makeText(MainActivity.this,
                                "Neuspjeh kod registracije u sustav! Provjerite vezu s Internetom",
                                Toast.LENGTH_LONG
                        ).show();
                        toggleVisibilityAtLoading(true);
                    }
                },
                false,
                getBaseContext()
        );
        toggleVisibilityAtLoading(true);
    }

    private void toggleVisibilityAtLoading(boolean showHiddenViews) {
        switch (findViewById(R.id.loading_panel).getVisibility()) {
            case View.VISIBLE:
                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                if (showHiddenViews) {
                    findViewById(R.id.visible_login_button).setVisibility(View.VISIBLE);
                    findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                    findViewById(R.id.subtitle).setVisibility(View.VISIBLE);
                }
                break;
            case View.GONE:
                findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
                findViewById(R.id.visible_login_button).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.GONE);
                findViewById(R.id.subtitle).setVisibility(View.GONE);
                break;
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            visibleLoginButton.setText(defaultLoginButton.getText());
        }
    };
}
