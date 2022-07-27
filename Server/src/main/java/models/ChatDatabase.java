package models;

import java.util.ArrayList;
import java.util.Comparator;

public class ChatDatabase {
    private static ArrayList<Message> globalMessages = new ArrayList<>();

    public static ArrayList<Message> getGlobalMessages() {
        globalMessages.sort(Comparator.comparing(Message::getSentAt));
        return globalMessages;
    }

    public static void setGlobalMessages(ArrayList<Message> globalMessages) {
        ChatDatabase.globalMessages = globalMessages;
    }

    public static void addGlobalMessage(Message message) {
        globalMessages.add(message);
    }
}
