package app.views;

import app.controllers.MainServer;
import app.controllers.singletonController.UserController;
import app.models.PreGame;
import app.models.User;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.models.connection.StringSocketToken;

import java.util.ArrayList;
import java.util.List;

public class ServerMainMenu extends ServerMenu{

    ServerMainMenu(MySocketHandler mySocketHandler){
        super("main", mySocketHandler);
    }

    public void processOneProcessor(Processor processor) {
        System.out.println("processOneProcessor");
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
        System.out.println(processor);
        if (processor.getSection() != null && processor.getSection().equals("play")) startNewGame(processor,message);
        else if (processor.getSection().equals("create")) createGame(processor);
        else if (processor.getSection().equals("join")) addUserToGame(processor);
        else if (processor.getSection().equals("leave")) removeUserFromGame(processor);
        else message.addLine(getInvalidCommand());

    }


    //Starts a game between logged-in user and users in the command
    //play game --player1 <username> --player2 <username>
    private void startNewGameOld(Processor processor,Message message) {
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
            //MainServer.startNewGame(mySocketHandler.getPreGame());
            setCurrentMenu("game");
            message.addLine("Game started!");
        } else
            message.addLine(getInvalidCommand());
    }

    private void startNewGame(Processor processor,Message message) {

        User user = UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken());
        int index = Integer.parseInt(processor.get("index"));
        PreGame preGame = MainServer.getAllPreGames().get(index);
        if (preGame == null)
            message.addLine(getInvalidCommand());
        else if (!MainServer.isAdmin(preGame, user)) {
            message.addLine("You are not admin");
        } else {
            mySocketHandler.setGameToken(MainServer.startNewGame(preGame));
            setCurrentMenu("game");
            message.setSuccessful(true);
            message.addLine("Game started!");
        }
    }

    private void createGame(Processor processor){
        int n = Integer.parseInt(processor.get("capacity"));
        if (n > 1) {
            User user = UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken());
            MainServer.createPreGame(user, 13, n);
            message.setSuccessful(true);
        } else {
            message.addLine(getInvalidCommand());
        }
    }

    private void addUserToGame(Processor processor) {
        int GameId = Integer.parseInt(processor.get("index"));
        StringSocketToken token = mySocketHandler.getSocketToken();
        User user = UserController.getInstance().getLoggedInUsers(token);
        int Size = MainServer.getNumberOfPreGame();

        if (user == null)
            message.addLine("No user has logged in with this token");
        else if(Size <= GameId)
            message.addLine("There are no games with this information");
        else {
            if (MainServer.getAllPreGames().get(GameId).getUsers().contains(user))
                message.addLine("You have already joined this game");
            else {
                MainServer.addUserToGame(GameId, user);
                message.setSuccessful(true);
            }
        }
    }

    private void removeUserFromGame(Processor processor) {
        int GameId = Integer.parseInt(processor.get("index"));
        StringSocketToken token = mySocketHandler.getSocketToken();
        User user = UserController.getInstance().getLoggedInUsers(token);
        int Size = MainServer.getNumberOfPreGame();

        if (user == null)
            message.addLine("No user has logged in with this token");
        else if(Size <= GameId)
            message.addLine("There are no games with this information");
        else {
            if (!MainServer.getAllPreGames().get(GameId).getUsers().contains(user))
                message.addLine("You have not joined this game before");
            else {
                MainServer.removeUserFromGame(GameId, user);
                message.setSuccessful(true);
            }
        }
    }



    private void loadGame(Processor processor, Message message) {
        // TODO: Not phase1
    }
}
