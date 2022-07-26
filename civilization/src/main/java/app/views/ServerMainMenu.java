package app.views;

import app.controllers.MainServer;
import app.controllers.singletonController.UserController;
import app.models.User;
import app.models.connection.Message;
import app.models.connection.Processor;

import java.util.ArrayList;
import java.util.List;

public class ServerMainMenu extends ServerMenu{

    ServerMainMenu(MySocketHandler mySocketHandler){
        super("main", mySocketHandler);
    }

    public void processOneProcessor(Processor processor) {
        message = new Message();
        if (!processor.isValid() || processor.getCategory() == null) message.addLine(getInvalidCommand());
        else if (processor.getCategory().equals("user")) logout(processor, message);
        else if (processor.getCategory().equals("play")) playCategoryCommand(processor, message);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor, message);
        else message.addLine(getInvalidCommand());
        sendMessage(message);
    }



    //Logouts the user
    //user logout
    private void logout(Processor processor, Message message) {

        if (processor.getSection() != null && processor.getSection().equals("logout")) {
            UserController.getInstance().LogoutUser(mySocketHandler.getSocketToken());
            setCurrentMenu("register");
            message.addLine("User logged out successfully!");
        } else message.addLine(getInvalidCommand());

    }


    //Handles commands that start with "play"
    private void playCategoryCommand(Processor processor, Message message) {
        if (processor.getSection() != null && processor.getSection().equals("game")) startNewGame(processor,message);
        else message.addLine(getInvalidCommand());

    }


    //Starts a game between logged-in user and users in the command
    //play game --player1 <username> --player2 <username>
    private void startNewGame(Processor processor,Message message) {
        ArrayList<User> users = new ArrayList<>(List.of(UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken())));
        if (processor.getNumberOfFields()==0){
            message.addLine("Please select some other players");
            return;
        }

        for (int i = 1; i < processor.getNumberOfFields() + 1; i++) {
            if (processor.get("player" + i) == null) {
                message.addLine(getInvalidCommand());
                return;
            } else if (UserController.getInstance().getUserByUsername(processor.get("player" + i)) == null) {
                message.addLine("Some of the usernames doesn't exist!");
                return;
            } else if (UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()).getUsername().equals(processor.get("player" + i))) {
                message.addLine("Some of the usernames are invalid!");
                return;
            } else
                users.add(UserController.getInstance().getUserByUsername(processor.get("player" + i)));
        }
        if (users.size() > 0) {
            int mapScale = 1; //todo in client
            try {
                mapScale = Integer.parseInt(processor.get("mapScale"));
            } catch (Exception ignored){}
            MainServer.startNewGame(users, mapScale);
            setCurrentMenu("game");
            message.addLine("Game started!");
        } else
            message.addLine(getInvalidCommand());
    }


    private void loadGame(Processor processor, Message message) {
        // TODO: Not phase1
    }
}
