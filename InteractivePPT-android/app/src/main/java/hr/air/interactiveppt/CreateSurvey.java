package hr.air.interactiveppt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.air.interactiveppt.responses.ProcessingResultResponse;
import hr.air.interactiveppt.responses.Survey;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class CreateSurvey extends AppCompatActivity {

    private static final int REQUEST_CODE = 6384; // onActivityResult request code

    @Nullable
    private static Uri uriOfSelectedFile = null;

    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Answer> answers = new ArrayList<Answer>();
    private QuestionRecyclerAdapter adapter;
    AddQuestion cdd;
    RecyclerView mRecycler;
    boolean show = false;
    Button b;
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
        new RetrieveFeedTask().execute(surveyName, surveyDescription, surveyAuthorId);

        findViewById(R.id.loading_panel).setVisibility(View.VISIBLE);
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

        b = (Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    public void loadData(){
        List<ExpandableQuestionItem> questionItemList = new ArrayList<ExpandableQuestionItem>();

        if(questions.size() > 0) {
            for (Question store : questions) {
                questionItemList.add(new ExpandableQuestionItem(store));
            }
            if(mRecycler != null) {
                adapter = new QuestionRecyclerAdapter(this, questionItemList);
                mRecycler.setAdapter(adapter);
                mRecycler.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }


    class RetrieveFeedTask extends AsyncTask {

        private Exception exception;


        @Override
        protected Object doInBackground(Object[] objects) {

            MultipartBody.Part pptAsMessagePart = null;

            if (uriOfSelectedFile != null) {
                File fileHandler = FileUtils.getFile(getBaseContext(), uriOfSelectedFile);  //this instead of getBaseContext was before
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), fileHandler);
                pptAsMessagePart = MultipartBody.Part.createFormData("ppt", fileHandler.getName(), requestFile);
            }

            SurveyWithQuestions surveyWithQuestions = new SurveyWithQuestions(
                    objects[0].toString(),
                    objects[1].toString(),
                    questions,
                    objects[2].toString()
            );

            String serializedSurvey = (new Gson()).toJson(surveyWithQuestions);

            RequestBody surveyDetailsAsMessagePart =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), serializedSurvey);

            WebService client = ServiceGenerator.createService(WebService.class);

            Call<ProcessingResultResponse> call = client.createSurvey(
                    pptAsMessagePart,
                    surveyDetailsAsMessagePart
            );

            if(call != null){
                call.enqueue(new Callback<ProcessingResultResponse>() {

                    @Override
                    public void onResponse(Call<ProcessingResultResponse> call, Response<ProcessingResultResponse> response) {
                        if (response.isSuccessful() && response.body().success) {
                            Handler mainHandler = new Handler(getBaseContext().getMainLooper());

                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    findViewById(R.id.loading_panel).setVisibility(View.GONE);
                                    Toast.makeText(CreateSurvey.this,
                                            "Anketa je uspješno kreirana!",
                                            Toast.LENGTH_LONG
                                    ).show();

                                }
                            };
                            mainHandler.post(myRunnable);

                        }

                    }

                    @Override
                    public void onFailure(Call<ProcessingResultResponse> call, Throwable t) {

                    }
                });
            }
            return null;

        }
    }
}

class ServiceGenerator {

    public static final String API_BASE_URL = "http://46.101.68.86/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}

interface WebService {
    @Multipart
    @POST("interactivePPT-server.php")
    Call<ProcessingResultResponse> createSurvey(
            @Part MultipartBody.Part file,
            @Part("json") RequestBody json
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Survey> getDetails(
            @Field("access_code") String accessCode,
            @Field("request_type") String requestType
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<ProcessingResultResponse> registerUser(
            @Field("request_type") String requestType,
            @Field("app_uid") String appUid,
            @Field("name") String name
    );
}