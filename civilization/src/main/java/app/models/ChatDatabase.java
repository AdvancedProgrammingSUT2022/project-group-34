package app.models;

import java.util.ArrayList;

public class ChatDatabase {
    private static ArrayList<Message> globalMessages = new ArrayList<>();

    public static ArrayList<Message> getGlobalMessages() {
        return globalMessages;
    }

    public static void setGlobalMessages(ArrayList<Message> globalMessages) {
        ChatDatabase.globalMessages = globalMessages;
    }

    public static void addGlobalMessage(Message message) {
        globalMessages.add(message);
    }
}
