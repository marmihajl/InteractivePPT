package hr.air.interactiveppt;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.air.interactiveppt.entities.Option;

/**
 * Created by marin on 9.11.2016..
 */

public class AnswerViewHolder extends ChildViewHolder {

    @BindView(R.id.txtOption)
    TextView optionText;

    QuestionRecyclerAdapter mAdapter;

    private Option option;
    View mViewItem;

    public  AnswerViewHolder(View itemView, QuestionRecyclerAdapter adapter){
        super(itemView);
        mViewItem = itemView;
        mAdapter = adapter;
        ButterKnife.bind(this,itemView);
    }

    public void bind(Option o){
        option = o;
        optionText.setText(option.getOptionText());
    }

}
