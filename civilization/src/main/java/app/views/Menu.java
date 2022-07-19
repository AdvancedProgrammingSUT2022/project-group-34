package app.views;

import app.models.connection.Message;

import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static String currentMenu = "register";


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
        currentMenu = message.getCurrentMenu();
    }

    protected static void printMessage(Message message) {
        if (message.getMessageString().length() != 0) {
            System.out.println(message.getMessageString());
        }
    }
}