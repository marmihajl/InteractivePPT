package hr.foi.air.interactiveppt;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
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

import hr.foi.air.interactiveppt.entities.PresentationWithSurveys;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

public class ViewPresentation extends AppCompatActivity {

    String userId;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

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
                Intent i= new Intent(ViewPresentation.this, Chat.class);
                i.putExtra("id",userId);
                startActivity(i);
            }
        });
    }

    private void openPresentation(final WebView wv, final String pptPath) {
        String uriToPpt = "http://docs.google.com/viewer?url=http://46.101.68.86/" + pptPath + "&embedded=true";

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
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                intent = new Intent(this, PresentationList.class);
                intent.putExtra("id",userId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, CreateSurvey.class);
                intent.putExtra("id",userId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_third_fragment:
                intent = new Intent(this, GetCode.class);
                intent.putExtra("id",userId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_logout:
                finish();
                System.exit(0);
                break;
        }

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
