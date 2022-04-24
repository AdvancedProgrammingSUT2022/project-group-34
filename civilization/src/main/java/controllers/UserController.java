package controllers;

import models.User;

import java.util.ArrayList;

public class UserController {
    //Singleton Pattern
    private static UserController instance;

    public UserController() {
    }

    public static UserController getInstance(){
        if (instance==null)
            instance=new UserController();
        return instance;
    }


    private String strongPasswordRegex;
    private User loggedInUser;
    private ArrayList<User> users;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    private User getUserByUsername(String username){
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void login(String username, String password){
        // TODO: 4/24/2022
    }

    public void logout(){
        // TODO: 4/24/2022
    }

    public boolean isPasswordStrong(String password){
        // TODO: 4/24/2022
        return true;
    }
}
