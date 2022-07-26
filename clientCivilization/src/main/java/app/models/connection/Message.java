package app.models.connection;

import java.util.HashMap;

public class Message {
    private final StringBuilder message;
    private String currentMenu;
    private String whichMenu;
    private final HashMap<String, Object> data = new HashMap<>();
    private boolean isSuccessful;

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

    public String getWhichMenu() {
        return whichMenu;
    }

    public void setWhichMenu(String whichMenu) {
        this.whichMenu = whichMenu;
    }

    public Object getData(String name) {
        return data.get(name);
    }

    public void addData(String name, String object){
        data.put(name, object);
    }

    @Override
    public String toString() {
        return "Message{" +
                "message=" + message +
                ", currentMenu='" + currentMenu + '\'' +
                ", whichMenu='" + whichMenu + '\'' +
                '}';
    }

    public HashMap<String, Object> getAllData() {
        return data;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
