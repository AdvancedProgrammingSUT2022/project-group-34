package app.views;

import java.util.HashMap;

public class Adapter {

    public static String login(String username, String password) {
        return String.format("user login --username %s --password %s", username, password);
    }

    public static String register(String username, String nickname, String password) {
        return String.format("user register --username %s --nickname %s --password %s", username, nickname, password);
    }

    public static String changePassword(String currentPassword, String newPassword) {
        return String.format("change --password --current %s --new %s", currentPassword, newPassword);
    }

    public static String changeNickname(String nickname) {
        return String.format("change --nickname %s", nickname);
    }

    public static String startNewGame() {
        return "play game";
    }
}
