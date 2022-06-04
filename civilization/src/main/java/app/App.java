package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        setMenu("main_menu");
        stage.setTitle("Civilization");
        stage.show();
    }

    public static void setMenu(String menuName) {
        mainStage.setScene(new Scene(loadPage(menuName)));
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
        System.exit(0);
    }
}
