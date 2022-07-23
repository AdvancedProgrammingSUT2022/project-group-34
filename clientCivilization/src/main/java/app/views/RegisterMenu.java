package app.views;

import app.models.connection.Message;

public class RegisterMenu extends Menu {

    private static Message message = new Message();

    //Proccesse commands related with register menu
    static void processOneCommand() {
        Processor processor;
        while (Menu.getCurrentMenu().equals("register")) {
            isReceivedResponse = false;
            message = new Message();
            processor = new Processor(getInput());
            Menu.sendProcessor(processor);
            waitForResponse(-1);
        }
    }

    public static void setAndPrintMessage(Message receivedMessage) {
        Menu.setMessage(receivedMessage,message);
        printMessage(receivedMessage);

    }
}