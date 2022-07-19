package app.views;

import app.models.connection.Message;
import app.serverView.ServerGameMenu;

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
    protected static String getInput() {
        return scanner.nextLine().trim();
    }

    //Setters and Getters for fields of the class
    public static String getCurrentMenu() {
        return currentMenu;
    }

    //Handles commands that start with "menu"
    protected static void handleMenuCategoryCommand(Processor processor) {
        ServerGameMenu.getInstance().handleMenuCategoryCommand(processor,new Message());
    }

    //Prints "invalid command" if command is invalid
    protected static void invalidCommand() {
        System.out.println("Invalid command!");
    }

    protected static void setMessage(Message message) {
        currentMenu = message.getCurrentMenu();
    }
}