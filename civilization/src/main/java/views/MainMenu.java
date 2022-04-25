package views;

import controllers.UserController;

import java.util.Scanner;

public class MainMenu extends Menu {

    //Processes commands related with main menu
    static void processOneCommand() {
        Processor processor;

        while (Menu.getCurrentMenu().equals("main")) {
            processor = new Processor(getInput());

            if (!processor.isValid() || processor.getCategory() == null) invalidCommand();
            else if (processor.getCategory().equals("user")) logout(processor);
            else if (processor.getCategory().equals("play")) playCategoryCommand(processor);
            else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
            else invalidCommand();
        }
    }


    //Logouts the user
    //user logout
    private static void logout(Processor processor) {
        if (processor.getSection() != null && processor.getSection().equals("logout")) {
            UserController.getInstance().setLoggedInUser(null);
            setCurrentMenu("register");
            System.out.println("User logged out successfully!");
        } else invalidCommand();
    }


    private static void playCategoryCommand(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void startNewGame(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void loadGame(Processor processor) {
        // TODO: 4/21/2022
    }
}
