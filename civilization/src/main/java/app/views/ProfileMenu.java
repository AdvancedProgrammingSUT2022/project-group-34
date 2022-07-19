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

    public static void setMessage(Message message) {
        Menu.setMessage(message);
        ProfileMenu.message = message;
    }
}
