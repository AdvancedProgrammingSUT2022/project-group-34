package views;

import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentMenu = "register";

    protected static void run(){

        while (true){
            RegisterMenu.processOneCommand();
        }
    }

    protected static String getInput(){
        return scanner.nextLine().trim();
    }

    protected static void setCurrentMenu(String menuName) {
        currentMenu = menuName;
    }

    public static String getCurrentMenu() {
        return currentMenu;
    }

    protected static void handleMenuCategoryCommand(Processor processor){
        if (processor.getSection().equals("enter"))
            menuEnter(processor);
        else if (processor.getSection().equals("exit"))
            menuExit();
        else if (processor.getSection().equals("show-current"))
            menuShowCurrent();
        else
            invalidCommand();
    }

    protected static void menuEnter(Processor processor){
        if (processor.getSubSection().equals("register")) {
            // TODO: 4/24/2022
        } else if (processor.getSubSection().equals("main")) {
            // TODO: 4/24/2022
        }else if (processor.getSubSection().equals("profile")){
            // TODO: 4/24/2022
        }else if (processor.getSubSection().equals("game")){

        }else
            invalidCommand();

    }

    protected static void menuExit(){
        // TODO: 4/21/2022
    }

    protected static void menuShowCurrent(){
        System.out.println(currentMenu);
    }

    protected static void invalidCommand(){
        System.out.println("Invalid command!");
    }
}