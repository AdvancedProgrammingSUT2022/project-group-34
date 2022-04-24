import controllers.UserController;
import views.Menu;

public class Main {
    public static void main(String[] args) {
        UserController.getInstance().loadUsers();
        Menu.run();
        UserController.getInstance().saveUsers();
    }
}
