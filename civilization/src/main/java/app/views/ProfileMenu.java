package app.views;

import app.models.connection.Message;
import app.serverView.ServerProfileMenu;

public class ProfileMenu extends Menu {

    private static Message message = new Message();

    //Processes commands related with profile menu
    static void processOneCommand() {
        Processor processor;

        while (getCurrentMenu().equals("profile")) {
            processor = new Processor(getInput());
            ServerProfileMenu.getInstance().processOneProcessor(processor);
            printMessage(message);
        }
    }

    private static void printMessage(Message message) {
        if (message.getMessage() != null)
            System.out.println(message.getMessage());
    }

    public static void setMessage(Message message) {
        ProfileMenu.message = message;
    }
}
