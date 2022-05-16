package views;

import controllers.UserController;
import models.User;

import java.util.Scanner;

public class ProfileMenu extends Menu {

    //Processes commands related with profile menu
    static void processOneCommand() {
        Processor processor;

        while (Menu.getCurrentMenu().equals("profile")) {
            processor = new Processor(getInput());

            if (!processor.isValid() || processor.getCategory() == null) invalidCommand();
            else if (processor.getCategory().equals("change")) handleChangeCategoryCommand(processor);
            else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
            else invalidCommand();
        }
    }


    //Handles commands that start with "profile change"
    private static void handleChangeCategoryCommand(Processor processor) {
        if (processor.get("nickname") != null) changeNickname(processor);
        else if (processor.get("current") != null) changePassword(processor);
        else invalidCommand();
    }


    //Changes user nickname if it has wanted conditions
    //profile change --nickname <nickname>
    private static void changeNickname(Processor processor) {
        String nickname = processor.get("nickname");

        if (nickname == null || processor.getNumberOfFields() != 1)
            invalidCommand();
        else if (UserController.getInstance().getLoggedInUser().getNickname().equals(nickname))
            System.out.println("Please enter a new nickname");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            System.out.format("A user with nickname %s already exists\n", nickname);
        else {
            UserController.getInstance().getLoggedInUser().setNickname(nickname);
            System.out.println("Nickname changed successfully!");
        }
    }


    //Changes user password if it has wanted conditions
    //profile change --password --current <current password> --new <new password>
    private static void changePassword(Processor processor) {
        String currentPassword = processor.get("current");
        String newPassword = processor.get("new");

        if (currentPassword == null ||
                newPassword == null ||
                processor.get("password") != null ||
                processor.getNumberOfFields() != 3)
            invalidCommand();
        else if (!UserController.getInstance().getLoggedInUser().isPasswordCorrect(currentPassword))
            System.out.println("Current password is invalid");
        else if (currentPassword.equals(newPassword))
            System.out.println("Please enter a new password");
        else if (!UserController.getInstance().isPasswordStrong(newPassword))
            System.out.println("Password is weak!");
        else {
            UserController.getInstance().getLoggedInUser().setPassword(newPassword);
            System.out.println("Password changed successfully!");
        }
    }
}
