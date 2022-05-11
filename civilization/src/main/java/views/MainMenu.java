package views;

import controllers.GameController;
import controllers.UserController;
import models.Game;
import models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


    //Handles commands that start with "play"
    private static void playCategoryCommand(Processor processor) {
        if (processor.getSection() != null && processor.getSection().equals("game")) startNewGame(processor);
        else invalidCommand();
    }


    //Starts a game between logged-in user and users in the command
    //play game --player1 <username> --player2 <username>
    private static void startNewGame(Processor processor) {
        ArrayList<User> users = new ArrayList<>(List.of(UserController.getInstance().getLoggedInUser()));

        for (int i = 1; i < processor.getNumberOfFields() + 1; i++) {
            if (processor.get("player" + i) == null) {
                invalidCommand();
                return;
            } else if (UserController.getInstance().getUserByUsername(processor.get("player" + i)) == null) {
                System.out.println("Some of the usernames doesn't exist!");
                return;
            } else if (UserController.getInstance().getLoggedInUser().getUsername().equals(processor.get("player" + i))) {
                System.out.println("Some of the usernames are invalid!");
                return;
            } else
                users.add(UserController.getInstance().getUserByUsername(processor.get("player" + i)));
        }
        if (users.size() > 0) {
            GameController.getInstance().startNewGame(users);
            setCurrentMenu("game");
            System.out.println("Game started!");
        } else
            invalidCommand();
    }


    private static void loadGame(Processor processor) {
        // TODO: 4/21/2022
    }
}
