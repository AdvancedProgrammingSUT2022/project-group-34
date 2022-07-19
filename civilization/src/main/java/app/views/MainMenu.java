package app.views;

import app.models.connection.Message;
import app.serverView.ServerMainMenu;

public class MainMenu extends Menu {

    private static Message message = new Message();

    //Processes commands related with main menu
    static void processOneCommand() {
        Processor processor;

        while (Menu.getCurrentMenu().equals("main")) {
            processor = new Processor(getInput());
            ServerMainMenu.getInstance().processOneProcessor(processor);
            printMessage(message);
        }
    }

    public static void setMessage(Message message) {
        Menu.setMessage(message);
        MainMenu.message = message;
    }

}
