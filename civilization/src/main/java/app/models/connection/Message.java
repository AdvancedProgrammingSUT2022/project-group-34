package app.models.connection;

public class Message {
    StringBuilder message;
    String currentMenu;

    public Message() {
        this.message = new StringBuilder();
        this.currentMenu = null;
    }

    public Message(String message) {
        this.message = new StringBuilder(message);
        this.currentMenu = null;
    }

    public String getMessageString() {
        return message.toString();
    }

    public void addLine(String mess) {
        if (mess.length() != 0)
            this.message.append("\n");
        this.message.append(mess);
    }

    public void setCurrentMenu(String menu) {
        currentMenu = menu;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }
}
