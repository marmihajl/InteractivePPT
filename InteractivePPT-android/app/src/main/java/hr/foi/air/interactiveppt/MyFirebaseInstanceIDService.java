package hr.foi.air.interactiveppt;

import android.app.SharedElementCallback;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by marin on 28.12.2016..
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String FCM_TOKEN = "FCM_TOKEN";
    private static final String TAG = "INTERACTIVEPPT";

    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: "+refreshToken);
        saveToken(refreshToken);
    }

    public void saveToken(String token){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString(FCM_TOKEN,token).apply();
    }
}
