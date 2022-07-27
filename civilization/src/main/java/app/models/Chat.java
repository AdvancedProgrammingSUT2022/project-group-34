package app.models;

import app.models.Message;

import java.util.ArrayList;

public class Chat {
    private int id;
    private String username1;
    private String username2;
    private ArrayList<Message> messages;

    public Chat(String username1, String username2) {
        this.username1 = username1;
        this.username2 = username2;
        messages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }
}
