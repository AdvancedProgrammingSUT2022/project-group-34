package models;

public class User {

    private String username;
    private String password;
    private String nickname;
    private int score;

    public User(String username, String password, String nickname, int score) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPasswordCorrect(){
        return true;
    }

    public void changePassWord(String oldPassWord, String newPassword){
        this.password = newPassword;
    }
}
