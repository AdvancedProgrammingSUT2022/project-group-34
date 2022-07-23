package app.views;

import app.controllers.ConnectionController;
import app.controllers.InputController;
import app.models.connection.Message;

import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static String currentMenu = "register";
    protected static boolean isReceivedResponse = false;

    //Controls menus in the program
    public static void run() {
        while (true) {
            RegisterMenu.processOneCommand();
            if (currentMenu.equals("end")) return;
            while (true) {
                MainMenu.processOneCommand();
                if (currentMenu.equals("register")) break;
                else if (currentMenu.equals("profile")) ProfileMenu.processOneCommand();
                else GameMenu.processOneCommand();
            }
        }
    }

    //Scans one line of input
    public static String getInput() {
        return scanner.nextLine().trim();
    }

    //Setters and Getters for fields of the class
    public static String getCurrentMenu() {
        return currentMenu;
    }


    protected static void setMessage(Message message, Message message1) {
        message1.copy(message);

        if (message.getCurrentMenu() != null) {
            currentMenu = message.getCurrentMenu();
        }
        if (!message.getAllData().containsKey("continue"))
            isReceivedResponse = true;
    }

    protected static void sendProcessor(Processor processor){
        sendProcessor(processor, true);
    }
    protected static void sendProcessor(Processor processor, boolean start){
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
}