package hr.foi.air.interactiveppt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import hr.foi.air.interactiveppt.entities.PresentationWithSurveys;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

/**
 * Created by marin on 29.12.2016..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "INTERACTIVE";

    String text = "close";
    String id;
    Intent intent;

    PresentationWithSurveys pws;
    boolean check;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        pws = new Gson().fromJson(remoteMessage.getData().get("message"),PresentationWithSurveys.class);
        id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        new SendDataAndProcessResponseTask(
                ServiceGenerator.createService(WebService.class).checkStatus(
                        "check_status",
                        id,
                        pws.path
                ),
                new SendDataAndProcessResponseTask.PostActions() {
                    @Override
                    public void onSuccess(Object response) {
                        check = (boolean) response;
                    }

                    @Override
                    public void onFailure() {

                    }
                }
        );
        if(check){
            openPresentation(remoteMessage.getData().get("message"));
        }else {
            sendNotification();
        }

    }

    public void  openPresentation(String messageBody){
        intent = new Intent(this, ViewPresentation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        intent.putExtra("id", id);
        intent.putExtra("manual_open", text);
        intent.putExtra("serialized_presentation", messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_file).setContentTitle("NOVA VERZIJA!!VAŽNO!!")
                .setContentText("Na serveru se nalazi nove prezentacija:"+pws.path)
                .setAutoCancel(true).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }

    public void sendNotification(){

        intent = new Intent(this, PresentationList.class);
        id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_file).setContentTitle("Nova prezentacija")
                .setContentText("Na serveru se nalazi nove prezentacija:"+pws.path)
                .setAutoCancel(true).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());

    }
}
