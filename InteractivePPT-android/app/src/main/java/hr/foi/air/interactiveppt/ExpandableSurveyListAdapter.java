package hr.foi.air.interactiveppt;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import hr.foi.air.interactiveppt.entities.Survey;

/**
 * Created by zeko868 on 28.12.2016..
 */

public class ExpandableSurveyListAdapter extends BaseExpandableListAdapter {

    private final List<Survey> surveyItems;
    private LayoutInflater inflater;
    private Activity activity;
    private OnProceedButtonClicked consumer = null;

    public ExpandableSurveyListAdapter(Activity activity, List<Survey> surveyItems) {
        this.activity = activity;
        this.surveyItems = surveyItems;
        this.inflater = activity.getLayoutInflater();
    }

    public ExpandableSurveyListAdapter(Activity activity, List<Survey> surveyItems, OnProceedButtonClicked consumer) {
        this.activity = activity;
        this.surveyItems = surveyItems;
        this.inflater = activity.getLayoutInflater();
        this.consumer = consumer;
    }

    @Override
    public Object getChild(int surveyItemPosition, int childPosition) {
        return surveyItems.get(surveyItemPosition);
    }

    @Override
    public long getChildId(int surveyItemPosition, int childPosition) {
        return surveyItems.get(surveyItemPosition).id;
    }

    @Override
    public View getChildView(int surveyItemPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Survey child = (Survey) getChild(surveyItemPosition, childPosition);
        TextView tvId = null;
        TextView tvDescription = null;
        TextView tvNumOfQuestions = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ppt_row_details, null);
        }
        tvId = (TextView) convertView.findViewById(R.id.accessCode);
        tvId.setText(String.valueOf(child.id));
        tvDescription = (TextView) convertView.findViewById(R.id.numOfSurveys);
        tvDescription.setText(child.description);
        tvNumOfQuestions = (TextView) convertView.findViewById(R.id.authorName);
        tvNumOfQuestions.setText(String.valueOf(child.numOfQuestions));
        ((TextView)convertView.findViewById(R.id.accessCodeLabel)).setText("Id ankete: ");
        ((TextView)convertView.findViewById(R.id.numOfSurveysLabel)).setText("Opis ankete: ");
        ((TextView)convertView.findViewById(R.id.authorNameLabel)).setText("Broj pitanja ankete: ");
        return convertView;
    }

    @Override
    public int getChildrenCount(int surveyItemPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int surveyItemPosition) {
        return surveyItems.get(surveyItemPosition);
    }

    @Override
    public int getGroupCount() {
        return surveyItems.size();
    }

    @Override
    public void onGroupCollapsed(int surveyItemPosition) {
        super.onGroupCollapsed(surveyItemPosition);
    }

    @Override
    public void onGroupExpanded(int surveyItemPosition) {
        super.onGroupExpanded(surveyItemPosition);
    }

    @Override
    public long getGroupId(int surveyItemPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int surveyItemPosition, final boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ppt_row_title, null);
        }
        Survey surveyItem = (Survey) getGroup(surveyItemPosition);
        ((TextView) convertView.findViewById(R.id.presentationName)).setText(surveyItem.name);
        convertView.setId( (int)getChildId(surveyItemPosition, 0) );

        if (consumer != null) {
            convertView.findViewById(R.id.proceedButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    String id = String.valueOf(((View)v.getParent()).getId());
                    consumer.onClick(id);
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int surveyItemPosition, int childPosition) {
        return false;
    }
}