package controllers;

import models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserController {
    //Singleton Pattern
    private static UserController instance;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }


    //Fields of the class
    private User loggedInUser = null;
    private ArrayList<User> users = new ArrayList<>();


    //Setters and Getters for fields of the class
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setUsers(ArrayList<User> users) {
        if (users != null) this.users = users;
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


    //Loading registered users from a json file
    //File path: ./src/main/resources/UserDatabase.json
    public void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src", "main", "resources", "UserDatabase.json")));
            UserController.getInstance().setUsers(new Gson().fromJson(json,
                    new TypeToken<ArrayList<User>>() {}.getType()));
        } catch (IOException e) {
            System.out.println("Loading users failed!");
        }
    }


    //Saving registered users to a json file
    //File path: ./src/main/resources/UserDatabase.json
    public void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(Paths.get("src", "main", "resources", "UserDatabase.json")));
            fileWriter.write(new Gson().toJson(UserController.getInstance().getUsers()));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Saving users failed!");
        }
    }
}
