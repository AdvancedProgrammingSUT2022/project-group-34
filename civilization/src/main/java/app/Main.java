package app;

import app.controllers.singletonController.NetworkController;
import app.controllers.singletonController.UserController;

public class Main {
    public static void main(String[] args) {
        UserController.getInstance().loadUsers();
        NetworkController.getInstance().run();
        UserController.getInstance().saveUsers();
    }
}
