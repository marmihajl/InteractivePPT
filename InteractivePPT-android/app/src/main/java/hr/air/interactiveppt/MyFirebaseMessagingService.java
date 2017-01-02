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
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage.getData().get("message"));
    }

    public void sendNotification(String messageBody){
        Intent intent = new Intent(this, ViewPresentation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        intent.putExtra("id",id);
        intent.putExtra("code",messageBody);
        startActivity(intent);

        //intent.putExtra("serialized_presentation", new Gson().toJson(response.body()));

        /*Intent intent = new Intent(this, ViewPresentation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("code",messageBody);
        startActivity(intent);*/

    }
}
