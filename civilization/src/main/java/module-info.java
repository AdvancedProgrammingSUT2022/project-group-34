module civilization {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens app.controllers to javafx.fxml, com.google.gson;
    exports app.controllers;
    opens app.models to javafx.fxml, com.google.gson;
    exports app.models;
    opens app.views to javafx.fxml;
    exports app.views;
    opens app to javafx.fxml;
    exports app;
}