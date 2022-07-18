package app.serverView;

import app.controllers.UserController;
import app.models.connection.Message;
import app.views.Processor;
import app.views.ProfileMenu;

public class ServerProfileMenu extends ServerMenu{

    private static ServerProfileMenu instance;

    private ServerProfileMenu(){

    }

    public static ServerProfileMenu getInstance(){
        if (instance == null) instance = new ServerProfileMenu();
        return instance;
    }

    public void processOneProcessor(Processor processor) {
        if (!processor.isValid() || processor.getCategory() == null) sendMessage(new Message(getInvalidCommand()));
        else if (processor.getCategory().equals("change")) handleChangeCategoryCommand(processor);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
        else sendMessage(new Message(getInvalidCommand()));
    }

    private void sendMessage(Message message) {
        ProfileMenu.setMessage(message);
    }


    //Handles commands that start with "profile change"
    private void handleChangeCategoryCommand(Processor processor) {
        Message message = new Message();
        if (processor.get("nickname") != null) changeNickname(processor,message);
        else if (processor.contains("password")) changePassword(processor,message);
        else if (processor.contains("username")) message.setMessage("you can't change username");
        else message.setMessage(getInvalidCommand());
    }


    //Changes user nickname if it has wanted conditions
    //profile change --nickname <nickname>
    private void changeNickname(Processor processor, Message message) {
        String nickname = processor.get("nickname");

        if (nickname == null || processor.getNumberOfFields() != 1)
            message.setMessage(getInvalidCommand());
        else if (UserController.getInstance().getLoggedInUser().getNickname().equals(nickname))
            message.setMessage("Please enter a new nickname");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            message.setMessage("A user with nickname " + nickname + " already exists");
        else {
            UserController.getInstance().getLoggedInUser().setNickname(nickname);
            message.setMessage("Nickname changed successfully!");
        }
    }


    //Changes user password if it has wanted conditions
    //profile change --password --current <current password> --new <new password>
    private void changePassword(Processor processor, Message message) {
        String currentPassword = processor.get("current");
        String newPassword = processor.get("new");

        if (currentPassword == null ||
                newPassword == null ||
                processor.get("password") != null ||
                processor.getNumberOfFields() != 3)
            message.setMessage(getInvalidCommand());
        else if (!UserController.getInstance().getLoggedInUser().isPasswordCorrect(currentPassword))
            message.setMessage("Current password is invalid");
        else if (currentPassword.equals(newPassword))
            message.setMessage("Please enter a new password");
        else if (!UserController.getInstance().isPasswordStrong(newPassword))
            message.setMessage("Password is weak!");
        else {
            UserController.getInstance().getLoggedInUser().setPassword(newPassword);
            message.setMessage("Password changed successfully!");
        }
    }

}
