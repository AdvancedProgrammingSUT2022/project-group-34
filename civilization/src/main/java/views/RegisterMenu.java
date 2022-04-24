package views;

import controllers.UserController;
import models.User;

public class RegisterMenu extends Menu {

    static void processOneCommand() {
        Processor processor = new Processor(getInput());

        while (Menu.getType().equals("register")) {
            if (processor.getCategory().equals("user"))
                handleUserCategoryCommand(processor);
            else if (processor.getCategory().equals("menu"))
                handleMenuCategoryCommand(processor);
            else
                invalidCommand();

            processor = new Processor(getInput());
        }
    }

    private static void handleUserCategoryCommand(Processor processor) {
        if (processor.getSection().equals("register"))
            register(processor);
        else if (processor.getSection().equals("login"))
            login(processor);
        else
            invalidCommand();
    }

    private static void register(Processor processor) {
        String username = processor.get("username");
        String password = processor.get("password");
        String nickname = processor.get("nickname");

        if (username == null || password == null || nickname == null)
            invalidCommand();
        else if (UserController.getInstance().getUserByUsername(username) != null)
            System.out.format("A user with username %s already exists\n", username);
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            System.out.format("A user with nickname %s already exists\n", nickname);
        else if (!UserController.getInstance().isPasswordStrong(password))
            System.out.println("Password is weak!");
        else {
            UserController.getInstance().getUsers().add(new User(username, password, nickname));
            System.out.println("User Created successfully!");
        }
    }

    private static void login(Processor processor) {
        String username = processor.get("username");
        String password = processor.get("password");
        User user;
        if (username == null || password == null)
            invalidCommand();
        else if ((user = UserController.getInstance().getUserByUsername(username)) == null)
            System.out.println("Username or password didn't match!");
        else if (!user.getPassword().equals(password))
            System.out.println("Username or password didn't match!");
        else {
            UserController.getInstance().setLoggedInUser(user);
            Menu.setType("main");
            System.out.println("User logged in successfully!");
        }
    }
}
