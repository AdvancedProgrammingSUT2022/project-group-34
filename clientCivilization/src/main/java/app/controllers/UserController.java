package app.controllers;

import app.models.User;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.views.commandLineMenu.Menu;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserController {
    //Singleton Pattern
    private static UserController instance;
    private ArrayList<User> users = new ArrayList<>();
    private Message message;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }


    //Fields of the class


    //Setters and Getters for fields of the class
    public User getLoggedInUser() {
        Processor processor = new Processor("UserController","get","loggedInUser");
        processor.setGetOrSet(true);
        Menu.sendProcessor(processor);
        return new Gson().fromJson((String) InputController.getInstance().getMessage().getData("loggedInUser"), User.class);
    }

    public ArrayList<User> getUsers() {
        Processor processor = new Processor("UserController","get","users");
        processor.setGetOrSet(true);
        Menu.sendProcessor(processor);
        message = InputController.getInstance().getMessage();
        System.out.println(message.getAllData().containsKey("users"));
        Object objects  = message.getData("users");
        ArrayList<User> userArray = new ArrayList<>();
        for (var object : ((ArrayList)objects)) {
            userArray.add(new Gson().fromJson((String) object, User.class));
        }
        return userArray;
    }




    /*A "Strong password" has the following conditions:
    1.At least contains 6 characters
    2.Contains a lowercase letter
    3.Contains an uppercase letter
    4.Contains a digit*/
    //public boolean isPasswordStrong(String password)

}
