package app.views;

import app.controllers.singletonController.UserController;
import app.models.connection.Message;
import app.models.connection.Processor;

public class ServerProfileMenu extends ServerMenu{

    ServerProfileMenu(MySocketHandler mySocketHandler){
        super("profile", mySocketHandler);
    }

    public void processOneProcessor(Processor processor) {
        message = new Message();
        System.out.println(processor);
        if (!processor.isValid() || processor.getCategory() == null) message.addLine(getInvalidCommand());
        else if (processor.getCategory().equals("change")) handleChangeCategoryCommand(processor , message);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor, message);
        else message.addLine(getInvalidCommand());
        sendMessage(message);
    }


    //Handles commands that start with "profile change"
    private void handleChangeCategoryCommand(Processor processor, Message message) {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println(processor);
        if (processor.get("nickname") != null) changeNickname(processor,message);
        else if (processor.contains("password")) changePassword(processor,message);
        else if (processor.contains("username")) message.addLine("you can't change username");
        else message.addLine(getInvalidCommand());
        System.out.println("+++++++++++++++++++++++++++++++++++");

    }


    //Changes user nickname if it has wanted conditions
    //profile change --nickname <nickname>
    private void changeNickname(Processor processor, Message message) {
        String nickname = processor.get("nickname");
        if (nickname == null || processor.getNumberOfFields() != 1)
            message.addLine(getInvalidCommand());
        else if (UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()).getNickname().equals(nickname))
            message.addLine("Please enter a new nickname");
        else if (UserController.getInstance().getUserByNickname(nickname) != null)
            message.addLine("A user with nickname " + nickname + " already exists");
        else {
            UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()).setNickname(nickname);
            message.addLine("Nickname changed successfully!");
            message.setSuccessful(true);
            UserController.getInstance().saveUsers();
        }
    }


    //Changes user password if it has wanted conditions
    //profile change --password --current <current password> --new <new password>
    private void changePassword(Processor processor, Message message) {
        System.out.println("changePassword start");
        String currentPassword = processor.get("current");
        String newPassword = processor.get("new");

        if (currentPassword == null ||
                newPassword == null ||
                !Processor.isNull(processor.get("password")) ||
                processor.getNumberOfFields() != 3)
            message.addLine(getInvalidCommand());
        else if (!UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()).isPasswordCorrect(currentPassword))
            message.addLine("Current password is invalid");
        else if (currentPassword.equals(newPassword))
            message.addLine("Please enter a new password");
        else if (!UserController.getInstance().isPasswordStrong(newPassword))
            message.addLine("Password is weak!");
        else {
            UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()).setPassword(newPassword);
            message.addLine("Password changed successfully!");
            message.setSuccessful(true);
            UserController.getInstance().saveUsers();
        }
    }


}
