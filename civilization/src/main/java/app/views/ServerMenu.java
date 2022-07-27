package app.views;

import app.controllers.gameServer.GameController;
import app.controllers.singletonController.UserController;
import app.models.connection.Message;
import app.models.connection.Processor;

import java.util.Scanner;

public class ServerMenu {

    private final Scanner scanner = new Scanner(System.in);
    protected final MySocketHandler mySocketHandler;
    private String currentMenu;
    protected Message message;

    protected ServerMenu(String currentMenu, MySocketHandler mySocketHandler){
        this.currentMenu = currentMenu;
        this.mySocketHandler = mySocketHandler;
    }

    protected void setCurrentMenu(String menuName) {
        System.out.println(menuName);
        currentMenu = menuName;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }



    //Handles commands that start with "menu"
    public void handleMenuCategoryCommand(Processor processor, Message message) {
        if (processor.getSection() == null) message.addLine(getInvalidCommand());
        else {
            if (processor.getSection().equals("enter")) menuEnter(processor,message);
            else if (processor.getSection().equals("exit")) menuExit();
            else if (processor.getSection().equals("show-current")) menuShowCurrent(message);
            else message.addLine(getInvalidCommand());
        }
    }


    //Enters the asked menu if its possible
    //menu enter <menu name>
    protected void menuEnter(Processor processor, Message message) {
        if (processor.getSubSection() == null) {
            message.addLine(getInvalidCommand());
            return;
        }
        switch (processor.getSubSection()) {
            case "register":
                message.addLine("Menu navigation is not possible");
                break;
            case "main":
                if (currentMenu.equals("register")) message.addLine("Please login first");
                else {
                    setCurrentMenu("main");
                    GameController.getInstance().setGame(null);
                }
                break;
            case "profile":
                if (currentMenu.equals("register")) message.addLine("Please login first");
                else if (currentMenu.equals("game")) message.addLine("Menu navigation is not possible");
                else setCurrentMenu("profile");
                break;
            case "game":
                if (currentMenu.equals("register")) message.addLine("Please login first");
                else message.addLine("Menu navigation is not possible");
                break;
            default:
                message.addLine(getInvalidCommand());
                break;
        }
    }


    //Exit from current menu to upper menu(register-->main-->game/profile)
    //menu exit
    protected void menuExit() {
        System.out.println("in menuExit");
        System.out.println(getCurrentMenu());
        if (getCurrentMenu().equals("register")) setCurrentMenu("end");
        else if (getCurrentMenu().equals("main")) {
            setCurrentMenu("register");
            UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()).setLastSeen(System.currentTimeMillis());
            UserController.getInstance().LogoutUser(mySocketHandler.getSocketToken());
        } else setCurrentMenu("main");
        //GameController.getInstance().setGame(null); todo remove gameController after exit Game Menu not here!
    }


    //Shows the menu we are in
    //menu show-current
    protected void menuShowCurrent(Message message) {
        message.addLine(currentMenu.substring(0, 1).toUpperCase() + currentMenu.substring(1) + " Menu");
    }


    protected String getInvalidCommand() {
        return "Invalid command!";
    }


    protected void sendMessage(Message message) {
        message.setCurrentMenu(currentMenu);
        mySocketHandler.sendMessage(message);
    }
}
