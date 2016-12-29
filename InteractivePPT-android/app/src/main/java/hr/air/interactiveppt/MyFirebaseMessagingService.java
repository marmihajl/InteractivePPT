package hr.air.interactiveppt;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by marin on 29.12.2016..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.toString());
    }

    public void sendNotification(String messageBody){
        InitPresentation.refrashPresentation(messageBody);
    }
}
