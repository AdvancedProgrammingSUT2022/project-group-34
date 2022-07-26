package app.views.commandLineMenu;

import app.models.connection.Message;
import app.models.connection.Processor;

public class RegisterMenu extends Menu {

    private static Message message = new Message();

    //Proccesse commands related with register menu
    public static void processOneCommand() {
            isReceivedResponse = false;
            message = new Message();
            Processor processor = new Processor(getInput());
            Menu.sendProcessor(processor,true);
            waitForResponse(-1);
    }

    public static void setAndPrintMessage(Message receivedMessage) {
        Menu.setMessage(receivedMessage,message);
        printMessage(receivedMessage);

    }
}