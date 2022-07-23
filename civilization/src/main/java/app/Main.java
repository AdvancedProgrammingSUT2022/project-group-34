package app;

import app.controllers.NetworkController;
import app.controllers.UserController;

public class Main {
    public static void main(String[] args) {
        UserController.getInstance().loadUsers();
        NetworkController.getInstance().run();
        UserController.getInstance().saveUsers();
    }
}
