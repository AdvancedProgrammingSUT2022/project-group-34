package app.views;

import app.controllers.MainServer;
import app.controllers.singletonController.UserController;
import app.models.connection.Message;
import app.models.connection.Processor;
import com.google.gson.Gson;

public class ServerGetterAndSetterMenu extends ServerMenu{
    protected ServerGetterAndSetterMenu(MySocketHandler mySocketHandler) {
        super("ServerGetterAndSetterMenu", mySocketHandler);
    }

    public void processOneProcessor(Processor processor) {
        message = new Message();
        if (!processor.isValid() || processor.getCategory() == null) message.addLine(getInvalidCommand());
        else if (processor.getCategory().equals("UserController")) handleUserControllerCommand(processor);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor, message);
        else message.addLine(getInvalidCommand());
        System.out.println("asd");;
        sendMessage(message);
    }

    private void handleUserControllerCommand(Processor processor) {
        if (processor.getSection().equals("get")){
            switch (processor.getSubSection()){
                case "loggedInUser":
                    message.addData("loggedInUser", new Gson().toJson(UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken())));
                    break;
                case "users":
                    System.out.println("get users");
                    String[] strings = new String[UserController.getInstance().getUsers().size()];
                    for (int i = 0; i < UserController.getInstance().getUsers().size(); i++)
                        strings[i] = new Gson().toJson(UserController.getInstance().getUsers().get(i));

                    message.addData("users", strings);
                    break;
                case "allPreGames":
                    System.out.println("allPreGames");
                    String[] strings1 = new String[MainServer.getAllPreGames().size()];
                    for (int i = 0; i < MainServer.getAllPreGames().size(); i++)
                        strings1[i] = new Gson().toJson(MainServer.getAllPreGames().get(i));

                    message.addData("allPreGames", strings1);
                    break;
            }
        } else if (processor.getSection().equals("set")){
        }
    }
}
