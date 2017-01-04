package hr.air.interactiveppt;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
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

    PresentationWithSurveys pws;
    boolean check;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        pws = new Gson().fromJson(remoteMessage.getData().get("message"),PresentationWithSurveys.class);
        id = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_ID","");
        CommunicationHandler.SendDataAndProcessResponse(
                ServiceGenerator.createService(WebService.class).checkStatus(
                        "check_status",
                        id,
                        pws.path
                ),
                new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                    @Override
                    public void accept(Call<Boolean> call, Response<Boolean> response) {
                        check = response.body();
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
        //startActivity(intent);
    }

    public void sendNotification(){

        intent = new Intent(this, Home.class);
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
