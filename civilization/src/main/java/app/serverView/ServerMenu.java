package app.serverView;

import app.controllers.GameController;
import app.controllers.UserController;
import app.models.connection.Message;
import app.views.Processor;

import java.util.Scanner;

public class ServerMenu {

    private final Scanner scanner = new Scanner(System.in);
    private String currentMenu = "register";
    private Message message;

    protected ServerMenu(){}

    protected void setCurrentMenu(String menuName) {
        currentMenu = menuName;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }


    //Scans one line of input
    protected String getInput() {
        return scanner.nextLine().trim();
    }


    //Handles commands that start with "menu"
    protected void handleMenuCategoryCommand(Processor processor) {
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
    protected void menuEnter(Processor processor) {
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
    protected void menuExit() {
        if (getCurrentMenu().equals("register")) setCurrentMenu("end");
        else if (getCurrentMenu().equals("main")) {
            setCurrentMenu("register");
            UserController.getInstance().setLoggedInUser(null);
        } else setCurrentMenu("main");
        GameController.getInstance().setGame(null);
    }


    //Shows the menu we are in
    //menu show-current
    protected void menuShowCurrent() {
        System.out.println(currentMenu.substring(0, 1).toUpperCase() + currentMenu.substring(1) + " Menu");
    }


    //Prints "invalid command" if command is invalid
    protected void invalidCommand() {
        System.out.println("Invalid command!");
    }

    protected String getInvalidCommand() {
        return "Invalid command!";
    }


    protected void sendMessage() {
        message.setCurrentMenu(currentMenu);
    }
}
