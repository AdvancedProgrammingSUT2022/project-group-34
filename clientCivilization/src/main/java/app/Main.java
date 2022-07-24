//besmellah


package app;

import app.controllers.ConnectionController;
import app.controllers.UserController;
import app.views.commandLineMenu.Menu;

public class Main {
    public static void main(String[] args) {

        if (ConnectionController.connection()){
            UserController.getInstance().loadUsers();
            Menu.run();
            UserController.getInstance().saveUsers();
        }
    }
}
