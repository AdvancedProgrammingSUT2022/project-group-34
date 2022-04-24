package views;

import controllers.UserController;
import models.User;

import java.util.Scanner;

public class ProfileMenu extends Menu {

    static void processOneCommand(Scanner scanner) {
        Processor processor = new Processor(getInput());

        while (Menu.getCurrentMenu().equals("profile")) {
            if (processor.getSection().equals("change"))
                handleChangeCategoryCommand(processor);
            else if (processor.getCategory().equals("menu"))
                handleMenuCategoryCommand(processor);
            else
                invalidCommand();

            processor = new Processor(getInput());
        }
    }

    private static void handleChangeCategoryCommand(Processor processor) {
        if (processor.get("nickname") != null)
            changeNickname(processor);
        else if (processor.get("current") != null)
            changePassword(processor);
        else
            invalidCommand();
    }

    private static void changeNickname(Processor processor) {
        String nickname = processor.get("nickname");
        User user;

        if (processor.getNumberOfFields() != 1)
            invalidCommand();
        else if ((user = UserController.getInstance().getUserByNickname(nickname)) != null)
            System.out.format("A user with nickname %s already exists\n", nickname);
        else {
            user.setNickname(nickname);
            System.out.println("Nickname changed successfully!");
        }
    }

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
        else {
            UserController.getInstance().getLoggedInUser().setPassword(newPassword);
            System.out.println("Password changed successfully!");
        }
    }
}
