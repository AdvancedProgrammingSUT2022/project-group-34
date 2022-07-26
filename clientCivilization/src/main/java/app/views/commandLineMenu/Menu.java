package app.views.commandLineMenu;

import app.controllers.ConnectionController;
import app.controllers.InputController;
import app.models.connection.Message;
import app.models.connection.Processor;

import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static String currentMenu = "register";
    protected static boolean isReceivedResponse = false;

    //Setters and Getters for fields of the class
    public static String getCurrentMenu() {
        return currentMenu;
    }


    public static void setMessage(Message message) {

        if (message.getCurrentMenu() != null) {
            currentMenu = message.getCurrentMenu();
        }
        if (!message.getAllData().containsKey("continue"))
            isReceivedResponse = true;
    }

    public static void sendProcessor(Processor processor){
        sendProcessor(processor, false);
    }
    public static void sendProcessor(Processor processor, boolean start){
        if (processor.isGetOrSet())
            processor.setWhichMenu("isGetOrSet");
        else
            processor.setWhichMenu(currentMenu);

        ConnectionController.send(processor);
        if (start)
            new InputController().run();
    }

    protected static void printMessage(Message message) {
        if (message.getMessageString().length() != 0) {
            System.out.println("MESSAGE => " + message.getMessageString());
        }
    }

    protected static void waitForResponse(int limit) {
        int k = 0;
        for (int i = 0; i != limit && !isReceivedResponse; i++) {
            if (i % 4 == 0) k++;
        }
    }

    public static String getInput(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

    public static void sendProcessorAndIgnoreResponse(Processor processor) {
        Menu.sendProcessor(processor);
        InputController.getInstance().getMessage();
    }
}