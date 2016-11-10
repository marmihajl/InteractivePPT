package hr.air.interactiveppt;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;

/**
 * Created by marin on 9.11.2016..
 */

public class ExpandableQuestionItem extends Question implements Parent<Answer> {

    private ArrayList<Answer> answers;

    public ExpandableQuestionItem(Question question) {
        super(question);
        this.answers = question.getAnswers();

    }

    @Override
    public ArrayList<Answer> getChildList() {
        return answers;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
