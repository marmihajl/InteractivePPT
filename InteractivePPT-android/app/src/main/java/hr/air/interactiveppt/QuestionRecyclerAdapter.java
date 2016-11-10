package hr.air.interactiveppt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

/**
 * Created by marin on 9.11.2016..
 */

public class QuestionRecyclerAdapter extends ExpandableRecyclerAdapter<ExpandableQuestionItem,
        Answer, QuestionViewHolder, AnswerViewHolder> {

    private LayoutInflater layoutInflater;
    public QuestionRecyclerAdapter(Context context, @NonNull List<ExpandableQuestionItem> parentList) {
        super(parentList);
        layoutInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View questionView = layoutInflater.inflate(R.layout.question_list_item, parentViewGroup, false);
        return new QuestionViewHolder(questionView);
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View answerView = layoutInflater.inflate(R.layout.answer_list_item, childViewGroup, false);
        return new AnswerViewHolder(answerView, this);
    }

    @Override
    public void onBindParentViewHolder(@NonNull QuestionViewHolder parentViewHolder, int parentPosition, @NonNull ExpandableQuestionItem parentListItem) {
        ExpandableQuestionItem expandableQuestionItem = (ExpandableQuestionItem)parentListItem;
        parentViewHolder.bind(expandableQuestionItem);
    }

    @Override
    public void onBindChildViewHolder(@NonNull AnswerViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Answer childListItem) {
        Answer answer = childListItem;
        childViewHolder.bind(answer);
    }
}
