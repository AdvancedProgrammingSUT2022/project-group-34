package app.views;

import app.models.connection.Message;

public class ProfileMenu extends Menu {

    private static Message message = new Message();

    //Processes commands related with profile menu
    static void processOneCommand() {
        Processor processor;

        while (getCurrentMenu().equals("profile")) {
            isReceivedResponse = false;
            message = new Message();
            processor = new Processor(getInput());
            Menu.sendProcessor(processor);
            waitForResponse(-1);
        }
    }

    public static void setAndPrintMessage(Message receivedMessage) {
        Menu.setMessage(receivedMessage, message);
        printMessage(receivedMessage);
    }
}
