package app;

import app.controllers.ConnectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    private static String currentMenu;
    private static Stage mainStage;

    public static void main(String[] args) {
        if (ConnectionController.connection()){
            launch();
        }
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        stage.setTitle("Civilization");
        stage.show();

        setMenu("login_menu");
    }

    public static Stage getStage() {
        return mainStage;
    }

    public static void setMenu(String menuName) {
        currentMenu = menuName;
        mainStage.setScene(new Scene(loadPage(menuName)));
    }

    private static Parent loadPage(String pageName) {
        try {
            URL address = new URL(App.class.getResource("/app/fxml/" + pageName + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("nno nono");
            return null;
        }
    }

    public static void exit() {
        mainStage.close();
        System.exit(0);
    }
}
