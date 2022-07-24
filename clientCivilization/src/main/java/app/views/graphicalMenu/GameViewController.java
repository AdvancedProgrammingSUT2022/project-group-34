package app.views.graphicalMenu;

import app.App;
import app.controllers.CivilizationController;
import app.controllers.GLoad;
import app.controllers.GameController;
import app.controllers.InputController;
import app.models.City;
import app.models.Civilization;
import app.models.Technology;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.models.map.CivilizationMap;
import app.models.miniClass.MiniGameController;
import app.models.resource.Resource;
import app.models.resource.StrategicResource;
import app.models.tile.Feature;
import app.models.tile.Terrain;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import app.models.unit.CombatUnit;
import app.models.unit.NonCombatUnit;
import app.models.unit.Unit;
import app.views.commandLineMenu.Menu;
import com.google.gson.Gson;
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

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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
    private int personalMapMapHeight;
    private int personalMapMapWidth;

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

        personalMapMapHeight = GameController.getInstance().getCivilization().getPersonalMap().getMapHeight();
        personalMapMapWidth = GameController.getInstance().getCivilization().getPersonalMap().getMapWidth();

        App.getStage().getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                navigate(event);
            }
        });

        loadTiles();
        loadStatusBar();
        loadCurrentTechnology();
        loadCurrentUnit();
    }

    @FXML
    private void navigate(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                mapX--;
                break;
            case DOWN:
                mapX++;
                break;
            case LEFT:
                mapY--;
                break;
            case RIGHT:
                mapY++;
                break;
        }
        mapX = Math.max(mapX, VIEW_MAP_HEIGHT / 2);
        mapX = Math.min(mapX, personalMapMapHeight - VIEW_MAP_HEIGHT / 2 - 1);
        mapY = Math.max(mapY, VIEW_MAP_WIDTH / 2);
        mapY = Math.min(mapY, personalMapMapWidth - VIEW_MAP_WIDTH / 2 - 1);

        loadTiles();
    }

    private GameController loadDataForShowMap() {
        HashMap<String,Object> data = getData();
        writeTemp(data.keySet().toString());
        mapX = getAndCast2Integer(((Double) data.get("mapX")), mapX);
        mapY = getAndCast2Integer(((Double) data.get("mapY")), mapY);
        GLoad.getInstance().setTemp(new Gson().fromJson((String) data.get("GameController"), (Type) String[].class));
        GameController gameController = (GameController)GLoad.getInstance().loadObject(new MiniGameController(),0);
        return gameController;
    }

    private static void writeTemp(String str) {
        try {
            FileWriter fileWriter = new FileWriter("temp.json");
            //System.out.println("get pos :");
            //String[] strings = Menu.getInput().split(" ");
            //String str = new Gson().toJson(gameController.getCivilization().getPersonalMap().getMap().get(Integer.parseInt(strings[0])).get(Integer.parseInt(strings[1])));
            for (int i = 0; i < str.length(); i += 10000) {
                if (i + 10000 < str.length())
                    fileWriter.write(str.substring(i,i+10000));
                else fileWriter.write(str.substring(i));
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getAndCast2Integer(Double num, int def) {
        if (num == null) {
            System.out.println("null");
            return def;
        }
        return num.intValue();
    }

    private static HashMap<String, Object> getData() {
        Processor processor = new Processor("map", "get", "data");
        Menu.sendProcessor(processor, false);
        Message message = InputController.getInstance().getMessage();
        return message.getAllData();
    }

    private void loadTiles() {

        tileGroup.getChildren().clear();

        GameController.setInstance(loadDataForShowMap());
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
        StringBuilder tooltip = new StringBuilder("");
        ImageView imageView;
        Civilization civilization = GameController.getInstance().getCivilization();
        Tile tile = CivilizationController.getInstance().getTileByPosition(new int[]{visibleTile.getX(), visibleTile.getY()});
        if (visibleTile.isInFog()) {
            imageView = putTerrainFeature(null, null, upperBound, leftBound);
            tooltip = new StringBuilder("FOG OF WAR!\n");
        }
        else {
            putRivers(upperBound, leftBound, x, y);
            imageView = putTerrainFeature(visibleTile.getTerrain(), visibleTile.getFeature(), upperBound, leftBound);
            tooltip.append("This tile is owned by " + visibleTile.getCivilization() + ".\n");
            tooltip.append("Terrain: " + visibleTile.getTerrain() + "\n");
            tooltip.append("Terrain Feature: " + visibleTile.getFeature() + "\n");

            if (civilization.isTransparent(tile)) {
                putResource(tile.getResource(), upperBound, leftBound, x, y);
                tooltip.append("The resource in this tile is: " + getResourceName(tile.getResource()) + "\n");
            }
            else {
                imageView.getStyleClass().add("revealed");
                tooltip.insert(0, "This tile is revealed but not transparent!\n");
            }
        }
        tooltip.append("Tile coordinates: " + tile.getX() + ", " + tile.getY() + "\n");
        Tooltip.install(imageView, new Tooltip(tooltip.toString()));
    }

    private void putRivers(int upperBound, int leftBound, int x, int y) {
        Tile tile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y});
        Tile otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y});
        if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
            putRiver(upperBound, leftBound, 0);
        otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y});
        if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
            putRiver(upperBound, leftBound, 3);
        if (y % 2 == 0) {
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 5);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 4);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 1);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 2);
        } else {
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 5);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 4);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 1);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(upperBound, leftBound, 2);
        }
    }

    private void putRiver(int upperBound, int leftBound, int position) {
        //TODO...
        String filePath = "/app/assets/rivers/" + position + ".png";
        putElement(tileGroup, filePath, "river", upperBound, leftBound);
    }

    private void putResource(Resource resource, int upperBound, int leftBound, int x, int y) {
        String name = getResourceName(resource);
        String filePath = "/app/assets/resources/" + name + ".png";
        putElement(tileGroup, filePath, "resource", upperBound, leftBound);
    }

    private String getResourceName(Resource resource) {
        if (resource == null) return "null";
        Civilization civilization = GameController.getInstance().getCivilization();
        if (resource instanceof StrategicResource && !civilization.hasResearched(((StrategicResource) resource).getRequiredTechnology())) {
            return "unknown";
        } else return resource.getName();
    }

    private ImageView putTerrainFeature(Terrain terrain, Feature feature, int upperBound, int leftBound) {
        //TODO...
        String fileName = terrain + "_" + feature + ".png";
        String filePath = "/app/assets/tiles/" + fileName;
        System.out.println(filePath);
        return putElement(tileGroup, filePath, "tile", upperBound, leftBound);
    }

    private ImageView putElement(Group group, String filePath, String styleClass, int x, int y) {
        ImageView imageView = putElement(group.getChildren(), filePath, styleClass);
        imageView.setY(x);
        imageView.setX(y);
        return imageView;
    }

    private ImageView putElement(HBox box, String filePath, String styleClass) {
        return putElement(box.getChildren(), filePath, styleClass);
    }

    private ImageView putElement(ObservableList<Node> children, String filePath, String styleClass) {
        ImageView imageView = new ImageView(getImage(filePath));
        imageView.getStyleClass().add(styleClass);
        children.add(imageView);
        return imageView;
    }

    private Image getImage(String filePath) {
        Image image = loadedImages.get(filePath);
        if (image == null) {
            image = new Image(getClass().getResource(filePath).toExternalForm());
            loadedImages.put(filePath, image);
        }
        return image;
    }

    private void loadStatusBar() {
        statusBar.getChildren().clear();
        putStatusBarBackground();
        showGold();
        showHappiness();
        showBeaker();
        //TODO...
    }

    private void putStatusBarBackground() {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(25);
        rectangle.setWidth(1280);
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setFill(Color.DARKSLATEGRAY);
        statusBarGroup.getChildren().add(0, rectangle);
    }

    private void showGold() {
        ImageView imageView = putElement(statusBar, "/app/assets/icons/Gold.png", "icon");
        Tooltip.install(imageView, new Tooltip("Gold"));
        Label gold = new Label(String.valueOf(GameController.getInstance().getCivilization().getGold()));
        statusBar.getChildren().add(gold);
    }

    private void showHappiness() {
        ImageView imageView = putElement(statusBar, "/app/assets/icons/Happiness.png", "icon");
        Tooltip.install(imageView, new Tooltip("Happiness"));
        Label happiness = new Label(String.valueOf(GameController.getInstance().getCivilization().getHappiness()));
        statusBar.getChildren().add(happiness);
    }

    private void showBeaker() {
        ImageView imageView = putElement(statusBar, "/app/assets/icons/Beaker.png", "icon");
        Tooltip.install(imageView, new Tooltip("Science"));
        Label beaker = new Label(String.valueOf(GameController.getInstance().getCivilization().getNumberOfBeakers()));
        statusBar.getChildren().add(beaker);
    }

    private void loadCurrentTechnology() {
        currentTechnologyGroup.getChildren().clear();
        Technology technology = GameController.getInstance().getCivilization().getStudyingTechnology();
        putCurrentTechnologyBackground();
        putCurrentTechnologyImage(technology);
        putCurrentTechnologyLabel(technology);
        //TODO...
    }

    private void putCurrentTechnologyBackground() {
        Rectangle background = new Rectangle();
        background.setHeight(64);
        background.setWidth(64);
        background.setY(25);
        background.setFill(Color.WHITESMOKE);
        currentTechnologyGroup.getChildren().add(background);
    }

    private void putCurrentTechnologyImage(Technology technology) {
        String filePath = "/app/assets/technologies/" + technology + ".png";
        ImageView imageView = putElement(currentTechnologyGroup, filePath, "technology", 25, 0);
        Tooltip.install(imageView, new Tooltip(String.valueOf(technology)));
    }

    private void putCurrentTechnologyLabel(Technology technology) {
        Label currentTechnology = new Label(String.valueOf(technology));
        if (technology != null) currentTechnology.setText(currentTechnology.getText() + " (" + technology.getRemainingTerm() + ")");
        currentTechnology.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, null, null)));
        currentTechnology.setLayoutX(64);
        currentTechnology.setLayoutY(25);
        currentTechnology.setMaxHeight(15);
        currentTechnologyGroup.getChildren().add(currentTechnology);
    }

    private void loadCurrentUnit() {
        currentUnitGroup.getChildren().clear();
        Unit unit = selectedCombatUnit;
        if (unit == null) unit = selectedNonCombatUnit;
        putCurrentUnitBackground();
        putCurrentUnitImage(unit);
        putCurrentUnitLabel(unit);
        //TODO...
    }

    private void putCurrentUnitBackground() {
        Rectangle background = new Rectangle();
        background.setHeight(64);
        background.setWidth(64);
        background.setY(700 - 64);
        background.setFill(Color.WHITESMOKE);
        currentUnitGroup.getChildren().add(background);
    }

    private void putCurrentUnitImage(Unit unit) {
        String filePath = "/app/assets/units/" + unit + ".png";
        ImageView imageView = putElement(currentUnitGroup, filePath, "unit", 700 - 64, 0);
        Tooltip.install(imageView, new Tooltip(String.valueOf(unit)));
    }

    private void putCurrentUnitLabel(Unit unit) {
        Label currentUnit = new Label(String.valueOf(unit));
        if (unit != null) currentUnit.setText(currentUnit.getText());
        currentUnit.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, null, null)));
        currentUnit.setLayoutX(64);
        currentUnit.setLayoutY(700 - 15);
        currentUnit.setMaxHeight(15);
        currentUnitGroup.getChildren().add(currentUnit);
    }
}