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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import hr.foi.air.interactiveppt.entities.ListOfPresentations;
import hr.foi.air.interactiveppt.entities.PresentationWithSurveys;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

public class PresentationList extends AppCompatActivity {
    private ExpandableListView myPptsLV;
    private ExpandableListView subbedPptsLV;
    private String userId;
    private String name;
    private String text = "close";

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_list);
        com.facebook.FacebookSdk.sdkInitialize(getApplicationContext());

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

        findViewById(R.id.activity_presentation_list).setClickable(false);
        String requestType = "get_presentation_list";
        userId = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("fullName");

        myPptsLV = (ExpandableListView) findViewById(R.id.my_ppts_listview);
        subbedPptsLV = (ExpandableListView) findViewById(R.id.subbed_ppts_listview);

        new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                .getPresentationList(userId, requestType),
                new SendDataAndProcessResponseTask.PostActions() {
                    @Override
                    public void onSuccess(Object genericResponse) {
                        ListOfPresentations response = (ListOfPresentations) genericResponse;
                        displayPresentations(response);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_presentation_list).setClickable(true);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(PresentationList.this, R.string.presentations_fetch_error, Toast.LENGTH_LONG).show();
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        findViewById(R.id.activity_presentation_list).setClickable(true);
                    }
                }
        );
    }

    private void displayPresentations(ListOfPresentations presentations) {

        ExpandablePresentationListAdapter adapter = null;

        OnProceedButtonClicked consumer = new OnProceedButtonClicked() {
            @Override
            public void onClick(String accessCode) {
                findViewById(R.id.activity_presentation_list).setClickable(false);
                findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);

                new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                        .getPresentation(accessCode, "get_presentation"),
                        new SendDataAndProcessResponseTask.PostActions() {
                            @Override
                            public void onSuccess(Object genericResponse) {
                                PresentationWithSurveys response = (PresentationWithSurveys) genericResponse;
                                if (response != null) {
                                    Intent intent = new Intent(PresentationList.this, ViewPresentation.class);
                                    intent.putExtra("id", userId);
                                    intent.putExtra("manual_open", text);
                                    intent.putExtra("serialized_presentation", new Gson().toJson(response));
                                    findViewById(R.id.activity_presentation_list).setClickable(true);
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    findViewById(R.id.activity_input_code).setClickable(true);
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    Toast.makeText(PresentationList.this, R.string.presentation_doesnt_exist_anymore, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure() {
                                findViewById(R.id.activity_presentation_list).setClickable(true);
                                findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                Toast.makeText(PresentationList.this, R.string.presentation_fetch_error, Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        };

        adapter = new ExpandablePresentationListAdapter(this, presentations.myPresentations, consumer);
        if(presentations.myPresentations.size() > 0){
            myPptsLV.setAdapter(adapter);
        }else {
            TextView textView = (TextView)findViewById(R.id.noPresentation);
            textView.setVisibility(View.VISIBLE);
            myPptsLV.setVisibility(View.INVISIBLE);
        }

        adapter = new ExpandablePresentationListAdapter(this, presentations.subbedPresentations, consumer);
        if(presentations.subbedPresentations.size() > 0){
            subbedPptsLV.setAdapter(adapter);
        }else {
            TextView textView = (TextView)findViewById(R.id.noSubscription);
            textView.setVisibility(View.VISIBLE);
            subbedPptsLV.setVisibility(View.INVISIBLE);
        }


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
                //finish();
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
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("logout", true);
                finish();
                startActivity(intent);
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

}
