open module civilization {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    //opens app.controllers to javafx.fxml, com.google.gson;
    exports app.controllers;
    //opens app.models to javafx.fxml, com.google.gson;
    exports app.models;
    //opens app.views to javafx.fxml, com.google.gson;
    exports app.views;
    //opens app to javafx.fxml, com.google.gson;
    exports app;
    exports app.models.resource;
    exports app.models.map;
    exports app.models.connection;
    exports app.models.tile;
    exports app.models.unit;
    exports app.views.commandLineMenu;
    exports app.views.graphicalMenu;
    //opens app.models.save to com.google.gson, javafx.fxml;

}