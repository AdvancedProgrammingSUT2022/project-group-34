package app.models.connection;

public class Message {
    String message;
    String currentMenu;

    public Message() {
        this.message = null;
        this.currentMenu = null;
    }

    public Message(String message) {
        this.message = message;
        this.currentMenu = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCurrentMenu(String main) {
    }
}
