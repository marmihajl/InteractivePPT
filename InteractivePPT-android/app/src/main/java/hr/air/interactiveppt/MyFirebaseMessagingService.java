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

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getData().get("message"));
    }

    public void sendNotification(String messageBody){
        String id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        Intent intent = new Intent(this, ViewPresentation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", id);
        intent.putExtra("serialized_presentation", messageBody);
        startActivity(intent);

    }
}
