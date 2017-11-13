package hr.foi.air.interactiveppt;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import hr.foi.air.interactiveppt.entities.ActiveChatMessagesList;
import hr.foi.air.interactiveppt.entities.ChatMessage;
import hr.foi.air.interactiveppt.entities.PresentationWithSurveys;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

public class ViewPresentation extends AppCompatActivity {

    String userId;
    private int pptId;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private Socket socket;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_presentation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ham_ic);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        drawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawerToggle = setupDrawerToggle();

        mDrawer.addDrawerListener(drawerToggle);

        Intent intent = getIntent();

        String manual = getIntent().getStringExtra("manual_open");

        final PresentationWithSurveys presentation = new Gson().fromJson(intent.getStringExtra("serialized_presentation"), PresentationWithSurveys.class);

        userId = intent.getStringExtra("id");
        pptId = presentation.id;

        WebView wv = (WebView)findViewById(R.id.webview);

        openPresentation(wv, presentation.path);

        Button requestReplyButton = (Button)findViewById(R.id.requestReply);
        requestReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendDataAndProcessResponseTask(
                        ServiceGenerator.createService(WebService.class).saveReplyRequest(
                                "save_interested_user",
                                presentation.path,
                                userId
                        ),
                        new SendDataAndProcessResponseTask.PostActions() {
                            @Override
                            public void onSuccess(Object genericResponse) {
                                boolean response = (boolean) genericResponse;
                                if (response) {
                                    Toast.makeText(ViewPresentation.this,
                                            "Uspješno ste poslali zahtjev za repliciranjem!",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                                else {
                                    Toast.makeText(ViewPresentation.this,
                                            "Molimo Vas za strpljenje! Vaš prethodno poslani zahtjev za repliciranjem još uvijek čeka da bude pogledan od prezentatora",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(ViewPresentation.this,
                                        "Neuspjeh kod slanja zahtjeva za repliciranjem! Pokušajte kasnije..",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
            }
        });

        Button openSurveyListButton = (Button)findViewById(R.id.openSurvey);
        openSurveyListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPresentation.this, SurveyList.class);
                intent.putExtra("id", userId);
                intent.putExtra("ppt_path", presentation.path);
                startActivity(intent);
            }});

        if(presentation.surveys.size() == 0){
            openSurveyListButton.setEnabled(false);
        }

        if(manual.equals("open")){
            new SendDataAndProcessResponseTask(
                    ServiceGenerator.createService(WebService.class).saveSubscription(
                            "save_subscription",
                            presentation.path,
                            userId
                    ),
                    new SendDataAndProcessResponseTask.PostActions() {
                        @Override
                        public void onSuccess(Object response) {

                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(ViewPresentation.this,
                                    "Neuspjeh kod pokušaja pohrane šifre za notificiranje!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        }

        Button chatButton = (Button)findViewById(R.id.chat);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewPresentation.this, Chat.class);
                i.putExtra("userId", userId);
                i.putExtra("pptId", pptId);
                startActivity(i);
            }
        });

        new Thread(new ClientThread(this, presentation.id, presentation.getPresentationName(), userId)).start();
    }

    class ClientThread implements Runnable {

        private Activity activity;
        private int pptId;
        private String pptName;
        private String userId;
        private ActiveChatMessagesList chatMessagesList;

        ClientThread(Activity activity, int pptId, String pptName, String userId) {
            this.activity = activity;
            this.pptId = pptId;
            this.pptName = pptName;
            this.userId = userId;
        }

        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(ServiceGenerator.SERVER_HOSTNAME);
                socket = new Socket(serverAddr, 50000 + pptId);
                OutputStream outToServer = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF(userId);
            }
            catch (Exception e) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, "Nije moguće uspostaviti komunikaciju s prezentatorom. Chat i repliciranje u ovoj sesiji neće biti omogućeni", Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }

            try {
                boolean initialState = true;
                while (true) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final String receivedMessage = in.readLine();
                    final String[] messageParts = receivedMessage.split("\t");
                    if (receivedMessage.equals("exit")) {
                        break;
                    }
                    if (initialState) {
                        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
                        for (int i = 0; i < messageParts.length; i+=3) {
                            if (!messageParts[i+2].isEmpty()) {
                                chatMessages.add(new ChatMessage(messageParts[i+2].replace('\f', '\n'), messageParts[i+1]));
                            }
                        }
                        ActiveChatMessagesList.getInstance().setChatMessages(chatMessages);
                        initialState = false;
                    }
                    else {
                        if (!messageParts[0].equals(userId)) {
                            activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(activity)
                                                    .setSmallIcon(R.drawable.app_icon)
                                                    .setContentTitle("InteractivePPT - " + pptName)
                                                    .setContentText(messageParts[1] + ": " + messageParts[2].replace('\f', '\n'))
                                                    .setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                                    Intent resultIntent = new Intent(activity, Chat.class);

                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
                                    stackBuilder.addParentStack(Chat.class);
                                    stackBuilder.addNextIntent(resultIntent);
                                    PendingIntent resultPendingIntent =
                                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                                    mBuilder.setContentIntent(resultPendingIntent);
                                    NotificationManager mNotificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    mNotificationManager.notify(new java.util.Random().nextInt(), mBuilder.build());
                                }
                            });
                        }
                        ActiveChatMessagesList.getInstance().addChatMessageIntoList(new ChatMessage(messageParts[2].replace('\f', '\n'), messageParts[1]));
                    }
                }
                socket.close();
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openPresentation(final WebView wv, final String pptPath) {
        String uriToPpt = "http://docs.google.com/viewer?url=" + ServiceGenerator.API_BASE_URL + pptPath + "&embedded=true";

        startWebView(uriToPpt, wv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class).killConnection("kill_connection", pptId, userId));
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                intent = new Intent(this, PresentationList.class);
                intent.putExtra("id",userId);
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, CreateSurvey.class);
                intent.putExtra("id",userId);
                break;
            case R.id.nav_third_fragment:
                intent = new Intent(this, GetCode.class);
                intent.putExtra("id",userId);
                break;
            default:    //case R.id.nav_logout:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("logout", true);
                break;
        }
        finish();
        startActivity(intent);
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void startWebView(String url, WebView webView) {


        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource (final WebView view, String url) {
            }
            public void onPageFinished(WebView view, String url) {
            }

        });

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);
    }
}
