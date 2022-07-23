package app.serverView;

import app.controllers.UserController;
import app.models.connection.Message;
import app.views.Processor;

public class ServerProfileMenu extends ServerMenu{

    ServerProfileMenu(MySocketHandler mySocketHandler){
        super("profile", mySocketHandler);
    }

    public void processOneProcessor(Processor processor) {
        message = new Message();
        if (!processor.isValid() || processor.getCategory() == null) message.addLine(getInvalidCommand());
        else if (processor.getCategory().equals("change")) handleChangeCategoryCommand(processor , message);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor, message);
        else message.addLine(getInvalidCommand());
        sendMessage(message);
    }


    //Handles commands that start with "profile change"
    private void handleChangeCategoryCommand(Processor processor, Message message) {
        if (processor.get("nickname") != null) changeNickname(processor,message);
        else if (processor.contains("password")) changePassword(processor,message);
        else if (processor.contains("username")) message.addLine("you can't change username");
        else message.addLine(getInvalidCommand());
    }


    //Changes user nickname if it has wanted conditions
    //profile change --nickname <nickname>
    private void changeNickname(Processor processor, Message message) {
        String nickname = processor.get("nickname");

        if (nickname == null || processor.getNumberOfFields() != 1)
            message.addLine(getInvalidCommand());
        else if (UserController.getInstance().getLoggedInUser().getNickname().equals(nickname))
            message.addLine("Please enter a new nickname");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            message.addLine("A user with nickname " + nickname + " already exists");
        else {
            UserController.getInstance().getLoggedInUser().setNickname(nickname);
            message.addLine("Nickname changed successfully!");
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
            message.addLine(getInvalidCommand());
        else if (!UserController.getInstance().getLoggedInUser().isPasswordCorrect(currentPassword))
            message.addLine("Current password is invalid");
        else if (currentPassword.equals(newPassword))
            message.addLine("Please enter a new password");
        else if (!UserController.getInstance().isPasswordStrong(newPassword))
            message.addLine("Password is weak!");
        else {
            UserController.getInstance().getLoggedInUser().setPassword(newPassword);
            message.addLine("Password changed successfully!");
        }
    }

}
