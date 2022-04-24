package views;

import controllers.UserController;

import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentMenu = "register";

    protected static void run() {

        while (true) {
            RegisterMenu.processOneCommand();
        }
    }

    protected static String getInput() {
        return scanner.nextLine().trim();
    }

    protected static void setCurrentMenu(String menuName) {
        currentMenu = menuName;
    }

    public static String getCurrentMenu() {
        return currentMenu;
    }

    protected static void handleMenuCategoryCommand(Processor processor) {
        switch (processor.getSection()) {
            case "enter":
                menuEnter(processor);
                break;
            case "exit":
                menuExit();
                break;
            case "show-current":
                menuShowCurrent();
                break;
            default:
                invalidCommand();
                break;
        }
    }

    protected static void menuEnter(Processor processor) {
        switch (processor.getSubSection()) {
            case "register":
                System.out.println("Menu navigation is not possible");
                break;
            case "main":
                if (currentMenu.equals("register")) System.out.println("Please login first");
                else setCurrentMenu("main");
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

    protected static void menuExit() {
        if (getCurrentMenu().equals("register")) setCurrentMenu("end");
        else if (getCurrentMenu().equals("main")) {
            setCurrentMenu("register");
            UserController.getInstance().setLoggedInUser(null);
        } else setCurrentMenu("main");
    }

    protected static void menuShowCurrent() {
        System.out.println(currentMenu);
    }

    protected static void invalidCommand() {
        System.out.println("Invalid command!");
    }
}