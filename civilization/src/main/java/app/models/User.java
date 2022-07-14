package app.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class User implements Comparable<User> {
    private final String username;
    private String password;
    private String nickname;
    private int score;
    private Date scoreTime;
    private Date lastSeen;

    //Constructor of the class
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.scoreTime = new Date(0);
        this.lastSeen = new Date(System.currentTimeMillis());
    }

    public User(String username, String password, String nickname, File file) throws Exception {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.scoreTime = new Date(0);
        this.lastSeen = new Date(System.currentTimeMillis());
        setAvatar(file);
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

    public void setLastSeen(long lastSeen) {
        this.lastSeen = new Date(lastSeen);
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public Date getLastSeen() {
        return lastSeen;
    }


    //Return avatar as ImageView
    public ImageView getImageView() {
        File file = new File("src/main/resources/app/avatars/" + username + ".png");
        try {
            if (!file.exists()) setAvatar(null);
        } catch (Exception e) {
        }
        return new ImageView(new Image(file.toURI().toString()));
    }

    //Checks if the given password is equal to user's password
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void setAvatar(File file) throws Exception {
        try {
            if (file == null) file = new File("src/main/resources/app/placeholder.png");
            new ImageView(new Image(file.toURI().toString()));
            Files.copy(file.toPath(), new File("src/main/resources/app/avatars/" + username + ".png").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            setAvatar(null);
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public int compareTo(User user) {
        if (this.score != user.score) return this.score - user.score;
        if (!this.scoreTime.equals(user.scoreTime)) return scoreTime.compareTo(user.scoreTime);
        return this.nickname.compareTo(user.nickname);
    }


}
