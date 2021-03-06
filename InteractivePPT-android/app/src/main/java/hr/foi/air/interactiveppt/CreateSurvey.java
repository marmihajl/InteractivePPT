package hr.foi.air.interactiveppt;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import hr.foi.air.interactiveppt.entities.ListOfPresentations;
import hr.foi.air.interactiveppt.entities.Presentation;
import hr.foi.air.interactiveppt.entities.Question;
import hr.foi.air.interactiveppt.entities.SurveyWithQuestions;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateSurvey extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    private static final int REQUEST_CODE = 6384; // onActivityResult request code

    @Nullable
    private Uri uriOfSelectedFile = null;

    ArrayList<Question> questions = new ArrayList<Question>();
    private QuestionRecyclerAdapter adapter;
    AddQuestion cdd = null;
    RecyclerView mRecycler;
    boolean myPptsLoaded = false;
    String surveyAuthorId;

    @BindView(R.id.button_attach_presentation)
    Button attachPresentation;

    @BindView(R.id.button_save_changes)
    Button saveChanges;

    @BindView(R.id.button_discard_changes)
    Button discardChanges;

    @BindView(R.id.newPptOption)
    RadioButton newPptOption;

    @BindView(R.id.existingPptOption)
    RadioButton existingPptOption;

    @OnCheckedChanged(R.id.newPptOption)
    protected void showNewPptPicker() {
        if (!newPptOption.isChecked()) {
            return;
        }
        (findViewById(R.id.newPptLV)).setVisibility(View.VISIBLE);
        (findViewById(R.id.existingPptsLV)).setVisibility(View.GONE);
        if (existingPptOption.isChecked()) {
            existingPptOption.setChecked(false);
            newPptOption.setChecked(true);
        }
    }

    @OnCheckedChanged(R.id.existingPptOption)
    protected void showExistingPptPicker() {
        if (!existingPptOption.isChecked()) {
            return;
        }
        if (newPptOption.isChecked()) {
            newPptOption.setChecked(false);
            existingPptOption.setChecked(true);
        }
        if (!myPptsLoaded) {
            (findViewById(R.id.loading_panel)).setVisibility(View.VISIBLE);
            (findViewById(R.id.activity_create_survey)).setClickable(false);
            new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                    .getPresentationList(surveyAuthorId, "get_presentation_list"),
                    new SendDataAndProcessResponseTask.PostActions() {
                        @Override
                        public void onSuccess(Object genericResponse) {
                            ArrayList<Presentation> myPpts = ((ListOfPresentations) genericResponse).myPresentations;
                            RadioGroup existingPptsRG = (RadioGroup) ((LinearLayout) findViewById(R.id.existingPptsLV)).getChildAt(1);
                            existingPptsRG.removeAllViews();
                            int numOfPpts = myPpts.size();
                            if (numOfPpts != 0) {
                                for (int i=0; i<numOfPpts; i++) {
                                    Presentation IthPpt = myPpts.get(i);
                                    RadioButton rbOfIthPpt = new RadioButton(CreateSurvey.this);
                                    rbOfIthPpt.setId(i);
                                    rbOfIthPpt.setText(IthPpt.getPresentationName());
                                    rbOfIthPpt.setTag(IthPpt.accessCode);
                                    existingPptsRG.addView(rbOfIthPpt);
                                }
                                if (numOfPpts == 1) {
                                    existingPptsRG.check(0);
                                }
                                (findViewById(R.id.newPptLV)).setVisibility(View.GONE);
                                (findViewById(R.id.existingPptsLV)).setVisibility(View.VISIBLE);
                                myPptsLoaded = true;
                            }
                            else {
                                Toast.makeText(CreateSurvey.this, R.string.no_my_ppts_error, Toast.LENGTH_LONG).show();
                                newPptOption.setChecked(true);
                            }
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            findViewById(R.id.activity_create_survey).setClickable(true);
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(CreateSurvey.this, R.string.presentations_fetch_error, Toast.LENGTH_LONG).show();
                            findViewById(R.id.loading_panel).setVisibility(View.GONE);
                            findViewById(R.id.activity_create_survey).setClickable(true);
                            newPptOption.setChecked(true);
                        }
                    }
            );

        }
        else {
            (findViewById(R.id.newPptLV)).setVisibility(View.GONE);
            (findViewById(R.id.existingPptsLV)).setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.button_attach_presentation)
    protected void attachPresentationButtonClick(View view){
        showFileChooser();
    }

    @OnClick(R.id.button_save_changes)
    protected void createSurvey(View view){

        final String surveyName = ((EditText)findViewById(R.id.title_input)).getText().toString();
        final String surveyDescription = ((EditText)findViewById(R.id.description_input)).getText().toString();

        String reasonsOfIncompletion;
        if (!(reasonsOfIncompletion = getReasonsWhyCurrentSurveyIsntComplete(surveyName, surveyDescription)).isEmpty()) {
            Toast.makeText(CreateSurvey.this, getString(R.string.survey_creation_failure_reason) + reasonsOfIncompletion, Toast.LENGTH_LONG).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_create_survey_title)
                .setMessage(R.string.alert_create_survey_message)
                .setIcon(android.R.drawable.ic_menu_save)
                .setNegativeButton(R.string.no_option, null)
                .setPositiveButton(R.string.yes_option, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sendRequestForSaving(surveyName, surveyDescription);
                    }
                })
                .show();

    }

    @OnClick(R.id.button_discard_changes)
    protected void clearCurrentSurvey(View view){
        ((EditText)findViewById(R.id.title_input)).setText("");
        ((EditText)findViewById(R.id.description_input)).setText("");
        ((TextView)findViewById(R.id.attached_file_name)).setText(R.string.no_any_file_attached_msg);
        attachPresentation.setText(R.string.add_presentation_caption);
        uriOfSelectedFile = null;
        questions.clear();
        loadData();
    }

    private String getSelectedSurveyFromExisting() {
        RadioGroup rgWithMyPpts = (RadioGroup) ((LinearLayout)(findViewById(R.id.existingPptsLV))).getChildAt(1);
        int selectedOption = rgWithMyPpts.getCheckedRadioButtonId();
        if (selectedOption == -1) {
            return null;
        }
        else {
            return rgWithMyPpts.getChildAt(selectedOption).getTag().toString();
        }
    }

    private void showFileChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        uriOfSelectedFile = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uriOfSelectedFile);
                            if (path.endsWith(".ppt") || path.endsWith(".pptx")) {
                                ((TextView)findViewById(R.id.attached_file_name)).setText(path.substring(path.lastIndexOf('/') + 1));
                                Toast.makeText(CreateSurvey.this,
                                        getResources().getString(R.string.file_successfully_added_msg) + path,
                                        Toast.LENGTH_LONG
                                ).show();
                                ((Button)findViewById(R.id.button_attach_presentation)).setText(R.string.change_presentation_caption);
                            }
                            else {
                                ((TextView)findViewById(R.id.attached_file_name)).setText(R.string.no_any_file_attached_msg);
                                Toast.makeText(CreateSurvey.this,
                                        getResources().getString(R.string.not_valid_file_type_msg) + path,
                                        Toast.LENGTH_LONG
                                ).show();
                                ((Button)findViewById(R.id.button_attach_presentation)).setText(R.string.add_presentation_caption);
                                uriOfSelectedFile = null;
                            }
                        } catch (Exception e) {
                            Log.e("CreateSurvey", "File select error", e);
                            uriOfSelectedFile = null;
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

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

        ButterKnife.bind(this);
        surveyAuthorId = getIntent().getStringExtra("id");

        findViewById(R.id.loading_panel).setVisibility(View.GONE);
        findViewById(R.id.newPptLV).setVisibility(View.GONE);
        findViewById(R.id.existingPptsLV).setVisibility(View.GONE);
        newPptOption = (RadioButton) (findViewById(R.id.newPptOption));
        existingPptOption = (RadioButton) (findViewById(R.id.existingPptOption));
        mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        Button addQuestion = (Button)findViewById(R.id.btn_add_question);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdd = new AddQuestion(CreateSurvey.this,questions);
                cdd.show();
            }

        });
    }

    public void loadData(){
        List<ExpandableQuestionItem> questionItemList = new ArrayList<ExpandableQuestionItem>();

        if(questions.size() > 0) {
            for (Question question : questions) {
                questionItemList.add(new ExpandableQuestionItem(question));
            }
        }
        if(mRecycler != null) {
            adapter = new QuestionRecyclerAdapter(this, questionItemList);
            mRecycler.setAdapter(adapter);
            mRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private String getReasonsWhyCurrentSurveyIsntComplete(String title, String description) {
        String reasonsOfIncompetion = "";
        if (title.isEmpty()) {
            reasonsOfIncompetion += "\n" + getString(R.string.no_survey_title_error);
        }
        if (description.isEmpty()) {
            reasonsOfIncompetion += "\n" + getString(R.string.no_survey_description_error);
        }
        if (newPptOption.isChecked()) {
            if (uriOfSelectedFile == null) {
                reasonsOfIncompetion += getString(R.string.no_corresponding_ppt_error);
            }
        }
        else if (existingPptOption.isChecked()) {
            if (getSelectedSurveyFromExisting() == null) {
                reasonsOfIncompetion += "\n" + getString(R.string.no_corresponding_ppt_error);
            }
        }
        else {
            reasonsOfIncompetion += "\n" + getString(R.string.no_corresponding_ppt_error);
        }
        if (questions.isEmpty()) {
            reasonsOfIncompetion += "\n" + getString(R.string.no_questions_error);
        }
        return reasonsOfIncompetion;
    }

    void sendRequestForSaving(String surveyName, String surveyDescription) {
        MultipartBody.Part pptAsMessagePart = null;
        String accessCodeIfExistingPpt = null;

        if (newPptOption.isChecked()) {
            File fileHandler = FileUtils.getFile(getBaseContext(), uriOfSelectedFile);  //this instead of getBaseContext was before
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileHandler);
            pptAsMessagePart = MultipartBody.Part.createFormData("ppt", fileHandler.getName(), requestFile);
        }
        else {
            accessCodeIfExistingPpt = getSelectedSurveyFromExisting();
        }

        SurveyWithQuestions surveyWithQuestions = new SurveyWithQuestions(
                surveyName,
                surveyDescription,
                questions,
                surveyAuthorId,
                accessCodeIfExistingPpt
        );

        String serializedSurvey = (new Gson()).toJson(surveyWithQuestions);
        String serializedSurveyWithRequestType = "{\"request_type\":\"create_survey\",\"survey\":" + serializedSurvey + "}";

        RequestBody surveyDetailsAsMessagePart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), serializedSurveyWithRequestType);

        new SendDataAndProcessResponseTask(
                ServiceGenerator.createService(WebService.class).createSurvey(
                        pptAsMessagePart,
                        surveyDetailsAsMessagePart
                ),
                new SendDataAndProcessResponseTask.PostActions() {
                    @Override
                    public void onSuccess(Object genericResponse) {
                        boolean response = (boolean) genericResponse;
                        if (response) {
                            Toast.makeText(CreateSurvey.this,
                                    R.string.survey_created_successfully,
                                    Toast.LENGTH_LONG
                            ).show();
                            myPptsLoaded = false;
                            discardChanges.performClick();
                        }
                        else {
                            Toast.makeText(CreateSurvey.this, R.string.presentation_store_error, Toast.LENGTH_SHORT).show();
                        }
                        findViewById(R.id.activity_create_survey).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(CreateSurvey.this,
                                R.string.survey_creation_error,
                                Toast.LENGTH_LONG
                        ).show();
                        findViewById(R.id.activity_create_survey).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                }
        );


        findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_create_survey).setClickable(false);
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
                intent.putExtra("id",surveyAuthorId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, CreateSurvey.class);
                intent.putExtra("id",surveyAuthorId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_third_fragment:
                intent = new Intent(this, GetCode.class);
                intent.putExtra("id",surveyAuthorId);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        ArrayList<String> serializedQuestions = new ArrayList<>();
        for (Question q : this.questions) {
            serializedQuestions.add(gson.toJson(q));
        }
        outState.putStringArrayList("questions", serializedQuestions);
        //outState.putBoolean("myPptsLoaded", this.myPptsLoaded);
        if (this.myPptsLoaded) {
            RadioGroup rg = ((RadioGroup) ((LinearLayout)(findViewById(R.id.existingPptsLV))).getChildAt(1));
            int numOfMyPpts = rg.getChildCount();
            ArrayList<String> myPptNames = new ArrayList<>();
            ArrayList<String> myPptAccessCodes = new ArrayList<>();
            for (int i=0; i<numOfMyPpts; i++) {
                TextView tv = (TextView)rg.getChildAt(i);
                myPptNames.add(tv.getText().toString());
                myPptAccessCodes.add((String)tv.getTag());
            }
            outState.putStringArrayList("myPptNames", myPptNames);
            outState.putStringArrayList("myPptAccessCodes", myPptAccessCodes);
            outState.putInt("myPptId", rg.getCheckedRadioButtonId());
        }
        else {
            if (this.uriOfSelectedFile != null) {
                outState.putString("uri", this.uriOfSelectedFile.toString());
            }
        }
        outState.putString("surveyAuthorId", this.surveyAuthorId);
        //outState.putString("surveyName", ((EditText)findViewById(R.id.title_input)).getText().toString());
        //outState.putString("surveyDescription", ((EditText)findViewById(R.id.description_input)).getText().toString());
        if (this.cdd != null) {
            outState.putBundle("incompleteQuestion", this.cdd.onSaveInstanceState());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Gson gson = new Gson();
        super.onRestoreInstanceState(savedInstanceState);

        for (String q : savedInstanceState.getStringArrayList("questions")) {
            this.questions.add(gson.fromJson(q, Question.class));
        }
        //this.myPptsLoaded = savedInstanceState.getBoolean("myPptsLoaded");
        this.myPptsLoaded = this.existingPptOption.isChecked();
        if (this.myPptsLoaded) {
            RadioGroup rg = ((RadioGroup) ((LinearLayout)(findViewById(R.id.existingPptsLV))).getChildAt(1));
            int i = 0;
            for (String accessCode : savedInstanceState.getStringArrayList("myPptAccessCodes")) {
                RadioButton rb = new RadioButton(this);
                rb.setId(i);
                rb.setTag(accessCode);
                rb.setText(savedInstanceState.getStringArrayList("myPptNames").get(i));
                rg.addView(rb);
                i++;
            }
            int idOfSelectedPpt = savedInstanceState.getInt("myPptId");
            if (idOfSelectedPpt != -1) {
                rg.check(idOfSelectedPpt);
            }
        }
        else {
            String uriString = savedInstanceState.getString("uri");
            if (uriString != null) {
                this.uriOfSelectedFile = Uri.parse(uriString);
                final String path = FileUtils.getPath(this, this.uriOfSelectedFile);
                ((Button)findViewById(R.id.button_attach_presentation)).setText(R.string.change_presentation_caption);
                ((TextView)findViewById(R.id.attached_file_name)).setText(path.substring(path.lastIndexOf('/') + 1));
            }
        }

        this.surveyAuthorId = savedInstanceState.getString("surveyAuthorId");
        //((EditText)findViewById(R.id.title_input)).setText(savedInstanceState.getString("surveyName"));
        //((EditText)findViewById(R.id.description_input)).setText(savedInstanceState.getString("surveyDescription"));
        this.loadData();
        Bundle incompleteQuestionBundle = savedInstanceState.getBundle("incompleteQuestion");
        if (incompleteQuestionBundle != null) {
            cdd = new AddQuestion(this, this.questions);
            cdd.onRestoreInstanceState(incompleteQuestionBundle);
            cdd.show();
        }
    }
}