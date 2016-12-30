package hr.air.interactiveppt;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by marin on 29.12.2016..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "INTERACTIVEPPT";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

                        // Check if message contains a data payload.
                                if (remoteMessage.getData().size() > 0) {
                        Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
                                    Log.d(TAG, "Message text: " + remoteMessage.toString());
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
        intent.putExtra("code",messageBody);
        startActivity(intent);
    }
}
