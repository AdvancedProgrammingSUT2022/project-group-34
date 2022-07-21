package app;

import app.controllers.UserController;
import app.models.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class App extends Application {
    private static Stage mainStage;

    public static void main(String[] args) {
        UserController.getInstance().loadUsers();
        launch();
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        stage.setTitle("Civilization");
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                User user = UserController.getInstance().getLoggedInUser();
                if (user != null) user.setLastSeen(System.currentTimeMillis());
                UserController.getInstance().saveUsers();
            }
        });
        setMenu("login_menu");
    }

    public static Stage getStage() {
        return mainStage;
    }

    public static void setMenu(String menuName) {
        mainStage.close();
        Scene scene = new Scene(new Pane());
        mainStage.setScene(scene);
        Parent root = loadPage(menuName);
        scene.setRoot(root);
        mainStage.show();
        System.out.println(menuName);
        System.out.println(mainStage.getScene().getRoot());
    }

    private static Parent loadPage(String pageName) {
        try {
            URL address = new URL(App.class.getResource("/app/fxml/" + pageName + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void exit() {
        mainStage.close();
        UserController.getInstance().saveUsers();
        System.exit(0);
    }
}
