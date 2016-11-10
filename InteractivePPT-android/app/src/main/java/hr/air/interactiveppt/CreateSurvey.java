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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CreateSurvey extends AppCompatActivity {

    private static final int REQUEST_CODE = 6384; // onActivityResult request code

    @Nullable
    private static Uri uri = null;

    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Answer> answers = new ArrayList<Answer>();
    private QuestionRecyclerAdapter adapter;
    AddQuestion cdd;
    RecyclerView mRecycler;
    boolean show = false;
    Button b;

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
        // TO DO
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
                        uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
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
                                uri = null;
                            }
                        } catch (Exception e) {
                            Log.e("CreateSurvey", "File select error", e);
                            uri = null;
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

        mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        Button addQuestion = (Button)findViewById(R.id.btnAddQuestion);
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
}
