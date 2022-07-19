package app.models.connection;

public class Message {

    private final StringBuilder message;
    private String currentMenu;

    public Message() {
        this.message = new StringBuilder();
    }

    public Message(String message) {
        this.message = new StringBuilder(message);
    }

    public String getMessageString() {
        return message.toString();
    }

    public <T> void addLine(T mess) {
        if (message.length() != 0)
            this.message.append("\n");
        this.message.append(mess.toString());
    }

    public void setCurrentMenu(String menu) {
        currentMenu = menu;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setMessage(String messageString) {
        message.setLength(0);
        message.append(messageString);
    }

    public void copy(Message message) {
        setCurrentMenu(message.currentMenu);
        setMessage(message.message.toString());
    }
}
