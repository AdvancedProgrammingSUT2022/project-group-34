package app.models.connection;

import java.time.LocalDateTime;

public class ChatMessage {
    private String senderUsername;
    private LocalDateTime sentAt;
    private String text;

    public ChatMessage(String senderUsername, String text) {
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
}