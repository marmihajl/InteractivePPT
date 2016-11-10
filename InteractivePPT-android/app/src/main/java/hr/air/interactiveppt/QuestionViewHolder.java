package hr.air.interactiveppt;


import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marin on 9.11.2016..
 */

public class QuestionViewHolder extends ParentViewHolder{
    @BindView(R.id.txtQuestion)
    TextView mQuestion;
    @BindView(R.id.txtQuestionType)
    TextView mQuestionType;

    View mItemView;

    public QuestionViewHolder(View itemView){
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this,itemView);
    }

    public void bind(Question question){
        mQuestion.setText(question.getQuestionText());
        switch (question.getQuestionType()){
            case 1:
                mQuestionType.setText("Single chose");
                break;
            case 2:
                mQuestionType.setText("Multiple chose");
                break;
            case 3:
                mQuestionType.setText("Text edit");
                break;
        }
    }
}