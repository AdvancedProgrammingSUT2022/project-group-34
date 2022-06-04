package app.models;

public class Notification {

    private final String message;
    private int tern;
    private final String type;

    public Notification(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public void accept(String type) {
    }

    public void refuse(String type) {
    }

    public int getTern() {
        return tern;
    }

    public void setTern(int tern) {
        this.tern = tern;
    }
}
