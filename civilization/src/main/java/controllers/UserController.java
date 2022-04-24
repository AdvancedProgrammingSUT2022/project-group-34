package controllers;

import models.User;

import java.util.ArrayList;

public class UserController {
    //Singleton Pattern
    private static UserController instance;

    public UserController() {
    }

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }


    //Fields of the class
    private User loggedInUser = null;
    private final ArrayList<User> users = new ArrayList<>();


    //Setters and Getters for fields of the class
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    //Returns the user with username
    //Returns null if such user doesn't exist
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }


    //Returns the user with username
    //Returns null if such user doesn't exist
    public User getUserByNickname(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname))
                return user;
        }
        return null;
    }


    /*A "Strong password" has the following conditions:
    1.At least contains 6 characters
    2.Contains a lowercase letter
    3.Contains an uppercase letter
    4.Contains a digit*/
    public boolean isPasswordStrong(String password) {
        if (password.length() < 6)
            return false;
        else return password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*");
    }

    public void logout() {
        // TODO: 4/24/2022
    }
}
