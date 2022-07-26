package app.views.commandLineMenu;

import app.models.connection.Message;
import app.models.connection.Processor;

public class MainMenu extends Menu {

    private static Message message = new Message();

    //Processes commands related with main menu
    static void processOneCommand() {
        Processor processor;
        while (Menu.getCurrentMenu().equals("main")) {
            isReceivedResponse = false;
            message = new Message();
            processor = new Processor(getInput());
            Menu.sendProcessor(processor, true);
            waitForResponse(-1);
        }
    }

    public static void setAndPrintMessage(Message receivedMessage) {
        Menu.setMessage(receivedMessage, message);
        printMessage(receivedMessage);

    }

}
