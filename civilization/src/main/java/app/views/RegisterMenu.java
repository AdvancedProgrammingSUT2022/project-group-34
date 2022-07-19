package app.views;

import app.models.connection.Message;
import app.serverView.ServerRegisterMenu;

public class RegisterMenu extends Menu {

    private static final Message message = new Message();

    //Proccesse commands related with register menu
    static void processOneCommand() {
        Processor processor;
        while (Menu.getCurrentMenu().equals("register")) {
            processor = new Processor(getInput());
            ServerRegisterMenu.getInstance().proccessOneProcessor(processor);
            //printMessage(message);
            //System.out.println("RegisterMenu processOneCommand Menu.getCurrentMenu() : " + Menu.getCurrentMenu());
        }
    }

    public static void setAndPrintMessage(Message receivedMessage) {
        Menu.setMessage(receivedMessage,message);
        printMessage(receivedMessage);

    }
}