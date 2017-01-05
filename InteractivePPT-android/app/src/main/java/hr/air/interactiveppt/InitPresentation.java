package hr.air.interactiveppt;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by marin on 29.12.2016..
 */

public class InitPresentation {

    public static String mDoc;
    public static WebView mWv;

    public static void openPresentation(String presentation, WebView wv){
        mDoc = "<iframe src='http://docs.google.com/viewer?url=http://46.101.68.86/"+presentation+"&embedded=true' width='100%' height='100%'  style='border: none;'></iframe>";
        mWv = wv;
        wv.setVisibility(WebView.VISIBLE);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.loadData(mDoc, "text/html", "UTF-8");
    }

}
