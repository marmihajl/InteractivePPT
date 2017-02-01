package hr.air.interactiveppt;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.air.interactiveppt.entities.Question;

/**
 * Created by marin on 9.11.2016..
 */

public class QuestionViewHolder extends ParentViewHolder{
    Context mContext;
    @BindView(R.id.txtQuestion)
    TextView mQuestion;
    @BindView(R.id.txtQuestionType)
    TextView mQuestionType;

    View mItemView;

    public QuestionViewHolder(View itemView, Context context){
        super(itemView);
        mItemView = itemView;
        mContext = context;
        ButterKnife.bind(this,itemView);
    }

    public void bind(Question question){
        mQuestion.setText(question.getQuestionText());
        mQuestionType.setText(mContext.getResources().getStringArray(R.array.questionTypesDisplayNames)[question.getQuestionType()-1]);
    }
}