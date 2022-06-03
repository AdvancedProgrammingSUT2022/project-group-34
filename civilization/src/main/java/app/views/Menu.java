package app.views;

import app.controllers.GameController;
import app.controllers.UserController;

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


    //Setters and Getters for fields of the class
    protected static void setCurrentMenu(String menuName) {
        currentMenu = menuName;
    }

    public static String getCurrentMenu() {
        return currentMenu;
    }


    //Scans one line of input
    protected static String getInput() {
        return scanner.nextLine().trim();
    }


    //Handles commands that start with "menu"
    protected static void handleMenuCategoryCommand(Processor processor) {
        if (processor.getSection() == null) invalidCommand();
        else {
            if (processor.getSection().equals("enter")) menuEnter(processor);
            else if (processor.getSection().equals("exit")) menuExit();
            else if (processor.getSection().equals("show-current")) menuShowCurrent();
            else invalidCommand();
        }
    }


    //Enters the asked menu if its possible
    //menu enter <menu name>
    protected static void menuEnter(Processor processor) {
        if (processor.getSubSection() == null) {
            invalidCommand();
            return;
        }
        switch (processor.getSubSection()) {
            case "register":
                System.out.println("Menu navigation is not possible");
                break;
            case "main":
                if (currentMenu.equals("register")) System.out.println("Please login first");
                else {
                    setCurrentMenu("main");
                    GameController.getInstance().setGame(null);
                }
                break;
            case "profile":
                if (currentMenu.equals("register")) System.out.println("Please login first");
                else if (currentMenu.equals("game")) System.out.println("Menu navigation is not possible");
                else setCurrentMenu("profile");
                break;
            case "game":
                if (currentMenu.equals("register")) System.out.println("Please login first");
                else System.out.println("Menu navigation is not possible");
                break;
            default:
                invalidCommand();
                break;
        }
    }


    //Exit from current menu to upper menu(register-->main-->game/profile)
    //menu exit
    protected static void menuExit() {
        if (getCurrentMenu().equals("register")) setCurrentMenu("end");
        else if (getCurrentMenu().equals("main")) {
            setCurrentMenu("register");
            UserController.getInstance().setLoggedInUser(null);
        } else setCurrentMenu("main");
        GameController.getInstance().setGame(null);
    }


    //Shows the menu we are in
    //menu show-current
    protected static void menuShowCurrent() {
        System.out.println(currentMenu.substring(0, 1).toUpperCase() + currentMenu.substring(1) + " Menu");
    }


    //Prints "invalid command" if command is invalid
    protected static void invalidCommand() {
        System.out.println("Invalid command!");
    }
}