package views;

import controllers.CivilizationController;
import controllers.GameController;
import models.City;
import models.Civilization;
import models.Notification;
import models.Technology;
import models.map.CivilizationMap;
import models.tile.Tile;
import models.tile.VisibleTile;
import models.unit.*;

import java.util.Scanner;

public class GameMenu extends Menu {
    private final static int VIEW_MAP_WIDTH = 9;
    private final static int VIEW_MAP_HEIGHT = 7;

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
        // TODO: 4/21/2022
    }


    private static void handleCityInfo(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void showMap() {
        // TODO
        Civilization civilization = GameController.getInstance().getCivilization();
        System.out.printf("%s : ", civilization.getCivilizationName());
        System.out.printf("Turn %d\n", civilization.getTurn());

        CivilizationMap map = civilization.getPersonalMap();

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
                     /            \            /            \
        3           /              \__________/              \
                    \              /          \              /
                     \            /            \            /
        6             \__________/              \__________/
                      /          \              /          \
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
                VisibleTile tile = map.getTileByXY(x, y);

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

                for (int k = leftBound + 3; k < leftBound + 13; k++) {
                    output[upperBound][k].replace(0, 1, "_");
                }
                output[upperBound + 1][leftBound + 2].replace(0, 1, "/");
                output[upperBound + 2][leftBound + 1].replace(0, 1, "/");
                output[upperBound + 3][leftBound + 0].replace(0, 1, "/");
                output[upperBound + 4][leftBound + 0].replace(0, 1, "\\");
                output[upperBound + 5][leftBound + 1].replace(0, 1, "\\");
                output[upperBound + 6][leftBound + 2].replace(0, 1, "\\");

                output[upperBound + 1][leftBound + 13].replace(0, 1, "\\");
                output[upperBound + 2][leftBound + 14].replace(0, 1, "\\");
                output[upperBound + 3][leftBound + 15].replace(0, 1, "\\");
                output[upperBound + 4][leftBound + 15].replace(0, 1, "/");
                output[upperBound + 5][leftBound + 14].replace(0, 1, "/");
                output[upperBound + 6][leftBound + 13].replace(0, 1, "/");
                for (int k = leftBound + 3; k < leftBound + 13; k++) {
                    output[upperBound + 6][k].replace(0, 1, "_");
                }

                // TODO : handle rivers

                // TODO : complete, print;
            }
        }
    }
}
