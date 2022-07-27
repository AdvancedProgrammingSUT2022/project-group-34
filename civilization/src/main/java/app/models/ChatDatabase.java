package app.models;

import app.models.connection.ChatMessage;

import java.util.ArrayList;

public class ChatDatabase {
    private static ArrayList<ChatMessage> globalChatMessages = new ArrayList<>();

    public static ArrayList<ChatMessage> getGlobalMessages() {
        return globalChatMessages;
    }

    public static void setGlobalMessages(ArrayList<ChatMessage> globalChatMessages) {
        ChatDatabase.globalChatMessages = globalChatMessages;
    }

    public static void addGlobalMessage(ChatMessage ChatMessage) {
        globalChatMessages.add(ChatMessage);
    }
}
