package hr.foi.air.interactiveppt.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeko868 on 25.2.2017..
 */
public class ActiveChatMessagesList {
    private static ActiveChatMessagesList ourInstance = new ActiveChatMessagesList();

    public static ActiveChatMessagesList getInstance() {
        return ourInstance;
    }

    private List<ChatMessage> chatMessages;

    private ActiveChatMessagesList() {
        this.chatMessages = new ArrayList<ChatMessage>();
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addChatMessageIntoList(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }
}
