package app.serverView;

import app.controllers.GameController;
import app.controllers.UserController;
import app.models.User;
import app.models.connection.Message;
import app.views.MainMenu;
import app.views.Processor;

import java.util.ArrayList;
import java.util.List;

public class ServerMainMenu extends ServerMenu{

    private static ServerMainMenu instance;

    private ServerMainMenu(){

    }

    public static ServerMainMenu getInstance(){
        if (instance == null) instance = new ServerMainMenu();
        return instance;
    }
    public void processOneProcessor(Processor processor) {
        if (!processor.isValid() || processor.getCategory() == null) sendMessage(new Message(getInvalidCommand()));
        else if (processor.getCategory().equals("user")) logout(processor);
        else if (processor.getCategory().equals("play")) playCategoryCommand(processor);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
        else sendMessage(new Message(getInvalidCommand()));
    }

    private void sendMessage(Message message) {
        MainMenu.setMessage(message);
    }



    //Logouts the user
    //user logout
    private void logout(Processor processor) {

        Message message = new Message();

        if (processor.getSection() != null && processor.getSection().equals("logout")) {
            UserController.getInstance().setLoggedInUser(null);
            setCurrentMenu("register");
            message.setMessage("User logged out successfully!");
        } else message.setMessage(getInvalidCommand());

        sendMessage(message);
    }


    //Handles commands that start with "play"
    private void playCategoryCommand(Processor processor) {
        Message message = new Message();
        if (processor.getSection() != null && processor.getSection().equals("game")) startNewGame(processor,message);
        else message.setMessage(getInvalidCommand());

        sendMessage(message);
    }


    //Starts a game between logged-in user and users in the command
    //play game --player1 <username> --player2 <username>
    private void startNewGame(Processor processor,Message message) {
        ArrayList<User> users = new ArrayList<>(List.of(UserController.getInstance().getLoggedInUser()));
        if (processor.getNumberOfFields()==0){
            message.setMessage("Please select some other players");
            return;
        }

        for (int i = 1; i < processor.getNumberOfFields() + 1; i++) {
            if (processor.get("player" + i) == null) {
                message.setMessage(getInvalidCommand());
                return;
            } else if (UserController.getInstance().getUserByUsername(processor.get("player" + i)) == null) {
                message.setMessage("Some of the usernames doesn't exist!");
                return;
            } else if (UserController.getInstance().getLoggedInUser().getUsername().equals(processor.get("player" + i))) {
                message.setMessage("Some of the usernames are invalid!");
                return;
            } else
                users.add(UserController.getInstance().getUserByUsername(processor.get("player" + i)));
        }
        if (users.size() > 0) {
            GameController.getInstance().startNewGame(users);
            setCurrentMenu("game");
            message.setMessage("Game started!");
        } else
            message.setMessage(getInvalidCommand());
    }


    private void loadGame(Processor processor) {
        // TODO: Not phase1
    }
}
