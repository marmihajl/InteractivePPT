package hr.air.interactiveppt;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.function.BiConsumer;

import hr.air.interactiveppt.entities.Presentation;
import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marin on 29.12.2016..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "INTERACTIVE";

    String text = "close";
    String id;
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getData().get("message"));
    }

    public void sendNotification(String messageBody){
        intent = new Intent(this, ViewPresentation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", id);
        intent.putExtra("manual_open", text);
        intent.putExtra("serialized_presentation", messageBody);

        id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        PresentationWithSurveys pws = new Gson().fromJson(messageBody,PresentationWithSurveys.class);
        CommunicationHandler.SendDataAndProcessResponse(
                ServiceGenerator.createService(WebService.class).checkStatus(
                        "check_status",
                        id,
                        pws.path
                ),
                new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                    @Override
                    public void accept(Call<Boolean> call, Response<Boolean> response) {
                            startActivity(intent);
                    }
                },
                new BiConsumer<Call<Boolean>, Throwable>() {
                    @Override
                    public void accept(Call<Boolean> call, Throwable throwable) {

                    }
                },
                false,
                getBaseContext()
        );
    }
}
