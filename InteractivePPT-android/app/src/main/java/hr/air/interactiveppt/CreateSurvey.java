package hr.air.interactiveppt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class CreateSurvey extends AppCompatActivity {

    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<Answer> answers = new ArrayList<Answer>();
    private QuestionRecyclerAdapter adapter;
    AddQuestion cdd;
    RecyclerView mRecycler;
    boolean show = false;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);

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
