package app.views;

import app.App;
import app.controllers.CivilizationController;
import app.controllers.GameController;
import app.models.City;
import app.models.Civilization;
import app.models.Technology;
import app.models.TechnologyEnum;
import app.models.map.CivilizationMap;
import app.models.resource.BonusResource;
import app.models.resource.LuxuryResource;
import app.models.resource.Resource;
import app.models.resource.StrategicResource;
import app.models.tile.Feature;
import app.models.tile.Terrain;
import app.models.tile.Tile;
import app.models.tile.VisibleTile;
import app.models.unit.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

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
    private Group currentCityGroup;

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

        App.getStage().getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                navigate(event);
            }
        });

        System.out.println(GameController.getInstance().getCivilization().getUnits().get(0).getPosition().getX());

        load();
    }

    private void load() {
        loadTiles();
        loadStatusBar();
        loadCurrentTechnology();
        loadCurrentUnit();
        loadCurrentCity();
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
        mapX = Math.min(mapX, GameController.getInstance().getCivilization().getPersonalMap().getMapHeight() - VIEW_MAP_HEIGHT / 2 - 1);
        mapY = Math.max(mapY, VIEW_MAP_WIDTH / 2);
        mapY = Math.min(mapY, GameController.getInstance().getCivilization().getPersonalMap().getMapWidth() - VIEW_MAP_WIDTH / 2 - 1);

        loadTiles();
    }

    private void loadTiles() {
        tileGroup.getChildren().clear();
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

    private void clickOnTile(MouseEvent event, int x, int y) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            moveTo(x, y);
        }
    }

    private void moveTo(int x, int y) {
        Unit unit = getSelectedUnit();
        if (unit == null) return;
        if (unit.getDestination() != null && !unit.getPosition().equals(unit.getDestination()))
            alert("Invalid movement!", "Unit is in a multiple-turn movement.", Alert.AlertType.ERROR);
        else {

            int[] position = new int[]{x, y};
            String response = CivilizationController.getInstance().moveUnit(unit, position);

            if (response.equals("invalid destination"))
                alert(response.toUpperCase(), "Destination is not valid", Alert.AlertType.ERROR);
            else if (response.equals("fog of war"))
                alert(response.toUpperCase(), "Destination is in fog of war", Alert.AlertType.ERROR);
            else if (response.equals("already at the same tile"))
                alert(response.toUpperCase(), "We already are at the tile you want to move to", Alert.AlertType.ERROR);
            else if (response.equals("destination occupied"))
                alert(response.toUpperCase(), "There is already a unit in the tile you want to move to", Alert.AlertType.ERROR);
            else if (response.equals("no valid path"))
                alert(response.toUpperCase(), "There is no path to the tile you want to move to", Alert.AlertType.ERROR);
            else if (response.equals("success")) {
                unit.makeUnitAwake();
                alert("success", "Unit moved to destination successfully", Alert.AlertType.INFORMATION);
                selectedCombatUnit = null;
                selectedNonCombatUnit = null;
                load();
            }
            else alert("WHAT?", response, Alert.AlertType.ERROR);
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
                Unit unit = tile.getCombatUnit();
                if (unit == null) unit = tile.getNonCombatUnit();
                if (unit != null) putUnit(unit, upperBound, leftBound, x, y);
                if (tile.getCity() != null) {
                    putCity(tile.getCity(), upperBound, leftBound, x, y);
                    tooltip.append("City Name: " + tile.getCity().getName() + "\n");
                }
                if (tile.hasRoad()) tooltip.append("Has road.\n");
                if (tile.hasRail()) tooltip.append("Has railroad.\n");
                if (tile.getImprovement() != null) tooltip.append("Improvement: " + tile.getImprovement().getName() + "\n");
            }
            else {
                imageView.getStyleClass().add("revealed");
                tooltip.insert(0, "This tile is revealed but not transparent!\n");
            }
        }
        tooltip.append("Tile coordinates: " + tile.getX() + ", " + tile.getY() + "\n");
        Tooltip.install(imageView, new Tooltip(tooltip.toString()));
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clickOnTile(mouseEvent, x, y);
            }
        });
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

    private void putUnit(Unit unit, int upperBound, int leftBound, int x, int y) {
        String filePath = "/app/assets/units/" + unit.getName() + ".png";
        ImageView imageView = putElement(tileGroup, filePath, "unit", upperBound + 10, leftBound + 70 - 32);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectUnit(unit);
            }
        });
        if (unit.isSleep()) imageView.setOpacity(0.5);
        StringBuilder str = new StringBuilder();
        str.append(unit.getName() + "\n");
        str.append("Motion Point: " + unit.getMotionPoint() +"\n");
        str.append("Owner Civilization: " + unit.getCivilization().getCivilizationName() + "\n");
        Tooltip.install(imageView, new Tooltip(str.toString()));
    }

    private void putCity(City city, int upperBound, int leftBound, int x, int y) {
        String filePath = "/app/assets/cities/city.png";
        ImageView imageView = putElement(tileGroup, filePath, "city", upperBound + 60 - 16, leftBound + 10);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectCity(city);
            }
        });
        Tooltip tip = new Tooltip(city.getName() + "\nOwner: " + city.getCivilization().getCivilizationName() + "\nGarrison: " + city.isGarrison());
        Tooltip.install(imageView, tip);
    }

    private void selectUnit(Unit unit) {
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        if (unit instanceof CombatUnit) selectedCombatUnit = (CombatUnit)unit;
        else selectedNonCombatUnit = (NonCombatUnit)unit;
        loadCurrentUnit();
    }

    private void selectCity(City city) {
        selectedCity = city;
        loadCurrentCity();
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
//        System.out.println(filePath);
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
        showReloadButton();
        showEndTurnButton();
        showCivilizationInformation();
        //TODO...
    }

    private void showReloadButton() {
        Button button = new Button("reload page");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                load();
            }
        });
        statusBar.getChildren().add(button);
    }

    private void showEndTurnButton() {
        Button button = new Button("end turn");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.getInstance().startTurn();
                load();
            }
        });
        statusBar.getChildren().add(button);
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

    private void showCivilizationInformation() {
        Label name = new Label("Civilization Name: " + GameController.getInstance().getCivilization().getCivilizationName());
        Label turn = new Label("Turn: " + GameController.getInstance().getCivilization().getTurn());
        statusBar.getChildren().add(turn);
        statusBar.getChildren().add(name);
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

    private Unit getSelectedUnit() {
        Unit unit = selectedCombatUnit;
        if (unit == null) unit = selectedNonCombatUnit;
        return unit;
    }

    private City getSelectedCity() {
        return selectedCity;
    }

    private void loadCurrentUnit() {
        currentUnitGroup.getChildren().clear();
        putCurrentUnitBackground().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showUnitActions();
            }
        });
        Unit unit = getSelectedUnit();
        putCurrentUnitImage(unit).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                showUnitActions();
            }
        });
        putCurrentUnitLabel(unit);
        //TODO...
    }

    private void loadCurrentCity() {
        currentCityGroup.getChildren().clear();
        putCurrentCityBackground();//.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                showUnitActions();
//            }
//        });
        City city = getSelectedCity();
        putCurrentCityImage(city);//.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                showUnitActions();
//            }
//        });
        putCurrentCityLabel(city);
        //TODO...
    }

    private Rectangle putCurrentUnitBackground() {
        Rectangle background = new Rectangle();
        background.setHeight(64);
        background.setWidth(64);
        background.setY(700 - 64);
        background.setFill(Color.WHITESMOKE);
        currentUnitGroup.getChildren().add(background);
        return background;
    }

    private Rectangle putCurrentCityBackground() {
        Rectangle background = new Rectangle();
        background.setHeight(64);
        background.setWidth(64);
        background.setX(1280 - 64);
        background.setY(700 - 64);
        background.setFill(Color.WHITESMOKE);
        currentCityGroup.getChildren().add(background);
        return background;
    }

    private ImageView putCurrentUnitImage(Unit unit) {
        String filePath = "/app/assets/units/" + unit + ".png";
        ImageView imageView = putElement(currentUnitGroup, filePath, "unit", 700 - 64, 0);
        Tooltip.install(imageView, new Tooltip(String.valueOf(unit)));
        return imageView;
    }

    private ImageView putCurrentCityImage(City city) {
        String filePath = "/app/assets/cities/bigcity.png";
        if (city == null) filePath = "/app/assets/cities/null.png";
        ImageView imageView = putElement(currentCityGroup, filePath, "city", 700 - 64, 1280 - 64);
        Tooltip.install(imageView, new Tooltip(String.valueOf(city)));
        return imageView;
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

    private void putCurrentCityLabel(City city) {
        Label currentCity = new Label(String.valueOf(city));
        if (city != null) currentCity.setText(currentCity.getText());
        currentCity.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, null, null)));
        currentCity.setMaxHeight(15);
        currentCity.setLayoutX(1280 - 64 - currentCity.getWidth());
        currentCity.setLayoutY(700 - 15);
        currentCityGroup.getChildren().add(currentCity);
    }

    private void sleepCommand() {
        Unit unit = getSelectedUnit();

        unit.makeUnitAwake();
        unit.setSleep(true);
        unit.setDestination(unit.getPosition());
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        alert("SUCCESS", "Unit is asleep.", Alert.AlertType.INFORMATION);
    }

    private void alertCommand() {
        if (selectedCombatUnit == null) {
            alert("NONCOMBAT UNIT", "Selected unit is not a military unit", Alert.AlertType.ERROR);
        } else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setAlert(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
            selectedCombatUnit = null;
            alert("SUCCESS", "Unit is alert.", Alert.AlertType.INFORMATION);
        }
    }

    private void fortifyCommand() {
        if (selectedCombatUnit == null)
            alert("NONCOMBAT UNIT", "Selected unit is not a military unit", Alert.AlertType.ERROR);
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setFortify(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
            selectedCombatUnit = null;
            alert("SUCCESS", "Unit is fortified", Alert.AlertType.INFORMATION);
        }
    }

    private void healCommand() {
        if (selectedCombatUnit == null)
            alert("NONCOMBAT UNIT", "Selected unit is not a military unit", Alert.AlertType.ERROR);
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setFortifyUntilHealed(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
            selectedCombatUnit = null;
            alert("SUCCESS", "Unit is healed", Alert.AlertType.INFORMATION);
        }
    }

    private void garrisonCommand() {
        switch (CivilizationController.getInstance().garrisonCity(selectedCombatUnit)) {
            case "not military":
                alert("NONCOMBAT UNIT", "Selected unit is not a military unit", Alert.AlertType.ERROR);
                break;
            case "no city":
                alert("NO CITY", "There is no city in this tile", Alert.AlertType.ERROR);
                break;
            case "in  movement":
                alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
                break;
            case "ok":
                selectedCombatUnit = null;
                alert("SUCCESS", "City is garrisoned", Alert.AlertType.INFORMATION);
                break;
        }
    }

    private void setupCommand() {
        if (!(selectedCombatUnit instanceof Archer) || !((Archer) selectedCombatUnit).isSiegeTool)
            alert("NOT SIEGE TOOL", "Selected unit is not a siege tool unit", Alert.AlertType.ERROR);
        else if (selectedCombatUnit.getDestination() != null && !selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
        else {
            selectedCombatUnit.makeUnitAwake();
            ((Archer) selectedCombatUnit).setSetup(true);
            selectedCombatUnit = null;
            alert("SUCCESS", "Unit is setup", Alert.AlertType.INFORMATION);
        }
    }

//    private void attackCommand(Processor processor) {
//        String x = processor.get("x");
//        String y = processor.get("y");
//        if (selectedCombatUnit == null)
//            System.out.println("Selected unit is not a military unit");
//        else if (!CivilizationController.getInstance().isPositionValid(new int[]{Integer.parseInt(x), Integer.parseInt(y)}))
//            System.out.println("Invalid coordinates!");
//        else if (selectedCombatUnit.getDestination() != null && !selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
//            System.out.println("Unit is in a multiple-turn movement");
//        else {
//            int[] position = new int[]{Integer.parseInt(x), Integer.parseInt(y)};
//            if (processor.getSubSection().equals("city")) {
//                selectedCombatUnit.makeUnitAwake();
//                attackCity(position);
//            } else {
//                // TODO: Not phase1
//                System.out.println("Not this phase!");
//            }
//            selectedCombatUnit = null;
//        }
//    }

    private void cancelCommand() {
        Unit unit = getSelectedUnit();

        if (unit.getDestination() != null && unit.getDestination().equals(unit.getPosition()))
            alert("NOT MOVING", "Unit is not in a multiple-turn movement", Alert.AlertType.ERROR);
        else {
            unit.setDestination(unit.getPosition());
            alert("SUCCESS", "Unit's multiple-turn movement canceled", Alert.AlertType.INFORMATION);
        }
    }

    private void foundCommand() {
        Dialog dialog = new TextInputDialog("Tehran");
        dialog.setTitle("NAME");
        dialog.setHeaderText("Enter a name for the city");

        Optional<String> result = dialog.showAndWait();
        String name = "Tehran";

        if (result.isPresent()) name = result.get();

        foundCityCommand(name);
    }

    private void foundCityCommand(String name) {
        if (!(selectedNonCombatUnit instanceof Settler))
            alert("NOT A SETTLER", "Selected unit is not a settler", Alert.AlertType.ERROR);
        else if (selectedNonCombatUnit.getDestination() != null && !selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
            alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
        else {
            switch (CivilizationController.getInstance().foundCity((Settler) selectedNonCombatUnit, name)) {
                case "too close":
                    alert("TOO CLOSE", "You cannot found a city close to another civilization", Alert.AlertType.ERROR);
                    break;
                case "duplicate name":
                    alert("DUPLICATE NAME", "This name is used before", Alert.AlertType.ERROR);
                    break;
                case "ok":
                    alert("SUCCESS", "City founded!", Alert.AlertType.INFORMATION);
                    selectedNonCombatUnit = null;
                    break;
            }
        }
        load();
    }

    private void wakeUnit() {
        if (selectedNonCombatUnit != null) selectedNonCombatUnit.makeUnitAwake();
        else selectedCombatUnit.makeUnitAwake();

        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        alert("SUCCESS", "Unit is awake", Alert.AlertType.INFORMATION);
    }

    private void deleteUnitCommand() {
        Unit unit = getSelectedUnit();

        CivilizationController.getInstance().deleteUnit(unit);

        selectedNonCombatUnit = null;
        selectedCombatUnit = null;
        alert("SUCCESS", "Unit deleted", Alert.AlertType.INFORMATION);
    }

    private void pillageCommand() {
        if (selectedCombatUnit == null)
            alert("NONCOMBAT UNIT", "Selected unit is not a military unit", Alert.AlertType.ERROR);
        else if (selectedCombatUnit.getDestination() != null && !selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
        else if (selectedCombatUnit.getPosition().getImprovement() == null)
            alert("NO IMPROVEMENT", "There is no improvement in this tile", Alert.AlertType.ERROR);
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.getPosition().setLooted(true);
            selectedCombatUnit = null;
            alert("SUCCESS", "Tile is pillaged", Alert.AlertType.INFORMATION);
        }
    }

    private void buildCommand() {
        if (!(selectedNonCombatUnit instanceof Worker))
            alert("NOT A WORKER", "Selected unit is not a worker", Alert.AlertType.ERROR);
        else if (selectedNonCombatUnit.getDestination() != null && !selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
            alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
        else {
            Tile tile = selectedNonCombatUnit.getPosition();
            ArrayList<String> improvements = CivilizationController.getInstance().getPossibleImprovements(tile);
            String choice = getBuildChoice(improvements);
            if (!choice.equals("exit")) {
                CivilizationController.getInstance().build((Worker) selectedNonCombatUnit, improvements.get(Integer.parseInt(choice) - 1));
                alert("SUCCESS", "Building improvement started", Alert.AlertType.ERROR);
                selectedNonCombatUnit = null;
            }
        }
    }

    private String getBuildChoice(ArrayList<String> improvements) {
        StringBuilder message = new StringBuilder();
        message.append("Please type improvement index you want to build\n");
        message.append("Type \"exit\" if you don't want to choose any improvement\n");

        for (int i = 0; i < improvements.size(); i++)
            message.append(i + 1 + "." + improvements.get(i) + "\n");

        Dialog dialog = new TextInputDialog("1");
        dialog.setTitle("CHOOSE AN IMPROVEMENT");
        dialog.setHeaderText(message.toString());

        String command = "exit";
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) command = result.get();

        if (command.equals("exit")) return "exit";
        else if (command.matches("\\d+")) {
            int number = Integer.parseInt(command);
            if (number < 1 || number > improvements.size()) {
                alert("ERROR", "Invalid number!", Alert.AlertType.ERROR);
                command = "exit";
            }
        } else command = "exit";
        return command;
    }

//    private void removeCommand(Processor processor) {
//        switch (CivilizationController.getInstance().removeFeature(selectedNonCombatUnit, processor.getSubSection())) {
//            case "not worker":
//                alert("NOT A WORKER", "Selected unit is not a worker", Alert.AlertType.ERROR);
//                break;
//            case "in movement":
//                alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
//                break;
//            case "irremovable feature":
//                alert("IRREMOVABLE FEATURE", "This tiles feature cannot get removed", Alert.AlertType.ERROR);
//                break;
//            case "ok":
//                alert("SUCCESS", "Removing feature started", Alert.AlertType.INFORMATION);
//                selectedNonCombatUnit = null;
//                break;
//        }
//    }

    private void repairCommand() {
        switch (CivilizationController.getInstance().repair(selectedNonCombatUnit)) {
            case "not worker":
                alert("NOT A WORKER", "Selected unit is not a worker", Alert.AlertType.ERROR);
                break;
            case "in movement":
                alert("MOVING UNIT", "Unit is in a multiple-turn movement", Alert.AlertType.ERROR);
                break;
            case "no improvement":
                alert("NO IMPROVEMENT", "There is no improvement in this tile to get repaired", Alert.AlertType.ERROR);
                break;
            case "ok":
                alert("SUCCESS", "Repairing tile started", Alert.AlertType.INFORMATION);
                selectedNonCombatUnit = null;
                break;
        }
    }

    private void alert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void showUnitActions() {
        Stage stage = new Stage();
        BorderPane pane = new BorderPane();
        pane.setPrefSize(200, 600);
        pane.setMinSize(200, 600);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        VBox box = new VBox();
        pane.setCenter(box);

        Button sleepButton = new Button("sleep");
        sleepButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sleepCommand();
                stage.close();
            }
        });
        box.getChildren().add(sleepButton);

        Button alertButton = new Button("alert");
        alertButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                alertCommand();
                stage.close();
            }
        });
        box.getChildren().add(alertButton);

        Button fortifyButton = new Button("fortify");
        fortifyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fortifyCommand();
                stage.close();
            }
        });
        box.getChildren().add(fortifyButton);

        Button healButton = new Button("heal");
        healButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                healCommand();
                stage.close();
            }
        });
        box.getChildren().add(healButton);

        Button garrisonButton = new Button("garrison");
        garrisonButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                garrisonCommand();
                stage.close();
            }
        });
        box.getChildren().add(garrisonButton);

        Button setupButton = new Button("setup");
        setupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setupCommand();
                stage.close();
            }
        });
        box.getChildren().add(setupButton);

        Button foundButton = new Button("found city");
        foundButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                foundCommand();
                stage.close();
            }
        });
        box.getChildren().add(foundButton);

        Button cancelButton = new Button("cancel");
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cancelCommand();
                stage.close();
            }
        });
        box.getChildren().add(cancelButton);

        Button wakeButton = new Button("wake");
        wakeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                wakeUnit();
                stage.close();
            }
        });
        box.getChildren().add(wakeButton);

        Button deleteButton = new Button("deleteUnit");
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteUnitCommand();
                stage.close();
            }
        });
        box.getChildren().add(deleteButton);

        Button pillageButton = new Button("pillage");
        pillageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pillageCommand();
                stage.close();
            }
        });
        box.getChildren().add(pillageButton);

        Button buildButton = new Button("build improvement");
        buildButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buildCommand();
                stage.close();
            }
        });
        box.getChildren().add(buildButton);

        Button repairButton = new Button("repair");
        repairButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                repairCommand();
                stage.close();
            }
        });
        box.getChildren().add(repairButton);

        Button exitButton = new Button("exit");
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });
        box.getChildren().add(exitButton);
    }
}