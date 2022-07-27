package models;

import java.time.LocalDateTime;

public class Message {
    private String senderUsername;
    private LocalDateTime sentAt;
    private String text;
    private int id;

    public Message(String senderUsername, String text) {
        this.senderUsername = senderUsername;
        this.sentAt = LocalDateTime.now();
        this.text = text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getText() {
        return text;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
