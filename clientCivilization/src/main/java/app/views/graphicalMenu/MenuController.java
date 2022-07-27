package app.views.graphicalMenu;

import app.controllers.ConnectionController;
import app.controllers.InputController;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.views.commandLineMenu.Menu;

public class MenuController {

    public static String currentMenu = "register";
    public static boolean isReceivedResponse = false;//Setters and Getters for fields of the class

    public static String getCurrentMenu() {
        return currentMenu;
    }

    public static void setMessage(Message message) {
        System.out.println(message);
        if (message.getCurrentMenu() != null) {
            currentMenu = message.getCurrentMenu();
        }
        if (!message.getAllData().containsKey("continue"))
            isReceivedResponse = true;
    }

    public static void sendProcessor(Processor processor) {
        sendProcessor(processor, false);
    }

    public static void sendProcessor(Processor processor, boolean start) {
        if (processor.isGetOrSet())
            processor.setWhichMenu("isGetOrSet");
        else
            processor.setWhichMenu(currentMenu);

        ConnectionController.send(processor);
        if (start)
            new InputController().run();
    }

    public static void printMessage(Message message) {
        if (message.getMessageString().length() != 0) {
            System.out.println("MESSAGE => " + message.getMessageString());
        }
    }

    public static void sendProcessorAndIgnoreResponse(Processor processor) {
        MenuController.sendProcessor(processor);
        InputController.getInstance().getMessage();
    }
}