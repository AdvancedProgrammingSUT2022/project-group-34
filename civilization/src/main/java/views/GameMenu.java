package views;

import controllers.CivilizationController;
import controllers.GameController;
import models.City;
import models.Civilization;
import models.Notification;
import models.Technology;
import models.map.CivilizationMap;
import models.map.GameMap;
import models.tile.Feature;
import models.tile.Improvement;
import models.tile.Tile;
import models.tile.VisibleTile;
import models.unit.*;

import java.util.*;

public class GameMenu extends Menu {
    private final static int VIEW_MAP_WIDTH = 9;
    private final static int VIEW_MAP_HEIGHT = 7;

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
    1.unit moveto --x <x> --y <y>
    2.unit sleep
    3.unit alert
    4.unit fortify
    5.unit heal
    6.unit wake
    7.unit found city --name <name>
    8.unit garrison
    9.unit cancel
    10.unit setup
    11.unit attack
    12.unit delete
    13.unit build
    14.unit remove <jungle/forest/marsh>
    15.unit repair
    16.unit pillage*/
    private static void handleUnitCategoryCommand(Processor processor) {

        // TODO: Booleans in unit class (Specially combat units)
        if (selectedCombatUnit == null && selectedNonCombatUnit == null)
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
            buildCommand();
        else if (processor.getSection().equals("remove"))
            removeCommand(processor);
        else if (processor.getSection().equals("repair"))
            repairCommand();
        else
            invalidCommand();
    }

    private static void movetoCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        Unit unit = null;

        if (selectedNonCombatUnit != null) unit = selectedNonCombatUnit;
        else if (selectedCombatUnit != null) unit = selectedCombatUnit;

        if (x == null || y == null || processor.getNumberOfFields() != 2)
            invalidCommand();
        else if (!unit.getPosition().equals(unit.getDestination()))
            System.out.println("Unit is in a multiple-turn movement");
        else {

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
    }

    private static void sleepCommand() {
        Unit unit;
        if (selectedNonCombatUnit != null) unit = selectedNonCombatUnit;
        else unit = selectedCombatUnit;

        unit.makeUnitAwake();
        unit.setSleep(true);
        unit.setDestination(unit.getPosition());
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        System.out.println("Unit is sleep");
    }

    private static void alertCommand() {
        if (selectedCombatUnit == null) {
            System.out.println("Selected unit is not a military unit");
        } else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setAlert(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
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
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
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
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
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
            case "in  movement":
                System.out.println("Unit is in a multiple-turn movement");
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
        else if (!selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            System.out.println("Unit is in a multiple-turn movement");
        else {
            selectedCombatUnit.makeUnitAwake();
            ((Archer) selectedCombatUnit).setSetup(true);
            selectedCombatUnit = null;
            System.out.println("Unit is setup");
        }
    }

    private static void attackCommand(Processor processor) {
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else if (!selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            System.out.println("Unit is in a multiple-turn movement");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit = null;
            // TODO: 5/15/2022
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
            else if (!selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
                System.out.println("Unit is in a multiple-turn movement");
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
        else if (!selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            System.out.println("Unit is in a multiple-turn movement");
        else if (selectedCombatUnit.getPosition().getImprovementName() == null)
            System.out.println("There is no improvement in this tile");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.getPosition().setLooted(true);
            selectedCombatUnit = null;
            System.out.println("Tile is pillaged");
        }
    }

    private static void buildCommand() {
        if (!(selectedNonCombatUnit instanceof Worker))
            System.out.println("Selected unit is not a worker");
        else if (!selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
            System.out.println("Unit is in a multiple-turn movement");
        else {
            Tile tile = selectedNonCombatUnit.getPosition();
            ArrayList<String> improvements = CivilizationController.getInstance().getPossibleImprovements(tile);
            String choice = getBuildChoice(improvements);
            if (!choice.equals("exit")) {
                CivilizationController.getInstance().build((Worker) selectedNonCombatUnit, improvements.get(Integer.parseInt(choice) - 1));
                System.out.println("Building improvement started");
                selectedNonCombatUnit = null;
            }
        }
    }

    private static String getBuildChoice(ArrayList<String> improvements) {
        System.out.println("Please type improvement index you want to build");
        System.out.println("Type \"exit\" if you don't want to choose any improvement");

        for (int i = 0; i < improvements.size(); i++)
            System.out.println(i + 1 + "." + improvements.get(i));

        String command;
        while (true) {
            command = getInput();

            if (command.equals("exit")) return "exit";
            else if (command.matches("\\d+")) {
                int number = Integer.parseInt(command);
                if (number < 1 || number > improvements.size()) System.out.println("Invalid number!");
                else return command;
            } else invalidCommand();
        }
    }

    private static void removeCommand(Processor processor) {
        if (processor.getSubSection() == null)
            invalidCommand();
        else if (!processor.getSubSection().equals("jungle") ||
                !processor.getSubSection().equals("forest") ||
                !processor.getSubSection().equals("marsh"))
            invalidCommand();
        else {
            switch (CivilizationController.getInstance().removeFeature(selectedNonCombatUnit, processor.getSubSection())) {
                case "not worker":
                    System.out.println("Selected unit is not a worker");
                    break;
                case "in movement":
                    System.out.println("Unit is in a multiple-turn movement");
                    break;
                case "irremovable feature":
                    System.out.println("This tiles feature cannot get removed");
                    break;
                case "ok":
                    System.out.println("Removing feature started");
                    selectedNonCombatUnit = null;
                    break;
            }
        }
    }

    private static void repairCommand() {
        switch (CivilizationController.getInstance().repair(selectedNonCombatUnit)) {
            case "not worker":
                System.out.println("Selected unit is not a worker");
                break;
            case "in movement":
                System.out.println("Unit is in a multiple-turn movement");
                break;
            case "no improvement":
                System.out.println("There is no improvement in this tile to get repaired");
                break;
            case "ok":
                System.out.println("Repairing tile started");
                selectedNonCombatUnit = null;
                break;
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
            ;// TODO: Not Phase1
        else if (processor.getSection().equals("victory"))
            ;// TODO: Not Phase1
        else if (processor.getSection().equals("demographics"))
            demographicsInfoMenu();
        else if (processor.getSection().equals("notifications"))
            notificationsInfoMenu();
        else if (processor.getSection().equals("military"))
            militaryInfoMenu();
        else if (processor.getSection().equals("economic"))
            economicInfoMenu();
        else if (processor.getSection().equals("diplomatic"))
            ;// TODO: Not Phase1
        else if (processor.getSection().equals("deals"))
            ;// TODO: Not Phase1
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


    private static void diplomacyInfoMenu() {
        // TODO: Not Phase1
    }

    private static void victoryInfoMenu() {
        // TODO: Not Phase1
    }


    private static void demographicsInfoMenu() {
        ArrayList<Civilization> civilizations = GameController.getInstance().getGame().getCivilizations();

        System.out.println("Demographics Screen:");
        territoryRank(civilizations);
        populationRank(civilizations);
        goldRank(civilizations);
        happinessRank(civilizations);
        unitsRank(civilizations);
    }

    private static void territoryRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> territory = new ArrayList<>();
        for (Civilization civilization : civilizations)
            territory.add(civilization.getTerritory().size());

        Collections.sort(territory);

        int territorySize = GameController.getInstance().getCivilization().getTerritory().size();
        int rank = territory.indexOf(territorySize) + 1;
        int best = territory.get(0);
        int average = territory.get(territory.size() / 2);
        int worst = territory.get(territory.size() - 1);

        System.out.format("Territory: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", territorySize,
                best, average, worst, rank);
    }

    private static void populationRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> population = new ArrayList<>();
        for (Civilization civilization : civilizations)
            population.add(civilization.getPopulation());

        Collections.sort(population);

        int populationSize = GameController.getInstance().getCivilization().getPopulation();
        int rank = population.indexOf(populationSize) + 1;
        int best = population.get(0);
        int average = population.get(population.size() / 2);
        int worst = population.get(population.size() - 1);

        System.out.format("Population: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", populationSize,
                best, average, worst, rank);
    }

    private static void goldRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> gold = new ArrayList<>();
        for (Civilization civilization : civilizations)
            gold.add(civilization.getGold());

        Collections.sort(gold);

        int goldAmount = GameController.getInstance().getCivilization().getGold();
        int rank = gold.indexOf(goldAmount) + 1;
        int best = gold.get(0);
        int average = gold.get(gold.size() / 2);
        int worst = gold.get(gold.size() - 1);

        System.out.format("Wealth: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", goldAmount,
                best, average, worst, rank);
    }

    private static void happinessRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> happiness = new ArrayList<>();
        for (Civilization civilization : civilizations)
            happiness.add(civilization.getHappiness());

        Collections.sort(happiness);

        int happinessAmount = GameController.getInstance().getCivilization().getHappiness();
        int rank = happiness.indexOf(happinessAmount) + 1;
        int best = happiness.get(0);
        int average = happiness.get(happiness.size() / 2);
        int worst = happiness.get(happiness.size() - 1);

        System.out.format("Happiness: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", happinessAmount,
                best, average, worst, rank);
    }

    private static void unitsRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> units = new ArrayList<>();
        for (Civilization civilization : civilizations)
            units.add(civilization.getUnits().size());

        Collections.sort(units);

        int unitsSize = GameController.getInstance().getCivilization().getUnits().size();
        int rank = units.indexOf(unitsSize) + 1;
        int best = units.get(0);
        int average = units.get(units.size() / 2);
        int worst = units.get(units.size() - 1);

        System.out.format("Number of units: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", unitsSize,
                best, average, worst, rank);
    }


    private static void notificationsInfoMenu() {
        Civilization civilization = GameController.getInstance().getCivilization();

        StringBuilder output = new StringBuilder("Notifications Log:").append("\n");
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

    private static String getEconomicMenuChoice(Civilization civilization) {
        String choice;

        while (true) {
            choice = getInput();
            if (choice.equals("exit")) return choice;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);

                if (number < 1 || number > civilization.getCities().size()) System.out.println("Invalid number!");
                else return choice;
            } else
                invalidCommand();
        }
    }


    private static void diplomaticInfoMenu() {
        // TODO: Not Phase1
    }

    private static void dealsInfoMenu() {
        // TODO: Not Phase1
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
                } else {
                    if (mapY % 2 == 1) {
                        upperBound = 6 * (i + VIEW_MAP_HEIGHT) - 3;
                    } else {
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

                // TODO : Handle improvements, resources, fog of war, revealed; then print;

                int[] position = {x, y};

                if (CivilizationController.getInstance().isPositionValid(position)) {
                    if (tile != null) {
                        if (!civilization.isInFog(tile)) {
                            if (tile.getCivilization() != null) {
                                int index = GameController.getInstance().getIndex(civilization);
                                output[upperBound + 2][leftBound + 8].replace(0, 1, String.valueOf((char) ('A' + index)));
                                String colorCode = ANSI_COLOR[index % 7 + 1];
                                output[upperBound + 2][leftBound + 8].insert(0, colorCode);
                                output[upperBound + 2][leftBound + 8].insert(output[upperBound + 2][leftBound + 8].length(), ANSI_RESET);
                            }

                            String terrainName = tile.getTerrain().name.toString();
                            output[upperBound + 1][leftBound + 5].replace(0, 1, String.valueOf(terrainName.charAt(0)));
                            String colorCode = ANSI_WHITE;
                            if (terrainName.equals("Desert")) colorCode = ANSI_YELLOW;
                            else if (terrainName.equals("Grasslands")) colorCode = ANSI_GREEN;
                            else if (terrainName.equals("Ocean")) colorCode = ANSI_BLUE;
                            output[upperBound + 1][leftBound + 5].insert(0, colorCode);
                            output[upperBound + 1][leftBound + 5].insert(output[upperBound + 1][leftBound + 5].length(), ANSI_RESET);

                            if (tile.getFeature() != null) {
                                String featureName = tile.getFeature().name.toString();
                                output[upperBound + 1][leftBound + 7].replace(0, 1, String.valueOf(terrainName.charAt(0)));
                                output[upperBound + 1][leftBound + 8].replace(0, 1, String.valueOf(terrainName.charAt(1)));
                                colorCode = ANSI_WHITE;
                                if (terrainName.equals("FloodPlane")) colorCode = ANSI_GREEN;
                                else if (terrainName.equals("Forests")) colorCode = ANSI_GREEN;
                                else if (terrainName.equals("Jungle")) colorCode = ANSI_GREEN;
                                else if (terrainName.equals("Marsh")) colorCode = ANSI_YELLOW;
                                else if (terrainName.equals("Oasis")) colorCode = ANSI_YELLOW;
                                output[upperBound + 1][leftBound + 7].insert(0, colorCode);
                                output[upperBound + 1][leftBound + 8].insert(0, colorCode);
                                output[upperBound + 1][leftBound + 7].insert(output[upperBound + 1][leftBound + 7].length(), ANSI_RESET);
                                output[upperBound + 1][leftBound + 8].insert(output[upperBound + 1][leftBound + 8].length(), ANSI_RESET);
                            }

                            if (civilization.isTransparent(tile)) {
                                if (tile.getCombatUnit() != null) {
                                    Unit unit = tile.getCombatUnit();
                                    int index = GameController.getInstance().getIndex(unit.getCivilization());
                                    output[upperBound + 4][leftBound + 8].replace(0, 1, String.valueOf(unit.getName().charAt(0)));
                                    output[upperBound + 4][leftBound + 9].replace(0, 1, String.valueOf(unit.getName().charAt(1)));
                                    output[upperBound + 4][leftBound + 10].replace(0, 1, String.valueOf(unit.getName().charAt(2)));
                                    colorCode = ANSI_COLOR[index % 7 + 1];
                                    output[upperBound + 4][leftBound + 8].insert(0, colorCode);
                                    output[upperBound + 4][leftBound + 9].insert(0, colorCode);
                                    output[upperBound + 4][leftBound + 10].insert(0, colorCode);
                                    output[upperBound + 4][leftBound + 8].insert(output[upperBound + 2][leftBound + 8].length(), ANSI_RESET);
                                    output[upperBound + 4][leftBound + 9].insert(output[upperBound + 2][leftBound + 9].length(), ANSI_RESET);
                                    output[upperBound + 4][leftBound + 10].insert(output[upperBound + 2][leftBound + 9].length(), ANSI_RESET);
                                }
                                if (tile.getNonCombatUnit() != null) {
                                    Unit unit = tile.getNonCombatUnit();
                                    int index = GameController.getInstance().getIndex(unit.getCivilization());
                                    if (unit instanceof Settler)
                                        output[upperBound + 4][leftBound + 6].replace(0, 1, "S");
                                    else output[upperBound + 4][leftBound + 6].replace(0, 1, "W");
                                    colorCode = ANSI_COLOR[index % 7 + 1];
                                    output[upperBound + 4][leftBound + 6].insert(0, colorCode);
                                    output[upperBound + 4][leftBound + 6].insert(output[upperBound + 2][leftBound + 8].length(), ANSI_RESET);
                                }
                            }
                        } else {
                            output[upperBound + 2][leftBound + 6].replace(0, 1, "F");
                            output[upperBound + 2][leftBound + 7].replace(0, 1, "O");
                            output[upperBound + 2][leftBound + 8].replace(0, 1, "G");
                            String colorCode = ANSI_PURPLE;
                            Random random = new Random(upperBound + leftBound + x + y);
                            for (int k = leftBound + 3; k <= leftBound + 12; k++) {
                                if (output[upperBound + 1][k].charAt(0) != ' ') continue;
                                output[upperBound + 1][k] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
                            }
                            for (int k = leftBound + 2; k <= leftBound + 13; k++) {
                                if (output[upperBound + 2][k].charAt(0) != ' ') continue;
                                output[upperBound + 2][k] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
                            }
                            for (int k = leftBound + 1; k <= leftBound + 14; k++) {
                                if (output[upperBound + 3][k].charAt(0) != ' ') continue;
                                output[upperBound + 3][k] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
                            }
                            for (int k = leftBound + 1; k <= leftBound + 14; k++) {
                                if (output[upperBound + 4][k].charAt(0) != ' ') continue;
                                output[upperBound + 4][k] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
                            }
                            for (int k = leftBound + 2; k <= leftBound + 13; k++) {
                                if (output[upperBound + 5][k].charAt(0) != ' ') continue;
                                output[upperBound + 5][k] = new StringBuilder(colorCode + CHARACTER_SEED[random.nextInt(CHARACTER_SEED.length)] + ANSI_RESET);
                            }
                        }
                    } else {
                        String backgroundCode = ANSI_RED_BACKGROUND;
                        for (int k = leftBound + 3; k <= leftBound + 12; k++) {
                            output[upperBound + 1][k].insert(0, backgroundCode);
                            output[upperBound + 1][k].insert(output[upperBound + 1][k].length(), ANSI_RESET);
                        }
                        for (int k = leftBound + 2; k <= leftBound + 13; k++) {
                            output[upperBound + 2][k].insert(0, backgroundCode);
                            output[upperBound + 2][k].insert(output[upperBound + 2][k].length(), ANSI_RESET);
                        }
                        for (int k = leftBound + 1; k <= leftBound + 14; k++) {
                            output[upperBound + 3][k].insert(0, backgroundCode);
                            output[upperBound + 3][k].insert(output[upperBound + 3][k].length(), ANSI_RESET);
                        }
                        for (int k = leftBound + 1; k <= leftBound + 14; k++) {
                            output[upperBound + 4][k].insert(0, backgroundCode);
                            output[upperBound + 4][k].insert(output[upperBound + 4][k].length(), ANSI_RESET);
                        }
                        for (int k = leftBound + 2; k <= leftBound + 13; k++) {
                            output[upperBound + 5][k].insert(0, backgroundCode);
                            output[upperBound + 5][k].insert(output[upperBound + 5][k].length(), ANSI_RESET);
                        }
                    }

                    output[upperBound + 3][leftBound + 5].replace(0, 1, String.valueOf(x / 10));
                    output[upperBound + 3][leftBound + 6].replace(0, 1, String.valueOf(x % 10));
                    output[upperBound + 3][leftBound + 7].replace(0, 1, ",");
                    output[upperBound + 3][leftBound + 8].replace(0, 1, String.valueOf(y / 10));
                    output[upperBound + 3][leftBound + 9].replace(0, 1, String.valueOf(y % 10));
                }


            }
        }
    }
}


