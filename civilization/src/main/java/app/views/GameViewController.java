package app.views;

import app.App;
import app.controllers.gameServer.CivilizationController;
import app.controllers.gameServer.GameController;
import app.models.City;
import app.models.Civilization;
import app.models.Technology;
import app.models.map.CivilizationMap;
import app.models.resource.Resource;
import app.models.resource.StrategicResource;
import app.models.tile.Feature;
import app.models.tile.Terrain;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import app.models.unit.CombatUnit;
import app.models.unit.NonCombatUnit;
import app.models.unit.Unit;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

public class GameViewController {
    private final int VIEW_MAP_HEIGHT = 5;
    private final int VIEW_MAP_WIDTH = 11;
    private final int TILE_HEIGHT = 124;
    private final int TILE_WIDTH = 144;
    private final int UP_PADDING = 50;
    private final int LEFT_PADDING = 28;

    private int mapX = VIEW_MAP_HEIGHT / 2;
    private int mapY = VIEW_MAP_WIDTH / 2;

    private CombatUnit selectedCombatUnit = null;
    private NonCombatUnit selectedNonCombatUnit = null;
    private City selectedCity = null;

    private HashMap<String, Image> loadedImages = new HashMap<>();

    @FXML
    private Pane pane;

    @FXML
    private Group tileGroup;

    @FXML
    private Group currentTechnologyGroup;

    @FXML
    private Group currentUnitGroup;

    @FXML
    private Group statusBarGroup;

    @FXML
    private HBox statusBar;

    @FXML
    private void initialize() {
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/game_view.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));

        pane.setBackground(background);


    }

}