package models;

import java.util.ArrayList;
import java.util.Comparator;

public class ChatDatabase {
    private static ArrayList<Message> globalMessages = new ArrayList<>();
    private static ArrayList<Chat> privateChats = new ArrayList<>();

    public static ArrayList<Message> getGlobalMessages() {
        return globalMessages;
    }

    public static void setGlobalMessages(ArrayList<Message> globalMessages) {
        ChatDatabase.globalMessages = globalMessages;
    }

    public static void addGlobalMessage(Message message) {
        globalMessages.add(message);
    }

    public static ArrayList<Chat> getPrivateChats() {
        return privateChats;
    }

    public static void setPrivateChats(ArrayList<Chat> privateChats) {
        ChatDatabase.privateChats = privateChats;
    }

    public static void addPrivateChat(Chat chat) {
        privateChats.add(chat);
    }

    public static Chat getChatByUser(User user) {
        String username = user.getUsername();
        for (Chat privateChat : privateChats)
            if (privateChat.getUsername1().equals(username) || privateChat.getUsername2().equals(username))
                return privateChat;
        return null;
    }
}
