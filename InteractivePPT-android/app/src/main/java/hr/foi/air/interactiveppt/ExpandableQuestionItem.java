package hr.foi.air.interactiveppt;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;

import hr.foi.air.interactiveppt.entities.Option;
import hr.foi.air.interactiveppt.entities.Question;

/**
 * Created by marin on 9.11.2016..
 */

public class ExpandableQuestionItem extends Question implements Parent<Option> {

    private ArrayList<Option> options;

    public ExpandableQuestionItem(Question question) {
        super(question);
        this.options = question.getOptions();

    }

    @Override
    public ArrayList<Option> getChildList() {
        return options;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
