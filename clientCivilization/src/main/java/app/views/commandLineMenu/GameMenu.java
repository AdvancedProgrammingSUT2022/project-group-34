package app.views.commandLineMenu;

import app.controllers.CivilizationController;
import app.controllers.GLoad;
import app.controllers.GameController;
import app.controllers.InputController;
import app.models.Civilization;
import app.models.connection.Message;
import app.models.map.CivilizationMap;
import app.models.map.GameMap;
import app.models.miniClass.MiniGameController;
import app.models.resource.BonusResource;
import app.models.resource.LuxuryResource;
import app.models.resource.Resource;
import app.models.resource.StrategicResource;
import app.models.tile.*;
import app.models.unit.Settler;
import app.models.unit.Unit;
import app.models.unit.Worker;
import app.models.connection.Processor;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Random;

public class GameMenu extends Menu {

    private static Message message = new Message();
    private final static int VIEW_MAP_WIDTH = 9;
    private final static int VIEW_MAP_HEIGHT = 7;

    private final static String NON_NEGATIVE_NUMBER_REGEX = "^+?\\d+$";

    private final static String ANSI_RESET = "\u001B[0m";

    private final static String ANSI_BLACK = "\u001B[30m";
    private final static String ANSI_RED = "\u001B[31m";
    private final static String ANSI_GREEN = "\u001B[32m";
    private final static String ANSI_YELLOW = "\u001B[33m";
    private final static String ANSI_BLUE = "\u001B[34m";
    private final static String ANSI_PURPLE = "\u001B[35m";
    private final static String ANSI_CYAN = "\u001B[36m";
    private final static String ANSI_WHITE = "\u001B[37m";

    private final static String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private final static String ANSI_RED_BACKGROUND = "\u001B[41m";
    private final static String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private final static String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private final static String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private final static String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private final static String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private final static String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private final static String[] ANSI_COLOR = {ANSI_BLACK, ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE};
    private final static String[] ANSI_BACKGROUND = {ANSI_BLACK_BACKGROUND, ANSI_RED_BACKGROUND, ANSI_GREEN_BACKGROUND, ANSI_YELLOW_BACKGROUND,
            ANSI_BLUE_BACKGROUND, ANSI_PURPLE_BACKGROUND, ANSI_CYAN_BACKGROUND, ANSI_WHITE_BACKGROUND};

    private final static String[] CHARACTER_SEED = {"A", "B", "G", "K", "M", "!", "@", "#", "H", "$", "%", "^", "&", "3",
            "5", "8", "a", "g", "q", "0", "}", "{", "[", "|", ",", ";", "m", "?", "="};

    private static int mapX;
    private static int mapY;


    //Processes commands related with main menu
    static void processOneCommand() {
        Processor processor;
        while (getCurrentMenu().equals("game")) {
            message = new Message();
            isReceivedResponse = false;
            showMap();
            processor = new Processor(getInput());
            Menu.sendProcessor(processor,true);
            waitForResponse(-1);
        }
    }

    public static void setAndPrintMessage(Message receivedMessage) {
        Menu.setMessage(receivedMessage, message);
        printMessage(receivedMessage);

    }


    private static void showMap() {

        GameController gameController = loadDataForShowMap();

        Civilization civilization = gameController.getCivilization();
        System.out.printf("%s : ", civilization.getCivilizationName());
        System.out.printf("Turn %d\n", civilization.getTurn());

        CivilizationMap personalMap = civilization.getPersonalMap();
        GameMap map = gameController.getGame().getMainGameMap();

        int arrayHeight = VIEW_MAP_HEIGHT * 6 + 2;
        int arrayWidth = VIEW_MAP_WIDTH * 30;

        StringBuilder[][] output = new StringBuilder[arrayHeight][arrayWidth];

        for (int i = 0; i < arrayHeight; i++) {
            for (int j = 0; j < arrayWidth; j++) {
                output[i][j] = new StringBuilder(" ");
            }
        }

    /*
               0   3       12 16         25 29        38
    0              __________                __________
                  /          \              /          \
                 /    FOG     \            /            \
    3           /              \__________/              \
                \              /  D Ma    \              /
                 \            /      A     \            /
    6             \__________/    xx, yy    \__________/
                  /          \ R R s Bal    /          \
                 /            \  ???? Fa   /            \
    9           /              \__________/              \
                \              /          \              /
                 \            /            \            /
    12            \__________/              \__________/
    */

        for (int i = -(VIEW_MAP_HEIGHT / 2); i <= VIEW_MAP_HEIGHT / 2; i++) {
            for (int j = -(VIEW_MAP_WIDTH / 2); j <= VIEW_MAP_WIDTH / 2; j++) {
                if (j % 2 == 1 || j % 2 == -1) {
                    if (mapY % 2 == 1 && i == -(VIEW_MAP_HEIGHT / 2)) continue;
                    else if (mapY % 2 == 0 && i == VIEW_MAP_HEIGHT / 2) continue;
                }
                int x = mapX + i;
                int y = mapY + j;
                VisibleTile visibleTile = personalMap.getTileByXY(x, y);
                Tile tile = map.getTileByXY(x, y);

                int upperBound;
                int leftBound = 13 * (j + VIEW_MAP_WIDTH / 2);
                if (j % 2 == 0) {
                    upperBound = 6 * (i + VIEW_MAP_HEIGHT / 2);
                } else {
                    if (mapY % 2 == 1) {
                        upperBound = 6 * (i + VIEW_MAP_HEIGHT / 2) - 3;
                    } else {
                        upperBound = 6 * (i + VIEW_MAP_HEIGHT / 2) + 3;
                    }
                }
                putTile(civilization, tile, visibleTile, output, x, y, upperBound, leftBound);
            }
        }

        printMap(output);
    }

    private static GameController loadDataForShowMap() {
        HashMap<String,Object> data = getData();
        writeTemp(data.keySet().toString());
        mapX = getAndCast2Integer(new Gson().fromJson((String) data.get("mapX"),Double.class), mapX);
        mapY = getAndCast2Integer(new Gson().fromJson((String) data.get("mapY"),Double.class), mapY);
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

    private static void printMap(StringBuilder[][] output) {
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                if (output[i][j].length() == 0) continue;
                System.out.printf("%s", output[i][j].toString());
            }
            System.out.println();;
        }
    }

    private static void putTile(Civilization civilization, Tile tile, VisibleTile visibleTile, StringBuilder[][] output, int x, int y, int upperBound, int leftBound) {
        putTileBorders(civilization, tile, output, upperBound, leftBound, x, y);

        if (tile != null) {
            putString(x + "," + y, output, upperBound + 3, leftBound + 5);

            if (!civilization.isInFog(tile)) {
                putCivilization(visibleTile.getCivilization(), output, upperBound, leftBound);
                try {
                    putTerrain(visibleTile.getTerrain(), output, upperBound, leftBound);
                }catch (Exception ignored){}
                putFeature(visibleTile.getFeature(), output, upperBound, leftBound);

                if (civilization.isTransparent(tile)) {
                    putUnit(tile.getCombatUnit(), output, upperBound, leftBound);
                    putUnit(tile.getNonCombatUnit(), output, upperBound, leftBound);
                    if (tile.hasRoad()) putRoad(output, upperBound, leftBound);
                    if (tile.hasRail()) putRail(output, upperBound, leftBound);
                    putImprovement(tile.getImprovement(), output, upperBound, leftBound);
                    putResource(civilization, tile.getResource(), output, upperBound, leftBound);
                } else putRevealed(output, upperBound, leftBound);
            } else putFogOfWar(output, upperBound, leftBound);
        } else putNullTile(output, upperBound, leftBound);
    }

    private static void putTileBorders(Civilization civilization, Tile tile, StringBuilder[][] output, int upperBound, int leftBound, int x, int y) {
        putString("__________", output, upperBound, leftBound + 3);
        putString("/          \\", output, upperBound + 1, leftBound + 2);
        putString("/            \\", output, upperBound + 2, leftBound + 1);
        putString("/              \\", output, upperBound + 3, leftBound + 0);
        putString("\\              /", output, upperBound + 4, leftBound + 0);
        putString("\\            /", output, upperBound + 5, leftBound + 1);
        putString("\\__________/", output, upperBound + 6, leftBound + 2);

        if (!civilization.isInFog(tile)) putRivers(tile, output, upperBound, leftBound, x, y);
    }

    private static void putColor(String colorCode, StringBuilder[][] output, int x, int y, int count) {
        output[x][y].insert(0, colorCode);
        output[x][y + count - 1].append(ANSI_RESET);
    }

    private static void putColor(String colorCode, StringBuilder[][] output, int x, int y) {
        putColor(colorCode, output, x, y, 1);
    }

    private static void putColorIfUncolored(String colorCode, StringBuilder[][] output, int x, int y, int count) {
        for (int i = y; i < y + count; i++) {
            if (output[x][i].length() == 1) putColor(colorCode, output, x, i);
        }
    }

    private static void fullWithRandomChars(String colorCode, StringBuilder[][] output, int x, int y, int count) {
        Random random = new Random(2 * x + y * y * y);
        for (int i = y; i < y + count; i++) {
            if (output[x][i].length() == 0 || output[x][i].charAt(0) != ' ') continue;
            output[x][i] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
        }
    }

    private static void putString(String str, StringBuilder[][] output, int x, int y) {
        for (int i = 0; i < str.length(); i++) {
            output[x][y + i] = new StringBuilder(str.substring(i, i + 1));
        }
    }

    private static void putRevealed(StringBuilder[][] output, int upperBound, int leftBound) {
        String colorCode = ANSI_GREEN_BACKGROUND;
        putColorIfUncolored(colorCode, output, upperBound + 1, leftBound + 3, 10);
        putColorIfUncolored(colorCode, output, upperBound + 2, leftBound + 2, 12);
        putColorIfUncolored(colorCode, output, upperBound + 3, leftBound + 1, 14);
        putColorIfUncolored(colorCode, output, upperBound + 4, leftBound + 1, 14);
        putColorIfUncolored(colorCode, output, upperBound + 5, leftBound + 2, 12);
    }

    private static void putFogOfWar(StringBuilder[][] output, int upperBound, int leftBound) {
        putString("FOG", output, upperBound + 2, leftBound + 6);
        String colorCode = ANSI_PURPLE;
        fullWithRandomChars(colorCode, output, upperBound + 1, leftBound + 3, 10);
        fullWithRandomChars(colorCode, output, upperBound + 2, leftBound + 2, 12);
        fullWithRandomChars(colorCode, output, upperBound + 3, leftBound + 1, 14);
        fullWithRandomChars(colorCode, output, upperBound + 4, leftBound + 1, 14);
        fullWithRandomChars(colorCode, output, upperBound + 5, leftBound + 2, 12);
    }

    private static void putNullTile(StringBuilder[][] output, int upperBound, int leftBound) {
        String backgroundCode = ANSI_RED_BACKGROUND;
        putColor(backgroundCode, output, upperBound + 1, leftBound + 3, 10);
        putColor(backgroundCode, output, upperBound + 2, leftBound + 2, 12);
        putColor(backgroundCode, output, upperBound + 3, leftBound + 1, 14);
        putColor(backgroundCode, output, upperBound + 4, leftBound + 1, 14);
        putColor(backgroundCode, output, upperBound + 5, leftBound + 2, 12);
    }





    private static void putRivers(Tile tile, StringBuilder[][] output, int upperBound, int leftBound, int x, int y) {
        //TODO : bad coordinations for rivers
        Tile otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y});
        if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
            putRiver(output, upperBound, leftBound, 0);
        otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y});
        if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
            putRiver(output, upperBound, leftBound, 3);
        if (y % 2 == 0) {
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 5);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 4);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 1);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 2);
        } else {
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 5);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 4);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 1);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile))
                putRiver(output, upperBound, leftBound, 2);
        }
    }

    private static void putRiver(StringBuilder[][] output, int upperBound, int leftBound, int whichBorderFromUpperClockwise) {
        int whichBorder = whichBorderFromUpperClockwise;
        if (whichBorder == 0) putColor(ANSI_BLUE_BACKGROUND, output, upperBound, leftBound + 3, 10);
        else if (whichBorder == 1) {
            //TODO : leftBound + 1
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 1, leftBound + 12);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 2, leftBound + 13);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 3, leftBound + 14);
        } else if (whichBorder == 2) {
            //TODO : leftBound + 1
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 6, leftBound + 12);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 5, leftBound + 13);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 4, leftBound + 14);
        } else if (whichBorder == 3) putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 6, leftBound + 2, 12);
        else if (whichBorder == 4) {
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 6, leftBound + 2);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 5, leftBound + 1);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 4, leftBound + 0);
        } else if (whichBorder == 5) {
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 1, leftBound + 2);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 2, leftBound + 1);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 3, leftBound + 0);
        }
    }

    private static void putCivilization(Civilization civilization, StringBuilder[][] output, int upperBound, int leftBound) {
        if (civilization == null) return;
        int index = GameController.getInstance().getIndex(civilization);
        putString(String.valueOf((char) ('A' + index)), output, upperBound + 2, leftBound + 8);
        String colorCode = ANSI_COLOR[index % 7 + 1];
        putColor(colorCode, output, upperBound + 2, leftBound + 8);
    }

    private static void putTerrain(Terrain terrain, StringBuilder[][] output, int upperBound, int leftBound){
        String terrainName = terrain.getName();
        putString(terrainName.substring(0, 1), output, upperBound + 1, leftBound + 5);
        String colorCode = ANSI_WHITE;
        if (terrainName.equals("Desert")) colorCode = ANSI_YELLOW;
        else if (terrainName.equals("Grasslands")) colorCode = ANSI_GREEN;
        else if (terrainName.equals("Ocean")) colorCode = ANSI_BLUE;
        putColor(colorCode, output, upperBound + 1, leftBound + 5);
    }

    private static void putFeature(Feature feature, StringBuilder[][] output, int upperBound, int leftBound) {
        if (feature == null) return;
        String featureName = feature.getName().toString();
        putString(featureName.substring(0, 2), output, upperBound + 1, leftBound + 7);
        String colorCode = ANSI_WHITE;
        if (featureName.equals("FloodPlane")) colorCode = ANSI_GREEN;
        else if (featureName.equals("Forests")) colorCode = ANSI_GREEN;
        else if (featureName.equals("Jungle")) colorCode = ANSI_GREEN;
        else if (featureName.equals("Marsh")) colorCode = ANSI_YELLOW;
        else if (featureName.equals("Oasis")) colorCode = ANSI_YELLOW;
        putColor(colorCode, output, upperBound + 1, leftBound + 7, 2);
    }

    private static void putUnit(Unit unit, StringBuilder[][] output, int upperBound, int leftBound) {
        if (unit == null) return;
        int index = unit.getCivilizationIndex();
        String shownName;
        if (unit instanceof Settler) shownName = "S";
        else if (unit instanceof Worker) shownName = "W";
        else shownName = unit.getName().substring(0, 3);
        putString(shownName, output, upperBound + 4, leftBound + 6);
        String colorCode = ANSI_COLOR[index % 7 + 1];
        putColor(colorCode, output, upperBound + 4, leftBound + 6, shownName.length());
    }

    private static void putRoad(StringBuilder[][] output, int upperBound, int leftBound) {
        putString("R", output, upperBound + 4, leftBound + 2);
        putColor(ANSI_GREEN, output, upperBound + 4, leftBound + 2);
    }

    private static void putRail(StringBuilder[][] output, int upperBound, int leftBound) {
        putString("R", output, upperBound + 4, leftBound + 4);
        putColor(ANSI_YELLOW, output, upperBound + 4, leftBound + 4);
    }

    private static void putImprovement(Improvement improvement, StringBuilder[][] output, int upperBound, int leftBound) {
        if (improvement == null) return;
        putString(improvement.getName().substring(0, 2), output, upperBound + 5, leftBound + 9);
        putColor(ANSI_CYAN, output, upperBound + 5, leftBound + 9, 2);
    }

    private static void putResource(Civilization civilization, Resource resource, StringBuilder[][] output, int upperBound, int leftBound) {

        if (resource == null) return;
        String name = resource.getName();

        if (resource instanceof StrategicResource && civilization.hasResearched(((StrategicResource) resource).getRequiredTechnology())) {
            putString("????", output, upperBound + 5, leftBound + 4);
        } else putString(name.substring(0, Math.min(4, name.length())), output, upperBound + 5, leftBound + 4);

        if (resource instanceof BonusResource)
            putColor(ANSI_CYAN, output, upperBound + 5, leftBound + 4, Math.min(4, name.length()));
        else if (resource instanceof LuxuryResource)
            putColor(ANSI_GREEN, output, upperBound + 5, leftBound + 4, Math.min(4, name.length()));
        else if (resource instanceof StrategicResource) {
            if (civilization.hasResearched(((StrategicResource) resource).getRequiredTechnology()))
                putColor(ANSI_PURPLE, output, upperBound + 5, leftBound + 4, Math.min(4, name.length()));
            else putColor(ANSI_RED, output, upperBound + 5, leftBound + 4, Math.min(4, name.length()));
        }
    }

}