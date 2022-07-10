package app.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class User {
    private final String username;
    private String password;
    private String nickname;
    private int score;

    //Constructor of the class
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
    }

    public User(String username, String password, String nickname, Image image) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
    }


    //Setters and Getters for fields of the class
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    private String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }


    //Return avatar as ImageView
    public ImageView getImageView() {
        File file = new File("src/main/resources/app/avatars/" + username + ".png");
        if (!file.exists()) setAvatar(null);
        return new ImageView(new Image(file.toURI().toString()));
    }

    //Checks if the given password is equal to user's password
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void setAvatar(File file) {
        try {
            if (file == null) file = new File("src/main/resources/app/placeholder.png");
            new ImageView(new Image(file.toURI().toString()));
            Files.copy(file.toPath(), new File("src/main/resources/app/avatars/" + username + ".png").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            setAvatar(null);
            e.printStackTrace();
            return;
        }
    }
}
