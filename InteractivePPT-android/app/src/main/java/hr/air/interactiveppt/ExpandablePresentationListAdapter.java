package hr.air.interactiveppt;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.function.Consumer;

import hr.air.interactiveppt.entities.Presentation;

/**
 * Created by zeko868 on 28.12.2016..
 */

public class ExpandablePresentationListAdapter extends BaseExpandableListAdapter {

    private final List<Presentation> presentationItems;
    private LayoutInflater inflater;
    private Activity activity;
    private Consumer<String> consumer = null;

    public ExpandablePresentationListAdapter(Activity activity, List<Presentation> presentationItems) {
        this.activity = activity;
        this.presentationItems = presentationItems;
        this.inflater = activity.getLayoutInflater();
    }

    public ExpandablePresentationListAdapter(Activity activity, List<Presentation> presentationItems, Consumer<String> consumer) {
        this.activity = activity;
        this.presentationItems = presentationItems;
        this.inflater = activity.getLayoutInflater();
        this.consumer = consumer;
    }

    @Override
    public Object getChild(int presentationItemPosition, int childPosition) {
        return presentationItems.get(presentationItemPosition);
    }

    @Override
    public long getChildId(int presentationItemPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int presentationItemPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Presentation child = (Presentation) getChild(presentationItemPosition, childPosition);
        TextView tvAccessCode = null;
        TextView tvNumOfSurveys = null;
        TextView tvAuthorName = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ppt_row_details, null);
        }
        tvAccessCode = (TextView) convertView.findViewById(R.id.accessCode);
        tvAccessCode.setText(child.accessCode);
        tvNumOfSurveys = (TextView) convertView.findViewById(R.id.numOfSurveys);
        tvNumOfSurveys.setText(String.valueOf(child.numOfSurveys));
        if (child.authorName != null) {
            tvAuthorName = (TextView) convertView.findViewById(R.id.authorName);
            tvAuthorName.setText(child.authorName);
        }
        else {
            convertView.findViewById(R.id.authorNameContainer).setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int presentationItemPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int presentationItemPosition) {
        return presentationItems.get(presentationItemPosition);
    }

    @Override
    public int getGroupCount() {
        return presentationItems.size();
    }

    @Override
    public void onGroupCollapsed(int presentationItemPosition) {
        super.onGroupCollapsed(presentationItemPosition);
    }

    @Override
    public void onGroupExpanded(int presentationItemPosition) {
        super.onGroupExpanded(presentationItemPosition);
    }

    @Override
    public long getGroupId(int presentationItemPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int presentationItemPosition, final boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ppt_row_title, null);
        }
        Presentation presentationItem = (Presentation) getGroup(presentationItemPosition);
        ((TextView) convertView.findViewById(R.id.presentationName)).setText(presentationItem.presentationName.substring(4, presentationItem.presentationName.lastIndexOf('.')));

        if (consumer != null) {
            convertView.setId(presentationItemPosition);
            convertView.findViewById(R.id.proceedButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Handler mainHandler = new Handler(activity.getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            String accessCode = presentationItems.get(((LinearLayout)v.getParent()).getId()).accessCode;
                            consumer.accept(accessCode);
                        }
                    };
                    mainHandler.post(myRunnable);
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
    public boolean isChildSelectable(int presentationItemPosition, int childPosition) {
        return false;
    }
}