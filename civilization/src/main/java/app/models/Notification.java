package app.models;

import app.models.save.Mock;

public class Notification extends Mock {

    private String message;
    private int tern;
    private String type;

    public Notification(String message, String type) {
        super(null);
        this.message = message;
        this.type = type;
    }

    public Notification() {
        super(0);
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

    @Override
    public Notification getOriginalObject() {
        return this;
    }
}
