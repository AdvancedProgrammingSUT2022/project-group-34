package views;

import controllers.CheatController;
import controllers.CivilizationController;
import controllers.GameController;
import models.City;
import models.Civilization;
import models.Notification;
import models.Technology;
import models.map.CivilizationMap;
import models.map.GameMap;
import models.tile.Feature;
import models.tile.Terrain;
import models.tile.Tile;
import models.tile.VisibleTile;
import models.unit.*;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Random;

public class GameMenu extends Menu {
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

    private static CombatUnit selectedCombatUnit = null;
    private static NonCombatUnit selectedNonCombatUnit = null;
    private static City selectedCity = null;
    private static int mapX = -1;
    private static int mapY = -1;


    //Processes commands related with main menu
    static void processOneCommand() {
        Processor processor;

        while (getCurrentMenu().equals("game")) {
            processor = new Processor(getInput());

            if (!processor.isValid() || processor.getCategory() == null) invalidCommand();
            else if (processor.getCategory().equals("select")) handleSelectCategoryCommand(processor);
            else if (processor.getCategory().equals("unit")) handleUnitCategoryCommand(processor);
            else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
            else if (processor.getCategory().equals("info")) handleInfoCategoryCommand(processor);
            else if (processor.getCategory().equals("cheat")) handleCheatCategoryCommand(processor);
            else invalidCommand();
        }
    }


    /*Handles commands that start with "select unit":
    1.select unit combat --x <x> --y <y>
    2.select unit noncombat --x <x> --y <y>
    3.select city --name <name>
    4.select city --x <x> --y <y>*/
    private static void handleSelectCategoryCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        String name = processor.get("nickname");
        if (name == null) name = processor.get("name");

        if (processor.getSection() == null)
            invalidCommand();
        else if (processor.getSection().equals("unit")) {
            if (x == null || y == null ||
                    processor.getNumberOfFields() != 2 ||
                    processor.getSubSection() == null ||
                    (!processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")))
                invalidCommand();
            else selectUnit(new int[]{Integer.parseInt(x), Integer.parseInt(y)}, processor.getSubSection());
        } else if (processor.getSection().equals("city")) {
            if (processor.getSubSection() != null) invalidCommand();
            else if (x != null) {
                if (y == null || processor.getNumberOfFields() != 2) invalidCommand();
                else selectCity(new int[]{Integer.parseInt(x), Integer.parseInt(y)});
            } else if (name != null) {
                if (processor.getNumberOfFields() != 1) invalidCommand();
                else selectCity(name);
            } else invalidCommand();
        } else
            invalidCommand();
    }

    private static void selectUnit(int[] position, String unitType) {
        switch (CivilizationController.getInstance().selectUnit(position, unitType)) {
            case "invalid position":
                System.out.println("Position is invalid");
                break;
            case "no such unit":
                System.out.format("No %s unit exists in position[%d %d]\n",
                        unitType.substring(0, 1).toUpperCase() + unitType.substring(1), position[0], position[1]);
                break;
            case "not yours":
                System.out.println("This unit is not yours");
                break;
            case "combat":
                selectedCombatUnit = CivilizationController.getInstance().getCombatUnitByPosition(position);
                selectedNonCombatUnit = null;
                selectedCity = null;
                System.out.println("Combat unit selected");
                break;
            case "noncombat":
                selectedNonCombatUnit = CivilizationController.getInstance().getNonCombatUnitByPosition(position);
                selectedCombatUnit = null;
                selectedCity = null;
                System.out.println("Noncombat unit selected");
                break;
        }
    }

    private static void selectCity(int[] position) {
        Tile tile = CivilizationController.getInstance().getTileByPosition(position);
        City city = tile.getCity();

        if (tile == null)
            System.out.println("Position is invalid");
        else if (tile.getCity() == null)
            System.out.println("There is no city in the selected area");
        else if (!GameController.getInstance().getCivilization().getCities().contains(city))
            System.out.println("This city is not in your territory");
        else {
            selectedCity = city;
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            System.out.format("%s city is selected\n", city.getName());
        }
    }

    private static void selectCity(String name) {
        City city;

        for (Civilization civilization : GameController.getInstance().getGame().getCivilizations()) {
            if ((city = civilization.getCityByName(name)) != null) {
                if (civilization == GameController.getInstance().getCivilization()) {
                    selectedCity = city;
                    selectedCombatUnit = null;
                    selectedNonCombatUnit = null;
                    System.out.format("%s city is selected\n", city.getName());
                } else System.out.println("This city is not in your territory");

                return;
            }
        }
        System.out.format("There is no city named %s\n", name);
    }


    /*Handles commands that start with "unit":
    unit moveto --x <x> --y <y>
    unit sleep
    unit found city
    unit fortify
    unit heal
    unit garrison
    unit setup
    unit cancel
    unit found city --name <name>
    unit wake
    unit delete
    unit pillage*/
    private static void handleUnitCategoryCommand(Processor processor) {

        // TODO: Booleans in unit class (Specially combat units)
        if (selectedCombatUnit == null || selectedNonCombatUnit == null)
            System.out.println("Please select a unit first");
        else if (processor.getSection() == null)
            invalidCommand();
        else if (processor.getSection().equals("moveto"))
            movetoCommand(processor);
        else if (processor.getSection().equals("sleep"))
            sleepCommand();
        else if (processor.getSection().equals("alert"))
            alertCommand();
        else if (processor.getSection().equals("fortify"))
            fortifyCommand();
        else if (processor.getSection().equals("heal"))
            healCommand();
        else if (processor.getSection().equals("garrison"))
            garrisonCommand();
        else if (processor.getSection().equals("setup"))
            setupCommand();
        else if (processor.getSection().equals("attack"))
            ;// TODO: 5/10/2022
        else if (processor.getSection().equals("found"))
            foundCityCommand(processor);
        else if (processor.getSection().equals("cancel"))
            cancelCommand();
        else if (processor.getSection().equals("wake"))
            wakeUnit();
        else if (processor.getSection().equals("delete"))
            deleteUnitCommand();
        else if (processor.getSection().equals("pillage"))
            pillageCommand();
        else if (processor.getSection().equals("build"))
            ;// TODO: 5/10/2022
        else if (processor.getSection().equals("remove"))
            ;// TODO: 5/10/2022
        else if (processor.getSection().equals("repair"))
            ;// TODO: 5/10/2022
        else
            invalidCommand();
    }

    private static void movetoCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        Unit unit = null;

        if (selectedNonCombatUnit != null) unit = selectedNonCombatUnit;
        else if (selectedCombatUnit != null) unit = selectedCombatUnit;

        if (x == null || y == null || processor.getNumberOfFields() != 2) {
            invalidCommand();
            return;
        }


        int[] position = new int[]{Integer.parseInt(x), Integer.parseInt(y)};
        String response = CivilizationController.getInstance().moveUnit(unit, position);

        if (response.equals("invalid destination"))
            System.out.println("Destination is not valid");
        else if (response.equals("fog of war"))
            System.out.println("Destination is in fog of war");
        else if (response.equals("already at the same tile"))
            System.out.println("We already are at the tile you want to move to");
        else if (response.equals("destination occupied"))
            System.out.println("There is already a unit in the tile you want to move to");
        else if (response.equals("no valid path"))
            System.out.println("There is no path to the tile you want to move to");
        else if (response.equals("success")) {
            unit.makeUnitAwake();
            System.out.println("Unit moved to destination successfully");
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
        }
    }

    private static void sleepCommand() {
        if (selectedNonCombatUnit != null) selectedNonCombatUnit.setSleep(true);
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setSleep(true);
        }
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        System.out.println("Unit is sleep");
    }

    private static void alertCommand() {
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setAlert(true);
            selectedCombatUnit = null;
            System.out.println("Unit is alert");
        }
    }

    private static void fortifyCommand() {
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setFortify(true);
            selectedCombatUnit = null;
            System.out.println("Unit is fortified");
        }
    }

    private static void healCommand() {
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setFortifyUntilHealed(true);
            selectedCombatUnit = null;
            System.out.println("Unit is healed");
        }
    }

    private static void garrisonCommand() {
        switch (CivilizationController.getInstance().garrisonCity(selectedCombatUnit)) {
            case "not military":
                System.out.println("Selected unit is not a military unit");
                break;
            case "no city":
                System.out.println("There is no city in this tile");
                break;
            case "ok":
                selectedCombatUnit = null;
                System.out.println("City is garrisoned");
                break;
        }
    }

    private static void setupCommand() {
        if (!(selectedCombatUnit instanceof Archer) || !((Archer) selectedCombatUnit).isSiegeTool)
            System.out.println("Selected unit is not a siege tool unit");
        else {
            selectedCombatUnit.makeUnitAwake();
            ((Archer) selectedCombatUnit).setSetup(true);
            selectedCombatUnit = null;
            System.out.println("Unit is setup");
        }
    }

    private static void cancelCommand() {
        Unit unit = selectedCombatUnit;
        if (unit == null) unit = selectedNonCombatUnit;

        if (unit.getDestination() == unit.getPosition())
            System.out.println("Unit is not in a multiple-turn movement");
        else {
            unit.setDestination(unit.getPosition());
            System.out.println("Unit's multiple-turn movement canceled");
        }
    }

    private static void foundCityCommand(Processor processor) {
        String name = processor.get("nickname");
        if (name == null) name = processor.get("name");

        if (name == null || processor.getNumberOfFields() != 1)
            invalidCommand();
        if (processor.getSubSection() != null && processor.getSubSection().equals("city")) {
            if (!(selectedNonCombatUnit instanceof Settler))
                System.out.println("Selected unit is not a settler");
            else {
                switch (CivilizationController.getInstance().foundCity((Settler) selectedNonCombatUnit, name)) {
                    case "too close":
                        System.out.println("You cannot found a city close to another civilization");
                        break;
                    case "duplicate name":
                        System.out.println("This name is used before");
                        break;
                    case "ok":
                        System.out.println("City founded!");
                        selectedNonCombatUnit = null;
                        break;
                }
            }
        } else
            invalidCommand();
    }

    private static void wakeUnit() {
        if (selectedNonCombatUnit != null) selectedNonCombatUnit.makeUnitAwake();
        else selectedCombatUnit.makeUnitAwake();

        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        System.out.println("Unit is awake");
    }

    private static void deleteUnitCommand() {
        Unit unit = selectedCombatUnit;
        if (unit == null) unit = selectedNonCombatUnit;

        CivilizationController.getInstance().deleteUnit(unit);

        selectedNonCombatUnit = null;
        selectedCombatUnit = null;
        System.out.println("Unit deleted");
    }

    private static void pillageCommand() {
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else if (selectedCombatUnit.getPosition().getImprovementName() == null)
            System.out.println("There is no improvement in this tile");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.getPosition().setLooted(true);
            selectedCombatUnit = null;
            System.out.println("Tile is pillaged");
        }
    }


    /*Handles commands that start with "info":
    1.info research
    2.info units
    3.info cities
    4.info demographics
    5.info notifications
    6.info military
    7.info economic*/
    private static void handleInfoCategoryCommand(Processor processor) {
        if (processor.getSection() == null)
            invalidCommand();
        else if (processor.getSection().equals("research"))
            researchInfoMenu();
        else if (processor.getSection().equals("units"))
            unitsInfoPanel();
        else if (processor.getSection().equals("cities"))
            citiesInfoPanel();
        else if (processor.getSection().equals("diplomacy"))
            ;// TODO: Next phase
        else if (processor.getSection().equals("victory"))
            ;// TODO: Next phase
        else if (processor.getSection().equals("demographics"))
            ;// TODO: 5/11/2022
        else if (processor.getSection().equals("notifications"))
            notificationsInfoMenu();
        else if (processor.getSection().equals("military"))
            militaryInfoMenu();
        else if (processor.getSection().equals("economic"))
            economicInfoMenu();
        else if (processor.getSection().equals("diplomatic"))
            ;// TODO: Next phase
        else if (processor.getSection().equals("deals"))
            ;// TODO: Next phase
        else
            invalidCommand();
    }

    private static void researchInfoMenu() {
        Technology technology = GameController.getInstance().getCivilization().getStudyingTechnology();

        StringBuilder output = new StringBuilder("Current Research Project: ").append(technology.getName()).append("\n");
        output.append("Remaining terms: ").append(technology.getRemainingTerm()).append("\n");
        output.append("Features unlocked: ").append("\n");
        // TODO: technologies, improvements, units, resources

        System.out.println(output);
    }

    // TODO: Sort units arraylist
    private static void unitsInfoPanel() {
        Civilization civilization = GameController.getInstance().getCivilization();

        StringBuilder output = new StringBuilder("List of units: ").append("\n");
        for (int i = 1; i <= civilization.getUnits().size(); i++) {
            Unit unit = civilization.getUnits().get(i - 1);

            output.append(i).append(".").append(unit.getName()).append("|(").append(unit.getPosition().getX());
            output.append(", ").append(unit.getPosition().getY()).append(")").append("\n");
        }

        output.append("If you want to select a unit, please type its index.\n");
        output.append("If you want to enter military overview screen, please type \"military\".\n");
        output.append("If you want to exit the panel, please type \"exit\".");

        System.out.println(output);

        String choice = getUnitsPanelChoice(civilization);
        if (choice.equals("military"))
            militaryInfoMenu();
        else if (choice.equals("exit"))
            return;
        else {
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            selectedCity = null;
            int index = Integer.parseInt(choice) - 1;
            Unit unit = civilization.getUnits().get(index);
            if (unit instanceof CombatUnit) selectedCombatUnit = (CombatUnit) unit;
            else selectedNonCombatUnit = (NonCombatUnit) unit;
        }
    }

    private static String getUnitsPanelChoice(Civilization civilization) {
        String choice;
        while (true) {
            choice = getInput();

            if (choice.equals("military")) return choice;
            else if (choice.equals("exit")) return choice;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);

                if (number < 1 || number > civilization.getUnits().size())
                    System.out.println("Invalid number!");
                else return choice;
            } else invalidCommand();
        }
    }

    private static void citiesInfoPanel() {
        Civilization civilization = GameController.getInstance().getCivilization();

        StringBuilder output = new StringBuilder("List of cities: ").append("\n");
        for (int i = 1; i <= civilization.getCities().size(); i++) {
            City city = civilization.getCities().get(i - 1);

            output.append(i).append(".Name:").append(city.getName());
            if (civilization.getCurrentCapital().equals(city)) output.append("(Capital)");
            output.append("|(").append(city.getPosition().getX()).append(", ").append(city.getPosition().getY()).append(")");
            output.append("|Number of Citizens:").append(city.getCitizens().size());
            output.append("|City Production:").append(city.getUnitUnderProduct().getName()).append("\n");
        }

        output.append("If you want to select a city, please type its index.\n");
        output.append("If you want to enter economic overview screen, please type \"economic\".\n");
        output.append("If you want to exit the panel, please type \"exit\".");

        System.out.println(output);

        String choice = getCitiesPanelChoice(civilization);
        if (choice.equals("economic"))
            economicInfoMenu();
        else if (choice.equals("exit"))
            return;
        else {
            // TODO: Enter city screen
        }
    }

    private static String getCitiesPanelChoice(Civilization civilization) {
        String choice;
        while (true) {
            choice = getInput();

            if (choice.equals("economic")) return choice;
            else if (choice.equals("exit")) return choice;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);

                if (number < 1 || number > civilization.getCities().size())
                    System.out.println("Invalid number!");
                else return choice;
            } else invalidCommand();
        }
    }

    private static void diplomacyInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void victoryInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void demographicsInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void notificationsInfoMenu() {
        Civilization civilization = GameController.getInstance().getCivilization();

        StringBuilder output = new StringBuilder("List of notifications:").append("\n");
        for (Notification notification : civilization.getNotifications()) {
            output.append("Message:").append(notification.getMessage());
            output.append("|Tern:").append(notification.getTern()).append("\n");
        }

        System.out.println(output);
    }

    private static void militaryInfoMenu() {
        Civilization civilization = GameController.getInstance().getCivilization();

        StringBuilder output = new StringBuilder("List of units: ").append("\n");
        printListOfUnits(output, civilization);

        System.out.println(output);
    }

    private static void printListOfUnits(StringBuilder output, Civilization civilization) {
        for (int i = 1; i <= civilization.getUnits().size(); i++) {
            Unit unit = civilization.getUnits().get(i - 1);
            output.append(i).append(".").append(unit.getName()).append("|(").append(unit.getPosition().getX());
            output.append(", ").append(unit.getPosition().getY()).append(")").append("|");
            if (unit.isSleep()) output.append("sleep");
            else {
                if (unit instanceof CombatUnit) {
                    if (((CombatUnit) unit).isAlert()) output.append("alert");
                    else if (((CombatUnit) unit).isFortify()) output.append("fortified");
                    else if (((CombatUnit) unit).isFortifyUntilHealed()) output.append("healed");
                    else output.append("active");
                } else if (unit instanceof Worker)
                    if (((Worker) unit).isWorking) output.append("is working");
                    else output.append("active");
            }
            output.append("\n");
        }
    }

    private static void economicInfoMenu() {
        Civilization civilization = GameController.getInstance().getCivilization();

        StringBuilder output = new StringBuilder("List of cities: ").append("\n");
        printListOfCities(output, civilization);

        output.append("If you want to select a city, please type its index.\n");
        output.append("If you want to exit the screen, please type \"exit\".\n");

        System.out.println(output);

        String choice = getEconomicMenuChoice(civilization);

        if (choice.equals("exit"))
            return;
        else {
            // TODO: Enter city screen
        }
    }

    private static void printListOfCities(StringBuilder output, Civilization civilization) {
        for (int i = 1; i <= civilization.getCities().size(); i++) {
            City city = civilization.getCities().get(i - 1);

            output.append(i).append(".Name:").append(city.getName());
            if (civilization.getCurrentCapital().equals(city)) output.append("(Capital)");
            output.append("|(").append(city.getPosition().getX()).append(", ").append(city.getPosition().getY()).append(")");
            output.append("|Number of Citizens:").append(city.getCitizens().size());
            output.append("|City Strength:").append(city.getStrength());
            output.append("|Food:").append(city.getFood());
            output.append("|Production Rate:").append(city.getProductionRate());
            output.append("|Science Rate:").append(city.getScienceRate());
            output.append("|Gold Rate:").append(city.getGoldRate());
            output.append("|City Production:").append(city.getUnitUnderProduct().getName());
            output.append("(").append(city.getUnitUnderProductTern()).append("\n");
        }
    }

    private static String getEconomicMenuChoice(Civilization civilization){
        String choice;

        while (true){
            choice=getInput();
            if (choice.equals("exit")) return choice;
            else if (choice.matches("\\d+")){
                int number = Integer.parseInt(choice);

                if (number<1||number>civilization.getCities().size()) System.out.println("Invalid number!");
                else return choice;
            }else
                invalidCommand();
        }
    }

    private static void diplomaticInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void dealsInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }


    private static void handleCityCategoryCommand(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void handleMapCategoryCommand(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void handleTurnCategory(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void handleCheatCategoryCommand(Processor processor) {
        if (processor.getSection().equals("increase")) increaseCheatCommand(processor.get("amount"), processor.getSubSection());
        else if (processor.getSection().equals("teleport")) teleportCheatCommand(processor.get("x"), processor.get("y"));
        else if (processor.getSection().equals("finish")) finishCheatCommand(processor.getSubSection());
        else if (processor.getSection().equals("reveal")) revealCheatCommand(processor.get("x"), processor.get("y"));
        else invalidCommand();

    }

    private static void increaseCheatCommand(String amountField, String subSection) {
        if (amountField == null) {
            System.out.printf("field 'amount' required\n");
        }
        else if (!amountField.matches(NON_NEGATIVE_NUMBER_REGEX)) {
            System.out.printf("amount must be a valid non-negative integer\n");
            return;
        }
        int amount = Integer.parseInt(amountField);
        if (subSection.equals("gold")) {
            CheatController.getInstance().increaseGold(amount);
            System.out.printf("Done");
        }
        else if (subSection.equals("beaker")) {
            CheatController.getInstance().increaseBeaker(amount);
            System.out.printf("Done");
        }
        else invalidCommand();
    }

    private static void teleportCheatCommand(String xField, String yField) {
        Unit selectedUnit = selectedCombatUnit;
        if (selectedUnit == null) selectedUnit = selectedNonCombatUnit;
        if (selectedUnit == null) {
            System.out.printf("no unit selected\n");
            return;
        }
        if (xField == null || yField == null) System.out.printf("fields 'x' and 'y' required\n");
        else if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX)) System.out.printf("x, y must be valid non-negative integers");
        else {
            int x = Integer.parseInt(xField);
            int y = Integer.parseInt(yField);
            System.out.printf("%s\n", CheatController.getInstance().teleport(selectedUnit, x, y));
        }
    }

    private static void finishCheatCommand(String subSection) {
        if (!subSection.equals("research")) invalidCommand();
        else System.out.printf(CheatController.getInstance().finishResearch());
    }

    private static void revealCheatCommand(String xField, String yField) {
        if (xField == null || yField == null) System.out.printf("fields 'x' and 'y' required\n");
        else if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX)) System.out.printf("x, y must be valid non-negative integers");
        else {
            int x = Integer.parseInt(xField);
            int y = Integer.parseInt(yField);
            System.out.printf("%s\n", CheatController.getInstance().reveal(x, y));
        }
    }


    private static void handleCityInfo(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void showMap() {
        // TODO
        Civilization civilization = GameController.getInstance().getCivilization();
        System.out.printf("%s : ", civilization.getCivilizationName());
        System.out.printf("Turn %d\n", civilization.getTurn());

        CivilizationMap personalMap = civilization.getPersonalMap();
        GameMap map = GameController.getInstance().getGame().getMainGameMap();

        int arrayHeight = VIEW_MAP_HEIGHT * 10;
        int arrayWidth = VIEW_MAP_WIDTH * 20;

        StringBuilder output[][] = new StringBuilder[arrayHeight][arrayWidth];

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
                      /          \     s Bal    /          \
                     /            \            /            \
        9           /              \__________/              \
                    \              /          \              /
                     \            /            \            /
        12            \__________/              \__________/
        */

        for (int i = -VIEW_MAP_HEIGHT / 2; i <= VIEW_MAP_HEIGHT; i++) {
            for (int j = -VIEW_MAP_WIDTH / 2; j <= VIEW_MAP_WIDTH; j++) {
                if (j % 2 == 1) {
                    if (mapY % 2 == 1 && i == -VIEW_MAP_HEIGHT) continue;
                    else if (mapY % 2 == 0 && i == VIEW_MAP_HEIGHT) continue;
                }
                int x = mapX + i;
                int y = mapY + j;
                VisibleTile visibleTile = personalMap.getTileByXY(x, y);
                Tile tile = map.getTileByXY(x, y);

                int upperBound;
                int leftBound = 13 * (j + VIEW_MAP_WIDTH);
                if (j % 2 == 0) {
                    upperBound = 6 * (i + VIEW_MAP_HEIGHT);
                }
                else {
                    if (mapY % 2 == 1) {
                        upperBound = 6 * (i + VIEW_MAP_HEIGHT) - 3;
                    }
                    else {
                        upperBound = 6 * (i + VIEW_MAP_HEIGHT) + 3;
                    }
                }

                // TODO : handle improvements, resources;

                putTile(civilization, tile, visibleTile, output, x, y, upperBound, leftBound);
            }
        }

        printMap(output);
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
            if (output[x][i].charAt(0) != ' ') continue;
            output[x][i] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
        }
    }

    private static void putString(String str, StringBuilder[][] output, int x, int y) {
        for (int i = 0; i < str.length(); i++) {
            output[x][y + i] = new StringBuilder(str.charAt(i));
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

    private static void putTile(Civilization civilization, Tile tile, VisibleTile visibleTile, StringBuilder[][] output, int x, int y, int upperBound, int leftBound) {
        putTileBorders(civilization, tile, output, upperBound, leftBound, x, y);
        
        if (tile != null) {
            putString(x + "," + y, output, upperBound + 3, leftBound + 5);

            if (!civilization.isInFog(tile)) {
                putCivilization(visibleTile.getCivilization(), output, upperBound, leftBound);
                putTerrain(visibleTile.getTerrain(), output, upperBound, leftBound);
                putFeature(visibleTile.getFeature(), output, upperBound, leftBound);

                if (civilization.isTransparent(tile)) {
                    putUnit(tile.getCombatUnit(), output, upperBound, leftBound);
                    putUnit(tile.getNonCombatUnit(), output, upperBound, leftBound);
                }
                else putRevealed(output, upperBound, leftBound);
            }
            else putFogOfWar(output, upperBound, leftBound);
        }
        else putNullTile(output, upperBound, leftBound);
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

    private static void putRivers(Tile tile, StringBuilder[][] output, int upperBound, int leftBound, int x, int y) {
        Tile otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y});
        if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 0);
        otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y});
        if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 3);
        if (y % 2 == 0) {
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 5);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 4);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x - 1, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 1);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 2);
        }
        else {
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 5);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y - 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 4);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 1);
            otherTile = CivilizationController.getInstance().getTileByPosition(new int[]{x + 1, y + 1});
            if (CivilizationController.getInstance().isRiverBetween(tile, otherTile)) putRiver(output, upperBound, leftBound, 2);
        }
    }

    private static void putRiver(StringBuilder[][] output, int upperBound, int leftBound, int whichBorderFromUpperClockwise) {
        int whichBorder = whichBorderFromUpperClockwise;
        if (whichBorder == 0) putColor(ANSI_BLUE_BACKGROUND, output, upperBound, leftBound + 3, 10);
        else if (whichBorder == 1) {
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 1, leftBound + 12);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 2, leftBound + 13);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 3, leftBound + 14);
        }
        else if (whichBorder == 2) {
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 6, leftBound + 12);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 5, leftBound + 13);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 4, leftBound + 14);
        }
        else if (whichBorder == 3) putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 6, leftBound + 2, 12);
        else if (whichBorder == 4) {
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 6, leftBound + 2);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 5, leftBound + 1);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 4, leftBound + 0);
        }
        else if (whichBorder == 5) {
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 1, leftBound + 2);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 2, leftBound + 1);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 3, leftBound + 0);
        }
    }

    private static void putCivilization(Civilization civilization, StringBuilder[][] output, int upperBound, int leftBound) {
        if (civilization == null) return;
        int index = GameController.getInstance().getIndex(civilization);
        putString(String.valueOf((char)('A' + index)), output, upperBound + 2, leftBound + 8);
        String colorCode = ANSI_COLOR[index % 7 + 1];
        putColor(colorCode, output, upperBound + 2, leftBound + 8);
    }

    private static void putTerrain(Terrain terrain, StringBuilder[][] output, int upperBound, int leftBound) {
        String terrainName = terrain.name.toString();
        putString(terrainName.substring(0, 1), output, upperBound + 1, leftBound + 5);
        String colorCode = ANSI_WHITE;
        if (terrainName.equals("Desert")) colorCode = ANSI_YELLOW;
        else if (terrainName.equals("Grasslands")) colorCode = ANSI_GREEN;
        else if (terrainName.equals("Ocean")) colorCode = ANSI_BLUE;
        putColor(colorCode, output, upperBound + 1, leftBound + 5);
    }

    private static void putFeature(Feature feature, StringBuilder[][] output, int upperBound, int leftBound) {
        if (feature == null) return;
        String featureName = feature.name.toString();
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
        int index = GameController.getInstance().getIndex(unit.getCivilization());
        String shownName;
        if (unit instanceof Settler) shownName = "S";
        else if (unit instanceof Worker) shownName = "W";
        else shownName = unit.getName().substring(0, 3);
        putString(shownName, output, upperBound + 4, upperBound + 6);
        String colorCode = ANSI_COLOR[index % 7 + 1];
        putColor(colorCode, output, upperBound + 4, leftBound + 6, shownName.length());
    }

    private static void printMap(StringBuilder[][] output) {
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                System.out.printf("%s", output[i][j].toString());
            }
            System.out.println();
        }
    }
}



