package views;

import controllers.UserController;

import java.util.Scanner;

public class MainMenu extends Menu{

    static void processOneCommand(Scanner scanner){
        Processor processor = new Processor(getInput());

        while (Menu.getCurrentMenu().equals("main")){
            if (processor.getCategory().equals("user"))
                handleUserCategoryCommand(processor);
            else if (processor.getCategory().equals("play"))
                playCategoryCommand(processor);
            else if (processor.getCategory().equals("menu"))
                handleMenuCategoryCommand(processor);
            else
                invalidCommand();

            processor = new Processor(getInput());
        }
    }

    private static void handleUserCategoryCommand(Processor processor){
        if (processor.getSection().equals("logout")) logout();
        else invalidCommand();
    }

    private static void logout(){
        UserController.getInstance().setLoggedInUser(null);
        setCurrentMenu("register");
        System.out.println("User logged out successfully!");
    }

    private static void playCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void startNewGame(Processor processor){
        // TODO: 4/21/2022
    }

    private static void loadGame(Processor processor){
        // TODO: 4/21/2022
    }
}
