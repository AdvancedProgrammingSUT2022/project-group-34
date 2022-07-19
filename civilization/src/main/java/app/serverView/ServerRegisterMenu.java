package app.serverView;

import app.controllers.UserController;
import app.models.User;
import app.models.connection.Message;
import app.views.Processor;
import app.views.RegisterMenu;

public class ServerRegisterMenu extends ServerMenu{

    private static ServerRegisterMenu instance;
    private ServerRegisterMenu(){
        super("register");
    }

    public static ServerRegisterMenu getInstance() {
        if (instance == null)
            instance = new ServerRegisterMenu();
        return instance;
    }

    public void proccessOneProcessor(Processor processor) {
        message = new Message();
        if (!processor.isValid() || processor.getCategory() == null) message.addLine(getInvalidCommand());
        else if (processor.getCategory().equals("user")) handleUserCategoryCommand(processor,message);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor,message);
        else message.addLine(getInvalidCommand());
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        super.sendMessage();
        RegisterMenu.setAndPrintMessage(message);
    }

    //Handles commands that start with "user"

    private void handleUserCategoryCommand(Processor processor, Message message) {
        if (processor.getSection() != null &&
                processor.getSection().equals("register"))
            register(processor,message);
        else if (processor.getSection() != null &&
                processor.getSection().equals("login"))
            login(processor,message);
        else message.addLine(getInvalidCommand());
    }

    //Creates a user if it has wanted conditions
    //register --username <username> --nickname <nickname> --password <password>

    private void register(Processor processor, Message message) {
        String username = processor.get("username");
        String password = processor.get("password");
        String nickname = processor.get("nickname");

        if (username == null ||
                password == null ||
                nickname == null ||
                processor.getNumberOfFields() != 3)
            message.addLine(getInvalidCommand());
        else if (UserController.getInstance().getUserByUsername(username) != null)
            message.addLine("A user with username " + username + " already exists");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            message.addLine("A user with nickname " + nickname + " already exists");
        else if (!UserController.getInstance().isPasswordStrong(password))
            message.addLine("Password is weak!");
        else {
            UserController.getInstance().getUsers().add(new User(username, password, nickname));
            message.addLine("User Created successfully!");
        }

    }


    //Login the user if it exists or password is correct
    //user login --username <username> --password <password>
    private void login(Processor processor, Message message) {
        String username = processor.get("username");
        String password = processor.get("password");
        User user = null;

        if (username == null ||
                password == null ||
                processor.getNumberOfFields() != 2)
            message.addLine(getInvalidCommand());
        else if ((user = UserController.getInstance().getUserByUsername(username)) == null)
            message.addLine("Username or password didn't match!");
        else if (!user.isPasswordCorrect(password))
            message.addLine("Username or password didn't match!");
        else {
            UserController.getInstance().setLoggedInUser(user);
            setCurrentMenu("main");
            message.addLine("User logged in successfully!");
        }


    }

}