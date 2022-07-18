package app.views;

import app.controllers.CivilizationController;
import app.controllers.GameController;
import app.models.Civilization;
import app.models.map.CivilizationMap;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GameViewController {
    private final int VIEW_MAP_HEIGHT = 5;
    private final int VIEW_MAP_WIDTH = 9;
    private final int TILE_HEIGHT = 120;
    private final int TILE_WIDTH = 140;

    private int mapX = VIEW_MAP_HEIGHT / 2;
    private int mapY = VIEW_MAP_WIDTH / 2;

    @FXML
    private BorderPane pane;

    @FXML
    private Pane tilePane;

    @FXML
    private void initialize() {
        Background background = new Background(new BackgroundImage(
                new Image(getClass().getResource("/app/background/game_view.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)));
        pane.setBackground(background);

        loadTiles();
    }

    private void loadTiles() {
        tilePane.getChildren().clear();
        Civilization civilization = GameController.getInstance().getCivilization();
        CivilizationMap personalMap = civilization.getPersonalMap();

        for (int i = -(VIEW_MAP_HEIGHT / 2); i <= VIEW_MAP_HEIGHT / 2; i++) {
            for (int j = -(VIEW_MAP_WIDTH / 2); j <= VIEW_MAP_WIDTH / 2; j++) {
                if (j % 2 == 1 || j % 2 == -1) {
                    if (mapY % 2 == 1 && i == -(VIEW_MAP_HEIGHT / 2)) continue;
                    else if (mapY % 2 == 0 && i == VIEW_MAP_HEIGHT / 2) continue;
                }
                int x = mapX + i;
                int y = mapY + j;
                VisibleTile visibleTile = personalMap.getTileByXY(x, y);
            }
        }
}