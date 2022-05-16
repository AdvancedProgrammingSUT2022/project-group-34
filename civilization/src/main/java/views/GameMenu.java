package views;

import controllers.CheatController;
import controllers.CivilizationController;
import controllers.GameController;
import models.*;
import models.map.CivilizationMap;
import models.map.GameMap;
import models.resource.BonusResource;
import models.resource.LuxuryResource;
import models.resource.Resource;
import models.resource.StrategicResource;
import models.tile.*;
import models.unit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

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
    private static int mapX = 0;
    private static int mapY = 0;


    //Processes commands related with main menu
    static void processOneCommand() {
        Processor processor;

        while (getCurrentMenu().equals("game")) {
            showMap();
            processor = new Processor(getInput());

            if (!processor.isValid() || processor.getCategory() == null) invalidCommand();
            else if (processor.getCategory().equals("select")) handleSelectCategoryCommand(processor);
            else if (processor.getCategory().equals("unit")) handleUnitCategoryCommand(processor);
            else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor);
            else if (processor.getCategory().equals("info")) handleInfoCategoryCommand(processor);
            else if (processor.getCategory().equals("city")) handleCityCategoryCommand(processor);
            else if (processor.getCategory().equals("cheat")) handleCheatCategoryCommand(processor);
            else if (processor.getCategory().equals("end")) handleEndCategoryCommand(processor);
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
    11.unit attack city --x <x> --y <y>
    unit attack combat --x <x> --y <y>
    unit attack noncombat --x <x> --y <y>
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
        String x = processor.get("x");
        String y = processor.get("y");
        if (processor.getSubSection() == null ||
                (!processor.getSubSection().equals("city") && !processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")) ||
                processor.getNumberOfFields() != 2 ||
                x == null || y == null)
            invalidCommand();
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else if (!CivilizationController.getInstance().isPositionValid(new int[]{Integer.parseInt(x), Integer.parseInt(y)}))
            System.out.println("Invalid coordinates!");
        else if (!selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            System.out.println("Unit is in a multiple-turn movement");
        else {
            int[] position = new int[]{Integer.parseInt(x), Integer.parseInt(y)};
            if (processor.getSubSection().equals("city")) {
                selectedCombatUnit.makeUnitAwake();
                attackCity(position);
            } else {
                // TODO: Not phase1
                System.out.println("Not this phase!");
            }
            selectedCombatUnit = null;
        }
    }

    private static void attackCity(int[] position) {
        Tile tile = CivilizationController.getInstance().getTileByPosition(position);
        if (tile.getCity() == null)
            System.out.println("There is no city in that place");
        else if (selectedCombatUnit.getCombatType().equals("Archery") ||
                selectedCombatUnit.getCombatType().equals("Siege") ||
                selectedCombatUnit.getName().equals("ChariotArcher")) {
            int distance = CivilizationController.getInstance().doBFSAndReturnDistances(selectedCity.getPosition(), true).get(tile);

            if (selectedCombatUnit.getCombatType().equals("Siege") && !((Archer) selectedCombatUnit).isSetup)
                System.out.println("Siege unit should be set up first");
            else if (distance > selectedCombatUnit.getRange())
                System.out.println("City is out of range");
            else {
                City city = tile.getCity();
                int cityHealth = city.getHitPoint();
                if (city.isGarrison()) cityHealth += cityHealth / 4;
                // TODO: city terrain

                float hitPointAffect = (float) (10 - selectedCombatUnit.getHitPoint()) / 20;
                int unitStrength = selectedCombatUnit.getCombatStrength() - (int) (selectedCombatUnit.getCombatStrength() * hitPointAffect);

                cityHealth -= unitStrength;
                city.setHitPoint(Math.max(cityHealth, 1));
                System.out.println("City HP:" + city.getHitPoint());
            }
        } else {
            int distance = CivilizationController.getInstance().doBFSAndReturnDistances(selectedCity.getPosition(), true).get(tile);

            if (!selectedCombatUnit.getPosition().getAdjacentTiles().contains(tile))
                System.out.println("Unit is far from the city to attack");
            else {
                City city = tile.getCity();
                int cityHealth = city.getHitPoint();
                if (city.isGarrison()) cityHealth += cityHealth / 4;
                // TODO: city terrain

                float hitPointAffect = (float) (10 - selectedCombatUnit.getHitPoint()) / 20;
                int unitStrength = selectedCombatUnit.getCombatStrength() - (int) (selectedCombatUnit.getCombatStrength() * hitPointAffect);

                int unitHealth = selectedCombatUnit.getHitPoint() - city.getStrength();

                if (unitHealth == 0) {
                    System.out.println("Your unit get destroyed");
                } else {
                    selectedCombatUnit.setHitPoint(unitHealth);
                    System.out.println("Unit HP:" + unitHealth);
                }

                cityHealth -= unitStrength;
                if (cityHealth == 0 && unitHealth != 0)
                    ;// TODO: destroy, ...
                else {
                    city.setHitPoint(cityHealth);
                    System.out.println("City HP:" + cityHealth);
                }
            }
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

        System.out.println("Current Research Project: " + technology.getName());
        System.out.println("Remaining Terms: " + technology.getRemainingTerm());

        futureTechnologies(technology);
        futureImprovements(technology);
        futureUnits(technology);
        futureResources(technology);
    }

    private static void futureTechnologies(Technology technology) {
        ArrayList<String> technologies = new ArrayList<>();
        for (TechnologyEnum value : TechnologyEnum.values())
            if (value.getPrerequisiteTechnologies().contains(technology.getName()))
                technologies.add(technology.getName());

        if (technologies.size() == 0)
            System.out.println("No new technology");
        else {
            System.out.println("New Technologies:");
            for (String tech : technologies)
                System.out.println(tech);
        }
    }

    private static void futureImprovements(Technology technology) {
        ArrayList<String> improvements = new ArrayList<>();
        for (ImprovementEnum value : ImprovementEnum.values())
            if (value.requiredTechnology.equals(technology))
                improvements.add(technology.getName());

        if (improvements.size() == 0)
            System.out.println("No new improvement");
        else {
            System.out.println("New Improvements:");
            for (String improvement : improvements)
                System.out.println(improvement);
        }
    }

    private static void futureUnits(Technology technology) {
        ArrayList<String> units = new ArrayList<>();
        for (UnitEnum value : UnitEnum.values())
            if (value.getRequiredTechnology().getName().equals(technology.getName()))
                units.add(technology.getName());

        if (units.size() == 0)
            System.out.println("No new unit");
        else {
            System.out.println("New Units:");
            for (String unit : units)
                System.out.println(unit);
        }
    }

    private static void futureResources(Technology technology) {
        String resourceName = null;
        if (technology.getName().equals(TechnologyEnum.ScientificTheory.getName()))
            resourceName = "Coal";
        else if (technology.getName().equals(TechnologyEnum.AnimalHusbandry.getName()))
            resourceName = "Horse";
        else if (technology.getName().equals(TechnologyEnum.IronWorking.getName()))
            resourceName = "Iron";

        if (resourceName == null)
            System.out.println("No new resource");
        else {
            System.out.println("New resources:");
            System.out.println(resourceName);
        }
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
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            selectedCity = civilization.getCities().get(Integer.parseInt(choice) - 1);
            cityScreen();
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
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            selectedCity = civilization.getCities().get(Integer.parseInt(choice) - 1);
            cityScreen();
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


    /*Handles commands that start with "city":
    1.city screen
    2.city output
    3.city lock citizens
    4.city remove citizens
    5.city buy tile
    6.city construction
    7.city attack combat --x <x> --y <y>
    city attack noncombat --x <x> --y <y>
     */
    private static void handleCityCategoryCommand(Processor processor) {
        if (selectedCity == null)
            System.out.println("Please select a city first");
        else if (processor.getSection() == null)
            invalidCommand();
        else if (processor.getSection().equals("screen"))
            cityScreen();
        else if (processor.getSection().equals("output"))
            cityOutput();
        else if (processor.getSection().equals("lock"))
            unemployedCitizenSection(processor);
        else if (processor.getSection().equals("remove"))
            removeCitizensFromWork(processor);
        else if (processor.getSection().equals("buy"))
            buyTile(processor);
        else if (processor.getSection().equals("construction"))
            constructionMenu();
        else if (processor.getSection().equals("attack"))
            ;// TODO: 5/16/2022
        else
            invalidCommand();
    }

    private static void cityScreen() {
        System.out.print(selectedCity.getName());
        if (GameController.getInstance().getCivilization().getCurrentCapital().equals(selectedCity))
            System.out.format("(Capital)| Strength:%d\n", selectedCity.getStrength());
        else
            System.out.format("|Strength:%d\n", selectedCity.getStrength());

        System.out.format("Food:%d| Production Rate:%d| Science Rate:%d| Gold Rate:%d\n",
                selectedCity.getFood(), selectedCity.getProductionRate(), selectedCity.getScienceRate(), selectedCity.getGoldRate());
        System.out.format("Population:%d| Turn's until new citizen:%d\n", selectedCity.getCitizens().size(), selectedCity.getTillNewCitizen());
        System.out.println("Turn's until new production:" + selectedCity.getUnitUnderProductTern());
        System.out.println("List of City's citizens:");
        for (int i = 1; i <= selectedCity.getCitizens().size(); i++) {
            Citizen citizen;
            if ((citizen = selectedCity.getCitizens().get(i - 1)).isWorking())
                System.out.println(i + ".(" + citizen.getWorkPosition().getX() + ", " + citizen.getWorkPosition().getY() + ")");
            else
                System.out.println("Idle");
        }

        for (Tile tile : selectedCity.getTerritory())
            System.out.print("(" + tile.getX() + ", " + tile.getY() + ") ");

        City city = navigateBetweenCities();

        if (city != null) {
            selectedCity = city;
            cityScreen();
        }
    }

    private static City navigateBetweenCities() {
        ArrayList<City> cities = new ArrayList<>(GameController.getInstance().getCivilization().getCities());
        cities.remove(selectedCity);

        System.out.println("-------------------------------------------------------------");
        System.out.println("If you want to enter a city's screen, please type its index");
        System.out.println("If you want to exit this screen, please type \"exit\"");
        for (int i = 1; i <= cities.size(); i++)
            System.out.println(i + "." + cities.get(i - 1));

        String choice;
        while (true) {
            choice = getInput();
            if (choice.equals("exit")) break;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > cities.size()) System.out.println("Invalid number");
                else break;
            } else invalidCommand();
        }

        if (choice.equals("exit")) return null;
        else return cities.get(Integer.parseInt(choice) - 1);
    }


    private static void cityOutput() {
        System.out.println(selectedCity.getName() + "'s output:");
        System.out.println("Production Rate:" + selectedCity.getProductionRate());
        System.out.println("Science Rate:" + selectedCity.getScienceRate());
        System.out.println("Gold Rate:" + selectedCity.getGoldRate());
        System.out.println("Food Rate:" + selectedCity.getFoodRate());
        System.out.println("Turns till new citizens:" + selectedCity.getTillNewCitizen());
    }


    private static void unemployedCitizenSection(Processor processor) {
        if (processor.getSubSection() == null || !processor.getSubSection().equals("citizens"))
            invalidCommand();
        else {
            ArrayList<Citizen> citizens = new ArrayList<>(selectedCity.getCitizens());

            citizens.removeIf(Citizen::isWorking);

            if (citizens.size() == 0) System.out.println("There is not unemployed citizen in this city");
            else lockCitizensToTiles(citizens);
        }
    }

    private static void lockCitizensToTiles(ArrayList<Citizen> citizens) {
        System.out.println("There is " + citizens.size() + " unemployed citizens in this city");
        System.out.println("If you want to lock a citizen to a tile, please type tiles coordinates like \"x y\"");
        System.out.println("If you want to exit this menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();

            if (choice.equals("exit")) return;
            else if (choice.matches("\\d+ \\d+")) {
                int[] position = {Integer.parseInt(choice.split(" ")[0]), Integer.parseInt(choice.split(" ")[1])};
                if (!CivilizationController.getInstance().isPositionValid(position))
                    System.out.println("Invalid coordinates");
                else if (!selectedCity.getTerritory().contains(CivilizationController.getInstance().getTileByPosition(position)))
                    System.out.println("This tile is not in this city");
                else {
                    citizens.get(0).setWorking(true);
                    citizens.get(0).setWorkPosition(CivilizationController.getInstance().getTileByPosition(position));
                    citizens.remove(citizens.get(0));
                    System.out.println("A citizen has been locked to tile (" + position[0] + ", " + position[1] + ")");
                    if (citizens.size() == 0) {
                        System.out.println("There is not any other unemployed citizen in this city");
                        return;
                    }
                    System.out.println("There is " + citizens.size() + " unemployed citizens in this city");
                    System.out.println("If you want to lock a citizen to a tile, please type tiles coordinates like \"x y\"");
                    System.out.println("If you want to exit this menu, please type \"exit\"");
                }
            } else invalidCommand();
        }
    }


    private static void removeCitizensFromWork(Processor processor) {
        if (processor.getSubSection() == null || !processor.getSubSection().equals("citizens"))
            invalidCommand();
        else {
            ArrayList<Citizen> citizens = new ArrayList<>(selectedCity.getCitizens());

            citizens.removeIf(citizen -> !citizen.isWorking());

            if (citizens.size() == 0) System.out.println("There is no employed citizens in this city");
            else getCitizenToRemove(citizens);
        }
    }

    private static void getCitizenToRemove(ArrayList<Citizen> citizens) {
        System.out.println("There is " + citizens.size() + " employed citizens in this city");
        for (int i = 1; i <= citizens.size(); i++)
            System.out.println(i + ".(" + citizens.get(i - 1).getWorkPosition().getX() +
                    ", " + citizens.get(i - 1).getWorkPosition().getY() + ")");
        System.out.println("If you want to remove a citizen from a tile, please type its index");
        System.out.println("If you want to exit this menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();

            if (choice.equals("exit")) return;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > citizens.size())
                    System.out.println("Invalid number");
                else {
                    citizens.get(number - 1).setWorking(false);
                    System.out.println("Selected citizen get removed from work");
                    return;
                }
            } else invalidCommand();
        }
    }


    private static void buyTile(Processor processor) {
        if (processor.getSubSection() == null || !processor.getSubSection().equals("tile"))
            invalidCommand();
        else if (GameController.getInstance().getCivilization().getGold() < 50)
            System.out.println("You don't have enough money to buy a tile");
        else {
            ArrayList<Tile> purchasableTiles = new ArrayList<>();

            for (Tile tile : selectedCity.getTerritory()) {
                ArrayList<Tile> adjacentTiles = tile.getAdjacentTiles();
                for (Tile adjacentTile : adjacentTiles)
                    if (adjacentTile.getCity() == null) purchasableTiles.add(adjacentTile);
            }
            if (purchasableTiles.size() == 0) System.out.println("There is not tile to buy");
            else chooseTileToBuy(purchasableTiles);
        }
    }

    private static void chooseTileToBuy(ArrayList<Tile> purchasableTiles) {
        System.out.println("Purchasable tiles:");
        for (int i = 1; i <= purchasableTiles.size(); i++)
            System.out.println(i + ".(" + purchasableTiles.get(i - 1).getX() + ", " + purchasableTiles.get(i - 1).getY() + ")");

        System.out.println("If you want to buy a tile, please type its index");
        System.out.println("If you want to exit this menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();

            if (choice.equals("exit")) return;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > purchasableTiles.size())
                    System.out.println("Invalid number");
                else {
                    CivilizationController.getInstance().purchaseTile(selectedCity, purchasableTiles.get(number - 1));
                    System.out.println("The selected tile is added to your territory");
                    return;
                }
            } else invalidCommand();
        }
    }


    private static void constructionMenu() {
        ArrayList<Unit> units = CivilizationController.getInstance().getProducibleUnits();

        System.out.println("List of producible units:");
        for (int i = 1; i <= units.size(); i++)
            System.out.println(i + "." + units.get(i - 1) + "|" + (int) Math.ceil((float) units.get(i - 1).getCost() / selectedCity.getProductionRate()));
        System.out.println("If you want to choose/change a production, please type its index");
        System.out.println("If you want to exit the menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();
            if (choice.equals("exit")) return;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > units.size()) System.out.println("Invalid number");
                else {
                    selectedCity.setUnitUnderProduct(units.get(number - 1));
                    selectedCity.setUnitUnderProductTern((int) Math.ceil((float) units.get(number - 1).getCost() / selectedCity.getProductionRate()));
                }
            } else invalidCommand();
        }
    }


    private static void attckUnits(Processor processor){
        String x = processor.get("x");
        String y = processor.get("y");
        if (processor.getSubSection() == null ||
                (!processor.getSubSection().equals("city") && !processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")) ||
                processor.getNumberOfFields() != 2 ||
                x == null || y == null)
            invalidCommand();
    }


    private static void handleMapCategoryCommand(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void handleTurnCategory(Processor processor) {
        // TODO: 4/21/2022
    }

    private static void handleCheatCategoryCommand(Processor processor) {
        if (processor.getSection().equals("increase"))
            increaseCheatCommand(processor.get("amount"), processor.getSubSection());
        else if (processor.getSection().equals("teleport"))
            teleportCheatCommand(processor.get("x"), processor.get("y"));
        else if (processor.getSection().equals("finish")) finishCheatCommand(processor.getSubSection());
        else if (processor.getSection().equals("reveal")) revealCheatCommand(processor.get("x"), processor.get("y"));
        else invalidCommand();

    }

    private static void increaseCheatCommand(String amountField, String subSection) {
        if (amountField == null) {
            System.out.printf("field 'amount' required\n");
        } else if (!amountField.matches(NON_NEGATIVE_NUMBER_REGEX)) {
            System.out.printf("amount must be a valid non-negative integer\n");
            return;
        }
        int amount = Integer.parseInt(amountField);
        if (subSection.equals("gold")) {
            CheatController.getInstance().increaseGold(amount);
            System.out.printf("Done");
        } else if (subSection.equals("beaker")) {
            CheatController.getInstance().increaseBeaker(amount);
            System.out.printf("Done");
        } else invalidCommand();
    }

    private static void teleportCheatCommand(String xField, String yField) {
        Unit selectedUnit = selectedCombatUnit;
        if (selectedUnit == null) selectedUnit = selectedNonCombatUnit;
        if (selectedUnit == null) {
            System.out.printf("no unit selected\n");
            return;
        }
        if (xField == null || yField == null) System.out.printf("fields 'x' and 'y' required\n");
        else if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX))
            System.out.printf("x, y must be valid non-negative integers");
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
        else if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX))
            System.out.printf("x, y must be valid non-negative integers");
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
        Civilization civilization = GameController.getInstance().getCivilization();
        System.out.printf("%s : ", civilization.getCivilizationName());
        System.out.printf("Turn %d\n", civilization.getTurn());

        CivilizationMap personalMap = civilization.getPersonalMap();
        GameMap map = GameController.getInstance().getGame().getMainGameMap();

        int arrayHeight = VIEW_MAP_HEIGHT * 6 + 2;
        int arrayWidth = VIEW_MAP_WIDTH * 30;

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
                    if (tile.hasRoad()) putRoad(output, upperBound, leftBound);
                    if (tile.hasRail()) putRail(output, upperBound, leftBound);
                    putImprovement(tile.getImprovementName(), output, upperBound, leftBound);
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

    private static void putRivers(Tile tile, StringBuilder[][] output, int upperBound, int leftBound, int x, int y) {
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
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 1, leftBound + 12);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 2, leftBound + 13);
            putColor(ANSI_BLUE_BACKGROUND, output, upperBound + 3, leftBound + 14);
        } else if (whichBorder == 2) {
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

    private static void putTerrain(Terrain terrain, StringBuilder[][] output, int upperBound, int leftBound) {
        String terrainName = terrain.getName().toString();
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
        int index = GameController.getInstance().getIndex(unit.getCivilization());
        String shownName;
        if (unit instanceof Settler) shownName = "S";
        else if (unit instanceof Worker) shownName = "W";
        else shownName = unit.getName().substring(0, 3);
        putString(shownName, output, upperBound + 4, upperBound + 6);
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

    private static void printMap(StringBuilder[][] output) {
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                if (output[i][j].length() == 0) continue;
                System.out.printf("%s", output[i][j].toString());
            }
            System.out.println();
        }
    }

    private static void handleEndCategoryCommand(Processor processor) {
        if (processor.getSection() == null) invalidCommand();
        else if (processor.getSection().equals("turn")) GameController.getInstance().startTurn();
        else invalidCommand();
    }
}