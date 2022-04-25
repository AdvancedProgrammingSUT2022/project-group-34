package models;

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

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }


    //Checks if the given password is equal to user's password
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }
}
