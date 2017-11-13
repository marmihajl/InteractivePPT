package hr.foi.air.interactiveppt;

/**
 * Created by zeko868 on 25.2.2017..
 */

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import hr.foi.air.interactiveppt.entities.ChatMessage;

public class ChatMessageListAdapter extends BaseAdapter {

    private Activity activity;
    private List <ChatMessage> messages;
    private static LayoutInflater inflater=null;

    public ChatMessageListAdapter(Activity a, List<ChatMessage> m) {
        activity = a;
        messages = m;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return messages.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.list_item, null);
        }

        TextView user = (TextView)vi.findViewById(R.id.message_user);
        TextView time = (TextView)vi.findViewById(R.id.message_time);
        EmojiconTextView text = (EmojiconTextView) vi.findViewById(R.id.message_text);

        ChatMessage message = messages.get(position);

        user.setText(message.getMessageUser());
        time.setText(DateFormat.format("dd.MM.yyyy (HH:mm:ss)", message.getMessageTime()).toString());
        text.setText(message.getMessageText());
        return vi;
    }
}