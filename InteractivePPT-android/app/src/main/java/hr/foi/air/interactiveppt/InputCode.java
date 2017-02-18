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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.interactiveppt.entities.PresentationWithSurveys;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

public class InputCode extends AppCompatActivity {
    @BindView(R.id.lozinkaAnketa)EditText mEditText;
    @BindView(R.id.posaljiSifruButton)Button button;
    String id;

    String text = "open";

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);

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

        findViewById(R.id.loading_panel).setVisibility(View.GONE);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
    }

    @OnClick(R.id.posaljiSifruButton)
    public void onButtonClick(View view){
        final String accessCode = mEditText.getText().toString();
        findViewById(R.id.activity_input_code).setClickable(false);
        findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
        new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                .getPresentation(accessCode, "get_presentation"),
                new SendDataAndProcessResponseTask.PostActions() {
                    @Override
                    public void onSuccess(Object genericResponse) {
                        PresentationWithSurveys response = (PresentationWithSurveys) genericResponse;
                        if (response != null) {
                            Intent intent = new Intent(InputCode.this, ViewPresentation.class);
                            intent.putExtra("id", id);
                            intent.putExtra("manual_open", text);
                            intent.putExtra("serialized_presentation", new Gson().toJson(response));
                            findViewById(R.id.activity_input_code).setClickable(true);
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            finish();
                            startActivity(intent);
                        }
                        else {
                            findViewById(R.id.activity_input_code).setClickable(true);
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            Toast.makeText(InputCode.this,"Prezentacija s navedenim pristupnim kodom ne postoji!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure() {
                        findViewById(R.id.activity_input_code).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                        Toast.makeText(InputCode.this,"Greška kod dobavljanja prezentacije", Toast.LENGTH_LONG).show();
                    }
                }
        );
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
                intent.putExtra("id",id);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, CreateSurvey.class);
                intent.putExtra("id",id);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_third_fragment:
                intent = new Intent(this, GetCode.class);
                intent.putExtra("id",id);
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
}
