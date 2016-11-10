package hr.air.interactiveppt;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marin on 9.11.2016..
 */

public class AnswerViewHolder extends ChildViewHolder {

    @BindView(R.id.txtAnswer)
    TextView answerText;

    QuestionRecyclerAdapter mAdapter;

    private Answer answer;
    View mViewItem;

    public  AnswerViewHolder(View itemView, QuestionRecyclerAdapter adapter){
        super(itemView);
        mViewItem = itemView;
        mAdapter = adapter;
        ButterKnife.bind(this,itemView);
    }

    public void bind(Answer a){
        answer = a;
        answerText.setText(answer.getAnswerText());
    }

}
