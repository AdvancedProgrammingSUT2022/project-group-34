package app.views;

import app.models.connection.Message;
import app.serverView.ServerRegisterMenu;

public class RegisterMenu extends Menu {

    private static Message message = new Message();

    //Proccesse commands related with register menu
    static void processOneCommand() {
        Processor processor;
        while (Menu.getCurrentMenu().equals("register")) {
            processor = new Processor(getInput());
            ServerRegisterMenu.getInstance().proccessOneProcessor(processor);
            printMessage(message);
        }
    }

    public static void setMessage(Message message) {
        Menu.setMessage(message);
        RegisterMenu.message = message;
    }
}