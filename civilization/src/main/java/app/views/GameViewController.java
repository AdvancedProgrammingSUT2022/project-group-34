package app.views;

import app.controllers.CivilizationController;
import app.controllers.GameController;
import app.models.Civilization;
import app.models.map.CivilizationMap;
import app.models.tile.Feature;
import app.models.tile.Terrain;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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

    private HashMap<String, Image> loadedImages = new HashMap<>();

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

                int upperBound;
                int leftBound = TILE_WIDTH * 3 / 4 * (j + VIEW_MAP_WIDTH / 2) + LEFT_PADDING;
                if (j % 2 == 0) {
                    upperBound = TILE_HEIGHT * (i + VIEW_MAP_HEIGHT / 2) + UP_PADDING;
                } else {
                    if (mapY % 2 == 1) {
                        upperBound = TILE_HEIGHT * (i + VIEW_MAP_HEIGHT / 2) - TILE_HEIGHT / 2 + UP_PADDING;
                    } else {
                        upperBound = TILE_HEIGHT * (i + VIEW_MAP_HEIGHT / 2) + TILE_HEIGHT / 2 + UP_PADDING;
                    }
                }

                putTile(visibleTile, x, y, upperBound, leftBound);
            }
        }
    }

    private void putTile(VisibleTile visibleTile, int x, int y, int upperBound, int leftBound) {
        putTileBorders(upperBound, leftBound, x, y);

        if (visibleTile.isInFog()) {
            putTerrainFeature(null, null, upperBound, leftBound);
        }
    }

    private void putTileBorders(int upperBound, int leftBound, int x, int y) {
        //TODO...
    }

    private void putTerrainFeature(Terrain terrain, Feature feature, int upperBound, int leftBound) {
        //TODO...
        String fileName = terrain + "_" + feature + ".png";
        String filePath = "/app/assets/tiles/" + fileName;
        ImageView imageView = new ImageView(getImage(filePath));
        imageView.setY(upperBound);
        imageView.setX(leftBound);
        imageView.getStyleClass().add("tile");
        tilePane.getChildren().add(imageView);
    }

    private Image getImage(String filePath) {
        Image image = loadedImages.get(filePath);
        if (image == null) {
            image = new Image(getClass().getResource(filePath).toExternalForm());
            loadedImages.put(filePath, image);
        }
        return image;
    }
}