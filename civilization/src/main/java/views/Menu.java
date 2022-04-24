package views;

import javax.swing.*;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static String type;

    protected static void run(){

        String command = getInput();
        Processor processor = new Processor(command);

        while (!command.equals("menu exit")){
            if (processor.getCategory().equals("menu")){
                handleMenuCategoryCommand(processor);
            }

            command = getInput();
        }
    }

    protected static String getInput(){
        return scanner.nextLine().trim();
    }

    protected static void setType(String menuName) {
        type = menuName;
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
        // TODO: 4/21/2022
    }

    protected static void invalidCommand(){
        // TODO: 4/21/2022  
    }
}
