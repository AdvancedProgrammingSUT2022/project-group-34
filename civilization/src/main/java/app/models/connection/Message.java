package app.models.connection;

import java.util.HashMap;

public class Message {

    private StringBuilder message;
    private String currentMenu;
    private String whichMenu;
    private HashMap<String, Object> data;

    public Message() {
        message = new StringBuilder();
        currentMenu = null;
        whichMenu = null;
        data = new HashMap<>();
    }

    public void clearMessageAndData(){
        message = new StringBuilder();
        data = new HashMap<>();
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

    public void addData(String name, Object object){
        data.put(name, object);
    }

    @Override
    public String toString() {
        return "Message{" +
                "message=" + message +
                ", currentMenu='" + currentMenu + '\'' +
                ", whichMenu='" + whichMenu + '\'' +
                ", data=" + data.toString().length() +
                '}';
    }
}
