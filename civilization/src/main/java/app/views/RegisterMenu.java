package app.views;

import app.models.User;
import app.controllers.UserController;

public class RegisterMenu extends Menu {

    //Processes commands related with register menu
    static void processOneCommand() {
        Processor processor;

        while (Menu.getCurrentMenu().equals("register")) {
            processor = new Processor(getInput());

            if (!processor.isValid() || processor.getCategory() == null) invalidCommand();
            else if (processor.getCategory().equals("user")) handleUserCategoryCommand(processor);
            else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
            else invalidCommand();
        }
    }


    //Handles commands that start with "user"
    private static void handleUserCategoryCommand(Processor processor) {
        if (processor.getSection() != null &&
                processor.getSection().equals("register"))
            register(processor);
        else if (processor.getSection() != null &&
                processor.getSection().equals("login"))
            login(processor);
        else invalidCommand();
    }


    //Creates a user if it has wanted conditions
    //register --username <username> --nickname <nickname> --password <password>
    private static void register(Processor processor) {
        String username = processor.get("username");
        String password = processor.get("password");
        String nickname = processor.get("nickname");

        if (username == null ||
                password == null ||
                nickname == null ||
                processor.getNumberOfFields() != 3)
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


    //Login the user if it exists or password is correct
    //user login --username <username> --password <password>
    private static void login(Processor processor) {
        String username = processor.get("username");
        String password = processor.get("password");
        User user;

        if (username == null ||
                password == null ||
                processor.getNumberOfFields() != 2)
            invalidCommand();
        else if ((user = UserController.getInstance().getUserByUsername(username)) == null)
            System.out.println("Username or password didn't match!");
        else if (!user.getPassword().equals(password))
            System.out.println("Username or password didn't match!");
        else {
            UserController.getInstance().setLoggedInUser(user);
            Menu.setCurrentMenu("main");
            System.out.println("User logged in successfully!");
        }
    }
}
