package app.serverView;

import app.controllers.UserController;
import app.models.User;
import app.models.connection.Message;
import app.views.Processor;
import app.views.RegisterMenu;

public class ServerRegisterMenu extends ServerMenu{

    private static ServerRegisterMenu instance;
    private ServerRegisterMenu(){}

    public static ServerRegisterMenu getInstance() {
        if (instance == null)
            instance = new ServerRegisterMenu();
        return instance;
    }

    public void proccessOneProcessor(Processor processor) {
        if (!processor.isValid() || processor.getCategory() == null) sendMessage(new Message(getInvalidCommand()));
        else if (processor.getCategory().equals("user")) handleUserCategoryCommand(processor);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
        else sendMessage(new Message(getInvalidCommand()));
    }

    private void sendMessage(Message message) {
        super.sendMessage();
        RegisterMenu.setMessage(message);
    }

    //Handles commands that start with "user"

    private void handleUserCategoryCommand(Processor processor) {
        if (processor.getSection() != null &&
                processor.getSection().equals("register"))
            register(processor);
        else if (processor.getSection() != null &&
                processor.getSection().equals("login"))
            login(processor);
        else sendMessage(new Message(getInvalidCommand()));
    }

    //Creates a user if it has wanted conditions
    //register --username <username> --nickname <nickname> --password <password>

    private void register(Processor processor) {
        String username = processor.get("username");
        String password = processor.get("password");
        String nickname = processor.get("nickname");

        Message message = new Message();

        if (username == null ||
                password == null ||
                nickname == null ||
                processor.getNumberOfFields() != 3)
            message.setMessage(getInvalidCommand());
        else if (UserController.getInstance().getUserByUsername(username) != null)
            message.setMessage("A user with username " + username + " already exists\n");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            message.setMessage("A user with nickname " + nickname + " already exists\n");
        else if (!UserController.getInstance().isPasswordStrong(password))
            message.setMessage("Password is weak!");
        else {
            UserController.getInstance().getUsers().add(new User(username, password, nickname));
            message.setMessage("User Created successfully!");
        }

        sendMessage(message);
    }


    //Login the user if it exists or password is correct
    //user login --username <username> --password <password>
    private void login(Processor processor) {
        String username = processor.get("username");
        String password = processor.get("password");
        User user;

        Message message = new Message();

        if (username == null ||
                password == null ||
                processor.getNumberOfFields() != 2)
            message.setMessage(getInvalidCommand());
        else if ((user = UserController.getInstance().getUserByUsername(username)) == null)
            message.setMessage("Username or password didn't match!");
        else if (!user.isPasswordCorrect(password))
            message.setMessage("Username or password didn't match!");
        else {
            UserController.getInstance().setLoggedInUser(user);
            setCurrentMenu("main");
            message.setMessage("User logged in successfully!");
        }

        sendMessage(message);
    }

}
