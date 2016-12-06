package hr.air.interactiveppt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.air.interactiveppt.entities.Option;
import hr.air.interactiveppt.entities.Question;
import hr.air.interactiveppt.entities.SurveyWithQuestions;
import hr.air.interactiveppt.webservice.CommunicationHandler;
import hr.air.interactiveppt.webservice.ServiceGenerator;
import hr.air.interactiveppt.webservice.WebService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSurvey extends AppCompatActivity {

    private static final int REQUEST_CODE = 6384; // onActivityResult request code

    @Nullable
    private static Uri uriOfSelectedFile = null;

    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Option> selectedOptions = new ArrayList<Option>();
    private QuestionRecyclerAdapter adapter;
    AddQuestion cdd;
    RecyclerView mRecycler;
    boolean show = false;
    String surveyAuthorId;

    @BindView(R.id.button_attach_presentation)
    Button attachPresentation;

    @BindView(R.id.button_save_changes)
    Button saveChanges;

    @BindView(R.id.button_discard_changes)
    Button discardChanges;


    @OnClick(R.id.button_attach_presentation)
    protected void attachPresentationButtonClick(View view){
        showFileChooser();
    }

    @OnClick(R.id.button_save_changes)
    protected void sendRequestForSaving(View view){

        String surveyName = ((EditText)findViewById(R.id.title_input)).getText().toString();
        String surveyDescription = ((EditText)findViewById(R.id.description_input)).getText().toString();

        String reasonsOfIncompletion;
        if ((reasonsOfIncompletion = getReasonsWhyCurrentSurveyIsntComplete(surveyName, surveyDescription)) != "") {
            Toast.makeText(CreateSurvey.this, "Neuspjeh kod kreiranja:" + reasonsOfIncompletion, Toast.LENGTH_LONG).show();
            return;
        }

        MultipartBody.Part pptAsMessagePart = null;

        if (uriOfSelectedFile != null) {
            File fileHandler = FileUtils.getFile(getBaseContext(), uriOfSelectedFile);  //this instead of getBaseContext was before
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileHandler);
            pptAsMessagePart = MultipartBody.Part.createFormData("ppt", fileHandler.getName(), requestFile);
        }

        SurveyWithQuestions surveyWithQuestions = new SurveyWithQuestions(
                surveyName,
                surveyDescription,
                questions,
                surveyAuthorId
        );

        String serializedSurvey = (new Gson()).toJson(surveyWithQuestions);

        RequestBody surveyDetailsAsMessagePart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), serializedSurvey);

        CommunicationHandler.SendDataAndProcessResponse(
                ServiceGenerator.createService(WebService.class).createSurvey(
                        pptAsMessagePart,
                        surveyDetailsAsMessagePart
                ),
                new BiConsumer<Call<Boolean>, Response<Boolean>>() {
                    @Override
                    public void accept(Call<Boolean> call, Response<Boolean> response) {
                        Toast.makeText(CreateSurvey.this,
                                "Anketa je uspješno kreirana!",
                                Toast.LENGTH_LONG
                        ).show();
                        findViewById(R.id.activity_create_survey).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                new BiConsumer<Call<Boolean>, Throwable>() {
                    @Override
                    public void accept(Call<Boolean> sCall, Throwable throwable) {
                        Toast.makeText(CreateSurvey.this,
                                "Neuspjeh kod slanja ankete! Provjerite vezu s Internetom",
                                Toast.LENGTH_LONG
                        ).show();
                        findViewById(R.id.activity_create_survey).setClickable(true);
                        findViewById(R.id.loading_panel).setVisibility(View.GONE);
                    }
                },
                true,
                getBaseContext()
        );


        findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_create_survey).setClickable(false);
    }

    @OnClick(R.id.button_discard_changes)
    protected void returnBackToHome(View view){
        finish();
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
        ButterKnife.bind(this);
        surveyAuthorId = getIntent().getStringExtra("id");

        findViewById(R.id.loading_panel).setVisibility(View.GONE);
        mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        Button addQuestion = (Button)findViewById(R.id.btn_add_question);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdd=new AddQuestion(CreateSurvey.this,questions);
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
            if(mRecycler != null) {
                adapter = new QuestionRecyclerAdapter(this, questionItemList);
                mRecycler.setAdapter(adapter);
                mRecycler.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }

    private String getReasonsWhyCurrentSurveyIsntComplete(String title, String description) {
        String reasonsOfIncompetion = "";
        if (title.isEmpty()) {
            reasonsOfIncompetion += "\nNaslov ankete nije postavljen!";
        }
        if (description.isEmpty()) {
            reasonsOfIncompetion += "\nOpis ankete nije postavljen!";
        }
        if (questions.isEmpty()) {
            reasonsOfIncompetion += "\nAnketa je trenutno bez pitanja!";
        }
        return reasonsOfIncompetion;
    }
}