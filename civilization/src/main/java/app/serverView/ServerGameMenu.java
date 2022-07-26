package app.serverView;

import app.controllers.MainServer;
import app.controllers.gameServer.CivilizationController;
import app.controllers.singletonController.GMini;
import app.models.*;
import app.models.connection.Message;
import app.models.connection.Processor;
import app.models.tile.ImprovementEnum;
import app.models.tile.Tile;
import app.models.unit.*;
import com.google.gson.Gson;

import java.util.*;

public class ServerGameMenu extends ServerMenu {

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

    private CombatUnit selectedCombatUnit = null;
    private NonCombatUnit selectedNonCombatUnit = null;
    private City selectedCity = null;
    private int mapX = 0;
    private int mapY = 0;

    ServerGameMenu(MySocketHandler mySocketHandler) {
        super("game", mySocketHandler);
    }

    public void processOneProcessor(Processor processor) {
        message = new Message();
        if (!processor.isValid() || processor.getCategory() == null) message.addLine(getInvalidCommand());
        else if (processor.getCategory().equals("select")) handleSelectCategoryCommand(processor);
        else if (processor.getCategory().equals("unit")) handleUnitCategoryCommand(processor);
        else if (processor.getCategory().equals("menu")) handleMenuCategoryCommand(processor, message);
        else if (processor.getCategory().equals("info")) handleInfoCategoryCommand(processor);
        else if (processor.getCategory().equals("city")) handleCityCategoryCommand(processor);
        else if (processor.getCategory().equals("cheat")) handleCheatCategoryCommand(processor);
        else if (processor.getCategory().equals("map")) handleMapCategoryCommand(processor);
        else if (processor.getCategory().equals("end")) handleEndCategoryCommand(processor);
        else message.addLine(getInvalidCommand());
        mySocketHandler.sendMessage(message);
    }


    private String getInput() {
        message.addData("input","");
        sendMessage(message);
        message.clearMessageAndData();
        return mySocketHandler.listen();//todo
    }

    /*Handles commands that start with "select unit":
    1.select unit combat --x <x> --y <y>
    2.select unit noncombat --x <x> --y <y>
    3.select city --name <name>
    4.select city --x <x> --y <y>*/

    private void handleSelectCategoryCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        String name = processor.get("nickname");
        if (name == null) name = processor.get("name");

        if (processor.getSection() == null)
            message.addLine(getInvalidCommand());
        else if (processor.getSection().equals("unit")) {
            if (x == null || y == null ||
                    processor.getNumberOfFields() != 2 ||
                    processor.getSubSection() == null ||
                    (!processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")))
                message.addLine(getInvalidCommand());
            else selectUnit(new int[]{Integer.parseInt(x), Integer.parseInt(y)}, processor.getSubSection());
        } else if (processor.getSection().equals("city")) {
            if (processor.getSubSection() != null) message.addLine(getInvalidCommand());
            else if (x != null) {
                if (y == null || processor.getNumberOfFields() != 2) message.addLine(getInvalidCommand());
                else selectCity(new int[]{Integer.parseInt(x), Integer.parseInt(y)});
            } else if (name != null) {
                if (processor.getNumberOfFields() != 1) message.addLine(getInvalidCommand());
                else selectCity(name);
            } else message.addLine(getInvalidCommand());
        } else
            message.addLine(getInvalidCommand());
    }

    private void selectUnit(int[] position, String unitType) {
        switch (CivilizationController.getInstance().selectUnit(position, unitType)) {
            case "invalid position":
                message.addLine("Position is invalid");
                break;
            case "no such unit":
                message.addLine("No " + unitType.substring(0, 1).toUpperCase() + unitType.substring(1) +
                        " unit exists in position[" + position[0] + " " + position[1] + "]");
                break;
            case "not yours":
                message.addLine("This unit is not yours");
                break;
            case "combat":
                selectedCombatUnit = MainServer.getCivilizationControllerByToken(mySocketHandler.getGameToken()).getCombatUnitByPosition(position);
                selectedNonCombatUnit = null;
                selectedCity = null;
                message.addLine("Combat unit selected");
                break;
            case "noncombat":
                selectedNonCombatUnit = CivilizationController.getInstance().getNonCombatUnitByPosition(position);
                selectedCombatUnit = null;
                selectedCity = null;
                message.addLine("Noncombat unit selected");
                break;
        }
    }

    private void selectCity(int[] position) {
        Tile tile = CivilizationController.getInstance().getTileByPosition(position);
        City city = tile.getCity();

        if (tile == null)
            message.addLine("Position is invalid");
        else if (tile.getCity() == null)
            message.addLine("There is no city in the selected area");
        else if (!MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getCities().contains(city))
            message.addLine("This city is not in your territory");
        else {
            selectedCity = city;
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            message.addLine(String.format("%s city is selected\n", city.getName()));
        }
    }

    private void selectCity(String name) {
        City city;

        for (Civilization civilization : MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getGame().getCivilizations()) {
            if ((city = civilization.getCityByName(name)) != null) {
                if (civilization == MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization()) {
                    selectedCity = city;
                    selectedCombatUnit = null;
                    selectedNonCombatUnit = null;
                    message.addLine(String.format("%s city is selected\n", city.getName()));
                } else message.addLine("This city is not in your territory");

                return;
            }
        }
        message.addLine(String.format("There is no city named %s\n", name));
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

    private void handleUnitCategoryCommand(Processor processor) {

        // TODO: Booleans in unit class (Specially combat units)
        if (selectedCombatUnit == null && selectedNonCombatUnit == null)
            message.addLine("Please select a unit first");
        else if (processor.getSection() == null)
            message.addLine(getInvalidCommand());
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
            message.addLine(getInvalidCommand());
    }

    private void movetoCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        Unit unit = null;

        if (selectedNonCombatUnit != null) unit = selectedNonCombatUnit;
        else if (selectedCombatUnit != null) unit = selectedCombatUnit;

        if (x == null || y == null || processor.getNumberOfFields() != 2)
            message.addLine(getInvalidCommand());
        else if (unit.getDestination() != null && !unit.getPosition().equals(unit.getDestination()))
            message.addLine("Unit is in a multiple-turn movement");
        else {

            int[] position = new int[]{Integer.parseInt(x), Integer.parseInt(y)};
            String response = CivilizationController.getInstance().moveUnit(unit, position);

            switch (response) {
                case "invalid destination":
                    message.addLine("Destination is not valid");
                    break;
                case "fog of war":
                    message.addLine("Destination is in fog of war");
                    break;
                case "already at the same tile":
                    message.addLine("We already are at the tile you want to move to");
                    break;
                case "destination occupied":
                    message.addLine("There is already a unit in the tile you want to move to");
                    break;
                case "no valid path":
                    message.addLine("There is no path to the tile you want to move to");
                    break;
                case "success":
                    unit.makeUnitAwake();
                    message.addLine("Unit moved to destination successfully");
                    selectedCombatUnit = null;
                    selectedNonCombatUnit = null;
                    break;
            }
        }
    }

    private void sleepCommand() {
        Unit unit;
        if (selectedNonCombatUnit != null) unit = selectedNonCombatUnit;
        else unit = selectedCombatUnit;

        unit.makeUnitAwake();
        unit.setSleep(true);
        unit.setDestination(unit.getPosition());
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        message.addLine("Unit is sleep");
    }

    private void alertCommand() {
        if (selectedCombatUnit == null) {
            message.addLine("Selected unit is not a military unit");
        } else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setAlert(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
            selectedCombatUnit = null;
            message.addLine("Unit is alert");
        }
    }

    private void fortifyCommand() {
        if (selectedCombatUnit == null)
            message.addLine("Selected unit is not a military unit");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setFortify(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
            selectedCombatUnit = null;
            message.addLine("Unit is fortified");
        }
    }

    private void healCommand() {
        if (selectedCombatUnit == null)
            message.addLine("Selected unit is not a military unit");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.setFortifyUntilHealed(true);
            selectedCombatUnit.setDestination(selectedCombatUnit.getPosition());
            selectedCombatUnit = null;
            message.addLine("Unit is healed");
        }
    }

    private void garrisonCommand() {
        switch (CivilizationController.getInstance().garrisonCity(selectedCombatUnit)) {
            case "not military":
                message.addLine("Selected unit is not a military unit");
                break;
            case "no city":
                message.addLine("There is no city in this tile");
                break;
            case "in  movement":
                message.addLine("Unit is in a multiple-turn movement");
                break;
            case "ok":
                selectedCombatUnit = null;
                message.addLine("City is garrisoned");
                break;
        }
    }

    private void setupCommand() {
        if (!(selectedCombatUnit instanceof Archer) || !((Archer) selectedCombatUnit).isSiegeTool)
            message.addLine("Selected unit is not a siege tool unit");
        else if (selectedCombatUnit.getDestination() != null && !selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            message.addLine("Unit is in a multiple-turn movement");
        else {
            selectedCombatUnit.makeUnitAwake();
            ((Archer) selectedCombatUnit).setSetup(true);
            selectedCombatUnit = null;
            message.addLine("Unit is setup");
        }
    }

    private void attackCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        if (processor.getSubSection() == null ||
                (!processor.getSubSection().equals("city") && !processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")) ||
                processor.getNumberOfFields() != 2 ||
                x == null || y == null)
            message.addLine(getInvalidCommand());
        if (selectedCombatUnit == null)
            message.addLine("Selected unit is not a military unit");
        else if (!CivilizationController.getInstance().isPositionValid(new int[]{Integer.parseInt(x), Integer.parseInt(y)}))
            message.addLine("Invalid coordinates!");
        else if (selectedCombatUnit.getDestination() != null && !selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            message.addLine("Unit is in a multiple-turn movement");
        else {
            int[] position = new int[]{Integer.parseInt(x), Integer.parseInt(y)};
            if (processor.getSubSection().equals("city")) {
                selectedCombatUnit.makeUnitAwake();
                attackCity(position);
            } else {
                // TODO: Not phase1
                message.addLine("Not this phase!");
            }
            selectedCombatUnit = null;
        }
    }

    private void attackCity(int[] position) {
        Tile tile = CivilizationController.getInstance().getTileByPosition(position);
        if (tile.getCity() == null)
            message.addLine("There is no city in that place");
        else if (selectedCombatUnit.getCombatType().equals("Archery") ||
                selectedCombatUnit.getCombatType().equals("Siege") ||
                selectedCombatUnit.getName().equals("ChariotArcher")) {
            int distance = CivilizationController.getInstance().doBFSAndReturnDistances(selectedCity.getPosition(), true).get(tile);

            if (selectedCombatUnit.getCombatType().equals("Siege") && !((Archer) selectedCombatUnit).isSetup)
                message.addLine("Siege unit should be set up first");
            else if (distance > selectedCombatUnit.getRange())
                message.addLine("City is out of range");
            else {
                City city = tile.getCity();
                int cityHealth = city.getHitPoint();
                if (city.isGarrison()) cityHealth += cityHealth / 4;
                // TODO: city terrain

                float hitPointAffect = (float) (10 - selectedCombatUnit.getHitPoint()) / 20;
                int unitStrength = selectedCombatUnit.getCombatStrength() - (int) (selectedCombatUnit.getCombatStrength() * hitPointAffect);

                cityHealth -= unitStrength;
                city.setHitPoint(Math.max(cityHealth, 1));
                message.addLine("City HP:" + city.getHitPoint());
            }
        } else {
            int distance = CivilizationController.getInstance().doBFSAndReturnDistances(selectedCity.getPosition(), true).get(tile);

            if (!selectedCombatUnit.getPosition().getAdjacentTiles().contains(tile))
                message.addLine("Unit is far from the city to attack");
            else {
                City city = tile.getCity();
                int cityHealth = city.getHitPoint();
                if (city.isGarrison()) cityHealth += cityHealth / 4;
                // TODO: city terrain

                float hitPointAffect = (float) (10 - selectedCombatUnit.getHitPoint()) / 20;
                int unitStrength = selectedCombatUnit.getCombatStrength() - (int) (selectedCombatUnit.getCombatStrength() * hitPointAffect);

                int unitHealth = selectedCombatUnit.getHitPoint() - city.getStrength();

                if (unitHealth == 0) {
                    message.addLine("Your unit get destroyed");
                } else {
                    selectedCombatUnit.setHitPoint(unitHealth);
                    message.addLine("Unit HP:" + unitHealth);
                }

                cityHealth -= unitStrength;
                if (cityHealth == 0 && unitHealth != 0)
                    ;// TODO: destroy, ...
                else {
                    city.setHitPoint(cityHealth);
                    message.addLine("City HP:" + cityHealth);
                }
            }
        }
    }

    private void cancelCommand() {
        Unit unit = selectedCombatUnit;
        if (unit == null) unit = selectedNonCombatUnit;

        if (unit.getDestination() != null && unit.getDestination().equals(unit.getPosition()))
            message.addLine("Unit is not in a multiple-turn movement");
        else {
            unit.setDestination(unit.getPosition());
            message.addLine("Unit's multiple-turn movement canceled");
        }
    }

    private void foundCityCommand(Processor processor) {
        String name = processor.get("nickname");
        if (name == null) name = processor.get("name");

        if (name == null || processor.getNumberOfFields() != 1)
            message.addLine(getInvalidCommand());
        else if (processor.getSubSection() != null && processor.getSubSection().equals("city")) {
            if (!(selectedNonCombatUnit instanceof Settler))
                message.addLine("Selected unit is not a settler");
            else if (selectedNonCombatUnit.getDestination() != null && !selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
                message.addLine("Unit is in a multiple-turn movement");
            else {
                switch (CivilizationController.getInstance().foundCity((Settler) selectedNonCombatUnit, name)) {
                    case "too close":
                        message.addLine("You cannot found a city close to another civilization");
                        break;
                    case "duplicate name":
                        message.addLine("This name is used before");
                        break;
                    case "ok":
                        message.addLine("City founded!");
                        selectedNonCombatUnit = null;
                        break;
                }
            }
        } else
            message.addLine(getInvalidCommand());
    }

    private void wakeUnit() {
        if (selectedNonCombatUnit != null) selectedNonCombatUnit.makeUnitAwake();
        else selectedCombatUnit.makeUnitAwake();

        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        message.addLine("Unit is awake");
    }

    private void deleteUnitCommand() {
        Unit unit = selectedCombatUnit;
        if (unit == null) unit = selectedNonCombatUnit;

        CivilizationController.getInstance().deleteUnit(unit);

        selectedNonCombatUnit = null;
        selectedCombatUnit = null;
        message.addLine("Unit deleted");
    }

    private void pillageCommand() {
        if (selectedCombatUnit == null)
            message.addLine("Selected unit is not a military unit");
        else if (selectedCombatUnit.getDestination() != null && !selectedCombatUnit.getPosition().equals(selectedCombatUnit.getDestination()))
            message.addLine("Unit is in a multiple-turn movement");
        else if (selectedCombatUnit.getPosition().getImprovement() == null)
            message.addLine("There is no improvement in this tile");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.getPosition().setLooted(true);
            selectedCombatUnit = null;
            message.addLine("Tile is pillaged");
        }
    }

    private void buildCommand() {
        if (!(selectedNonCombatUnit instanceof Worker))
            message.addLine("Selected unit is not a worker");
        else if (selectedNonCombatUnit.getDestination() != null && !selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
            message.addLine("Unit is in a multiple-turn movement");
        else {
            Tile tile = selectedNonCombatUnit.getPosition();
            ArrayList<String> improvements = CivilizationController.getInstance().getPossibleImprovements(tile);
            String choice = getBuildChoice(improvements);
            if (!choice.equals("exit")) {
                CivilizationController.getInstance().build((Worker) selectedNonCombatUnit, improvements.get(Integer.parseInt(choice) - 1));
                message.addLine("Building improvement started");
                selectedNonCombatUnit = null;
            }
        }
    }

    private String getBuildChoice(ArrayList<String> improvements) {
        message.addLine("Please type improvement index you want to build");
        message.addLine("Type \"exit\" if you don't want to choose any improvement");

        for (int i = 0; i < improvements.size(); i++)
            message.addLine(i + 1 + "." + improvements.get(i));

        String command;
        while (true) {
            command = getInput();

            if (command.equals("exit")) return "exit";
            else if (command.matches("\\d+")) {
                int number = Integer.parseInt(command);
                if (number < 1 || number > improvements.size()) message.addLine("Invalid number!");
                else return command;
            } else message.addLine(getInvalidCommand());
        }
    }

    private void removeCommand(Processor processor) {
        if (processor.getSubSection() == null)
            message.addLine(getInvalidCommand());
        else if (!processor.getSubSection().equals("jungle") ||
                !processor.getSubSection().equals("forest") ||
                !processor.getSubSection().equals("marsh"))
            message.addLine(getInvalidCommand());
        else {
            switch (CivilizationController.getInstance().removeFeature(selectedNonCombatUnit, processor.getSubSection())) {
                case "not worker":
                    message.addLine("Selected unit is not a worker");
                    break;
                case "in movement":
                    message.addLine("Unit is in a multiple-turn movement");
                    break;
                case "irremovable feature":
                    message.addLine("This tiles feature cannot get removed");
                    break;
                case "ok":
                    message.addLine("Removing feature started");
                    selectedNonCombatUnit = null;
                    break;
            }
        }
    }

    private void repairCommand() {
        switch (CivilizationController.getInstance().repair(selectedNonCombatUnit)) {
            case "not worker":
                message.addLine("Selected unit is not a worker");
                break;
            case "in movement":
                message.addLine("Unit is in a multiple-turn movement");
                break;
            case "no improvement":
                message.addLine("There is no improvement in this tile to get repaired");
                break;
            case "ok":
                message.addLine("Repairing tile started");
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
    private void handleInfoCategoryCommand(Processor processor) {
        if (processor.getSection() == null)
            message.addLine(getInvalidCommand());
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
            message.addLine(getInvalidCommand());
    }

    private void researchInfoMenu() {
        Technology technology = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getStudyingTechnology();
        if (technology != null) {
            message.addLine("Current Research Project: " + technology.getName());
            message.addLine("Remaining Terms: " + technology.getRemainingTerm());
            futureTechnologies(technology);
            futureImprovements(technology);
            futureUnits(technology);
            futureResources(technology);
        } else message.addLine("No technology is being researched");
    }

    private void futureTechnologies(Technology technology) {
        ArrayList<String> technologies = new ArrayList<>();
        for (TechnologyEnum value : TechnologyEnum.values())
            if (value.getPrerequisiteTechnologies().contains(technology.getName()))
                technologies.add(technology.getName());

        if (technologies.size() == 0)
            message.addLine("No new technology");
        else {
            message.addLine("New Technologies:");
            for (String tech : technologies)
                message.addLine(tech);
        }
    }

    private void futureImprovements(Technology technology) {
        ArrayList<String> improvements = new ArrayList<>();
        for (ImprovementEnum value : ImprovementEnum.values())
            if (value.requiredTechnology.equals(technology))
                improvements.add(technology.getName());

        if (improvements.size() == 0)
            message.addLine("No new improvement");
        else {
            message.addLine("New Improvements:");
            for (String improvement : improvements)
                message.addLine(improvement);
        }
    }

    private void futureUnits(Technology technology) {
        ArrayList<String> units = new ArrayList<>();
        for (UnitEnum value : UnitEnum.values())
            if (value.getRequiredTechnology().getName().equals(technology.getName()))
                units.add(technology.getName());

        if (units.size() == 0)
            message.addLine("No new unit");
        else {
            message.addLine("New Units:");
            for (String unit : units)
                message.addLine(unit);
        }
    }

    private void futureResources(Technology technology) {
        String resourceName = null;
        if (technology.getName().equals(TechnologyEnum.ScientificTheory.getName()))
            resourceName = "Coal";
        else if (technology.getName().equals(TechnologyEnum.AnimalHusbandry.getName()))
            resourceName = "Horse";
        else if (technology.getName().equals(TechnologyEnum.IronWorking.getName()))
            resourceName = "Iron";

        if (resourceName == null)
            message.addLine("No new resource");
        else {
            message.addLine("New resources:");
            message.addLine(resourceName);
        }
    }


    private void unitsInfoPanel() {
        Civilization civilization = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization();

        StringBuilder output = new StringBuilder("List of units: ").append("\n");
        for (int i = 1; i <= civilization.getUnits().size(); i++) {
            Unit unit = civilization.getUnits().get(i - 1);

            output.append(i).append(".").append(unit.getName()).append("|(").append(unit.getPosition().getX());
            output.append(", ").append(unit.getPosition().getY()).append(")").append("\n");
        }

        output.append("If you want to select a unit, please type its index.\n");
        output.append("If you want to enter military overview screen, please type \"military\".\n");
        output.append("If you want to exit the panel, please type \"exit\".");

        message.addLine(output);

        String choice = getUnitsPanelChoice(civilization);
        if (Objects.equals(choice, "military"))
            militaryInfoMenu();
        else if (Objects.equals(choice, "exit"))
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

    private String getUnitsPanelChoice(Civilization civilization) {
        String choice;
        while (true) {
            choice = getInput();
            if (Objects.equals(choice, "military")) return choice;
            else if (Objects.equals(choice, "exit")) return choice;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);

                if (number < 1 || number > civilization.getUnits().size())
                    message.addLine("Invalid number!");
                else return choice;
            } else message.addLine(getInvalidCommand());
        }
    }


    private void citiesInfoPanel() {
        Civilization civilization = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization();

        StringBuilder output = new StringBuilder("List of cities: ").append("\n");
        for (int i = 1; i <= civilization.getCities().size(); i++) {
            City city = civilization.getCities().get(i - 1);

            output.append(i).append(".Name:").append(city.getName());
            if (Objects.equals(civilization.getCurrentCapital(), city)) output.append("(Capital)");
            output.append("|(").append(city.getPosition().getX()).append(", ").append(city.getPosition().getY()).append(")");
            output.append("|Number of Citizens:").append(city.getCitizens().size());
            if (city.getUnitUnderProduct() != null)
                output.append("|City Production:").append(city.getUnitUnderProduct().getName()).append("\n");
            else
                output.append("|City Production:").append("\n");
        }

        output.append("If you want to select a city, please type its index.\n");
        output.append("If you want to enter economic overview screen, please type \"economic\".\n");
        output.append("If you want to exit the panel, please type \"exit\".");

        message.addLine(output);

        String choice = getCitiesPanelChoice(civilization);
        if (Objects.equals(choice, "economic"))
            economicInfoMenu();
        else if (Objects.equals(choice, "exit"))
            return;
        else {
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            selectedCity = civilization.getCities().get(Integer.parseInt(choice) - 1);
            cityScreen();
        }
    }

    private String getCitiesPanelChoice(Civilization civilization) {
        String choice;
        while (true) {
            choice = getInput();

            if (Objects.equals(choice, "economic")) return choice;
            else if (Objects.equals(choice, "exit")) return choice;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);

                if (number < 1 || number > civilization.getCities().size())
                    message.addLine("Invalid number!");
                else return choice;
            } else message.addLine(getInvalidCommand());
        }
    }


    private void diplomacyInfoMenu() {
        // TODO: Not Phase1
    }

    private void victoryInfoMenu() {
        // TODO: Not Phase1
    }


    private void demographicsInfoMenu() {
        ArrayList<Civilization> civilizations = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getGame().getCivilizations();

        message.addLine("Demographics Screen:");
        territoryRank(civilizations);
        populationRank(civilizations);
        goldRank(civilizations);
        happinessRank(civilizations);
        unitsRank(civilizations);
    }

    private void territoryRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> territory = new ArrayList<>();
        for (Civilization civilization : civilizations)
            territory.add(civilization.getTerritory().size());

        Collections.sort(territory);

        int territorySize = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getTerritory().size();
        int rank = territory.indexOf(territorySize) + 1;
        int best = territory.get(0);
        int average = territory.get(territory.size() / 2);
        int worst = territory.get(territory.size() - 1);

        message.addLine(String.format("Territory: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", territorySize,
                best, average, worst, rank));
    }

    private void populationRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> population = new ArrayList<>();
        for (Civilization civilization : civilizations)
            population.add(civilization.getPopulation());

        Collections.sort(population);

        int populationSize = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getPopulation();
        int rank = population.indexOf(populationSize) + 1;
        int best = population.get(0);
        int average = population.get(population.size() / 2);
        int worst = population.get(population.size() - 1);

        message.addLine(String.format("Population: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", populationSize,
                best, average, worst, rank));
    }

    private void goldRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> gold = new ArrayList<>();
        for (Civilization civilization : civilizations)
            gold.add(civilization.getGold());

        Collections.sort(gold);

        int goldAmount = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getGold();
        int rank = gold.indexOf(goldAmount) + 1;
        int best = gold.get(0);
        int average = gold.get(gold.size() / 2);
        int worst = gold.get(gold.size() - 1);

        message.addLine(String.format("Wealth: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", goldAmount,
                best, average, worst, rank));
    }

    private void happinessRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> happiness = new ArrayList<>();
        for (Civilization civilization : civilizations)
            happiness.add(civilization.getHappiness());

        Collections.sort(happiness);

        int happinessAmount = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getHappiness();
        int rank = happiness.indexOf(happinessAmount) + 1;
        int best = happiness.get(0);
        int average = happiness.get(happiness.size() / 2);
        int worst = happiness.get(happiness.size() - 1);

        message.addLine(String.format("Happiness: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", happinessAmount,
                best, average, worst, rank));
    }

    private void unitsRank(ArrayList<Civilization> civilizations) {
        ArrayList<Integer> units = new ArrayList<>();
        for (Civilization civilization : civilizations)
            units.add(civilization.getUnits().size());

        Collections.sort(units);

        int unitsSize = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getUnits().size();
        int rank = units.indexOf(unitsSize) + 1;
        int best = units.get(0);
        int average = units.get(units.size() / 2);
        int worst = units.get(units.size() - 1);

        message.addLine(String.format("Number of units: %d|Best: %d|Average: %d|Worst: %d|Rank: %d\n", unitsSize,
                best, average, worst, rank));
    }


    private void notificationsInfoMenu() {
        Civilization civilization = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization();

        StringBuilder output = new StringBuilder("Notifications Log:").append("\n");
        for (Notification notification : civilization.getNotifications()) {
            output.append("Message:").append(notification.getMessage());
            output.append("|Tern:").append(notification.getTern()).append("\n");
        }

        message.addLine(output);
    }


    private void militaryInfoMenu() {
        Civilization civilization = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization();

        StringBuilder output = new StringBuilder("List of units: ").append("\n");
        printListOfUnits(output, civilization);

        message.addLine(output);
    }

    private void printListOfUnits(StringBuilder output, Civilization civilization) {
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


    private void economicInfoMenu() {
        Civilization civilization = MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization();

        StringBuilder output = new StringBuilder("List of cities: ").append("\n");
        printListOfCities(output, civilization);

        output.append("If you want to select a city, please type its index.\n");
        output.append("If you want to exit the screen, please type \"exit\".\n");

        message.addLine(output.toString());

        String choice = getEconomicMenuChoice(civilization);

        if (Objects.equals(choice, "exit"))
            return;
        else {
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
            selectedCity = civilization.getCities().get(Integer.parseInt(choice) - 1);
            cityScreen();
        }
    }

    private void printListOfCities(StringBuilder output, Civilization civilization) {
        for (int i = 1; i <= civilization.getCities().size(); i++) {
            City city = civilization.getCities().get(i - 1);

            output.append(i).append(".Name:").append(city.getName());
            if (Objects.equals(civilization.getCurrentCapital(), city)) output.append("(Capital)");
            output.append("|(").append(city.getPosition().getX()).append(", ").append(city.getPosition().getY()).append(")");
            output.append("|Number of Citizens:").append(city.getCitizens().size());
            output.append("|City Strength:").append(city.getStrength());
            output.append("|Food:").append(city.getFood());
            output.append("|Production Rate:").append(city.getProductionRate());
            output.append("|Science Rate:").append(city.getScienceRate());
            output.append("|Gold Rate:").append(city.getGoldRate());
            if (city.getUnitUnderProduct() != null) {
                output.append("|City Production:").append(city.getUnitUnderProduct().getName());
                output.append("(").append(city.getProductionUnderProductTern()).append("\n");
            } else
                output.append("|City Production:").append("\n");
        }
    }

    private String getEconomicMenuChoice(Civilization civilization) {
        String choice;

        while (true) {
            choice = getInput();
            if (Objects.equals(choice, "exit")) return choice;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);

                if (number < 1 || number > civilization.getCities().size()) message.addLine("Invalid number!");
                else return choice;
            } else
                message.addLine(getInvalidCommand());
        }
    }


    private void diplomaticInfoMenu() {
        // TODO: Not Phase1
    }

    private void dealsInfoMenu() {
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
    private void handleCityCategoryCommand(Processor processor) {
        if (selectedCity == null)
            message.addLine("Please select a city first");
        else if (processor.getSection() == null)
            message.addLine(getInvalidCommand());
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
            message.addLine(getInvalidCommand());
    }

    private void cityScreen() {
        System.out.print(selectedCity.getName());
        if (Objects.equals(MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getCurrentCapital(), selectedCity))
            message.addLine(String.format("(Capital)| Strength:%d\n", selectedCity.getStrength()));
        else
            message.addLine(String.format("|Strength:%d\n", selectedCity.getStrength()));

        message.addLine(String.format("Food:%d| Production Rate:%d| Science Rate:%d| Gold Rate:%d\n",
                selectedCity.getFood(), selectedCity.getProductionRate(), selectedCity.getScienceRate(), selectedCity.getGoldRate()));
        message.addLine(String.format("Population:%d| Turn's until new citizen:%d\n", selectedCity.getCitizens().size(), selectedCity.getTillNewCitizen()));
        message.addLine("Turn's until new production:" + selectedCity.getProductionUnderProductTern());
        message.addLine("List of City's citizens:");
        for (int i = 1; i <= selectedCity.getCitizens().size(); i++) {
            Citizen citizen;
            if ((citizen = selectedCity.getCitizens().get(i - 1)).isWorking())
                message.addLine(i + ".(" + citizen.getWorkPosition().getX() + ", " + citizen.getWorkPosition().getY() + ")");
            else
                message.addLine("Idle");
        }

        message.addLine("City's territory:");
        for (Tile tile : selectedCity.getTerritory())
            System.out.print("(" + tile.getX() + ", " + tile.getY() + ") ");
        message.addLine("");

        City city = navigateBetweenCities();

        if (city != null) {
            selectedCity = city;
            cityScreen();
        }
    }

    private City navigateBetweenCities() {
        ArrayList<City> cities = new ArrayList<>(MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getCities());
        cities.remove(selectedCity);

        message.addLine("-------------------------------------------------------------");
        message.addLine("If you want to enter a city's screen, please type its index");
        message.addLine("If you want to exit this screen, please type \"exit\"");
        for (int i = 1; i <= cities.size(); i++)
            message.addLine(i + "." + cities.get(i - 1));

        String choice;
        while (true) {
            choice = getInput();
            if (Objects.equals(choice, "exit")) break;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > cities.size()) message.addLine("Invalid number");
                else break;
            } else message.addLine(getInvalidCommand());
        }

        if (Objects.equals(choice, "exit")) return null;
        else return cities.get(Integer.parseInt(choice) - 1);
    }


    private void cityOutput() {
        message.addLine(selectedCity.getName() + "'s output:");
        message.addLine("Production Rate:" + selectedCity.getProductionRate());
        message.addLine("Science Rate:" + selectedCity.getScienceRate());
        message.addLine("Gold Rate:" + selectedCity.getGoldRate());
        message.addLine("Food Rate:" + selectedCity.getFoodRate());
        message.addLine("Turns till new citizens:" + selectedCity.getTillNewCitizen());
    }


    private void unemployedCitizenSection(Processor processor) {
        if (processor.getSubSection() == null || !processor.getSubSection().equals("citizens"))
            message.addLine(getInvalidCommand());
        else {
            ArrayList<Citizen> citizens = new ArrayList<>(selectedCity.getCitizens());

            citizens.removeIf(Citizen::isWorking);

            if (citizens.size() == 0) message.addLine("There is not unemployed citizen in this city");
            else lockCitizensToTiles(citizens);
        }
    }

    private void lockCitizensToTiles(ArrayList<Citizen> citizens) {
        message.addLine("There is " + citizens.size() + " unemployed citizens in this city");
        message.addLine("If you want to lock a citizen to a tile, please type tiles coordinates like \"x y\"");
        message.addLine("If you want to exit this menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();

            if (Objects.equals(choice, "exit")) return;
            else if (choice.matches("\\d+ \\d+")) {
                int[] position = {Integer.parseInt(choice.split(" ")[0]), Integer.parseInt(choice.split(" ")[1])};
                if (!CivilizationController.getInstance().isPositionValid(position))
                    message.addLine("Invalid coordinates");
                else if (!selectedCity.getTerritory().contains(CivilizationController.getInstance().getTileByPosition(position)))
                    message.addLine("This tile is not in this city");
                else {
                    citizens.get(0).setWorking(true);
                    citizens.get(0).setWorkPosition(CivilizationController.getInstance().getTileByPosition(position));
                    citizens.remove(citizens.get(0));
                    message.addLine("A citizen has been locked to tile (" + position[0] + ", " + position[1] + ")");
                    if (citizens.size() == 0) {
                        message.addLine("There is not any other unemployed citizen in this city");
                        return;
                    }
                    message.addLine("There is " + citizens.size() + " unemployed citizens in this city");
                    message.addLine("If you want to lock a citizen to a tile, please type tiles coordinates like \"x y\"");
                    message.addLine("If you want to exit this menu, please type \"exit\"");
                }
            } else message.addLine(getInvalidCommand());
        }
    }


    private void removeCitizensFromWork(Processor processor) {
        if (processor.getSubSection() == null || !processor.getSubSection().equals("citizens"))
            message.addLine(getInvalidCommand());
        else {
            ArrayList<Citizen> citizens = new ArrayList<>(selectedCity.getCitizens());

            citizens.removeIf(citizen -> !citizen.isWorking());

            if (citizens.size() == 0) message.addLine("There is no employed citizens in this city");
            else getCitizenToRemove(citizens);
        }
    }

    private void getCitizenToRemove(ArrayList<Citizen> citizens) {
        message.addLine("There is " + citizens.size() + " employed citizens in this city");
        for (int i = 1; i <= citizens.size(); i++)
            message.addLine(i + ".(" + citizens.get(i - 1).getWorkPosition().getX() +
                    ", " + citizens.get(i - 1).getWorkPosition().getY() + ")");
        message.addLine("If you want to remove a citizen from a tile, please type its index");
        message.addLine("If you want to exit this menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();

            if (Objects.equals(choice, "exit")) return;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > citizens.size())
                    message.addLine("Invalid number");
                else {
                    citizens.get(number - 1).setWorking(false);
                    message.addLine("Selected citizen get removed from work");
                    return;
                }
            } else message.addLine(getInvalidCommand());
        }
    }


    private void buyTile(Processor processor) {
        if (processor.getSubSection() == null || !processor.getSubSection().equals("tile"))
            message.addLine(getInvalidCommand());
        else if (MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getCivilization().getGold() < 50)
            message.addLine("You don't have enough money to buy a tile");
        else {
            ArrayList<Tile> purchasableTiles = new ArrayList<>();

            for (Tile tile : selectedCity.getTerritory()) {
                ArrayList<Tile> adjacentTiles = tile.getAdjacentTiles();
                for (Tile adjacentTile : adjacentTiles)
                    if (adjacentTile.getCity() == null) purchasableTiles.add(adjacentTile);
            }
            Set<Tile> set = new HashSet<>(purchasableTiles);
            purchasableTiles.clear();
            purchasableTiles.addAll(set);
            if (purchasableTiles.size() == 0) message.addLine("There is not tile to buy");
            else chooseTileToBuy(purchasableTiles);
        }
    }

    private void chooseTileToBuy(ArrayList<Tile> purchasableTiles) {
        message.addLine("Purchasable tiles:");
        for (int i = 1; i <= purchasableTiles.size(); i++)
            message.addLine(i + ".(" + purchasableTiles.get(i - 1).getX() + ", " + purchasableTiles.get(i - 1).getY() + ")");

        message.addLine("If you want to buy a tile, please type its index");
        message.addLine("If you want to exit this menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();

            if (Objects.equals(choice, "exit")) return;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > purchasableTiles.size())
                    message.addLine("Invalid number");
                else {
                    CivilizationController.getInstance().purchaseTile(selectedCity, purchasableTiles.get(number - 1));
                    message.addLine("The selected tile is added to your territory");
                    return;
                }
            } else message.addLine(getInvalidCommand());
        }
    }


    private void constructionMenu() {
        ArrayList<UnitEnum> unitEnums = CivilizationController.getInstance().getProducibleUnits();

        message.addLine("List of producible units:");
        for (int i = 1; i <= unitEnums.size(); i++)
            message.addLine(i + "." + unitEnums.get(i - 1) + "|" + (int) Math.ceil((float) unitEnums.get(i - 1).getCost() / selectedCity.getProductionRate()));
        message.addLine("If you want to choose/change a production, please type its index");
        message.addLine("If you want to exit the menu, please type \"exit\"");

        String choice;
        while (true) {
            choice = getInput();
            if (Objects.equals(choice, "exit")) return;
            else if (choice.matches("\\d+")) {
                int number = Integer.parseInt(choice);
                if (number < 1 || number > unitEnums.size()) message.addLine("Invalid number");
                else {
                    selectedCity.setUnitUnderProduct(Unit.getUnitByUnitEnum(unitEnums.get(number - 1)));
                    selectedCity.setUnitUnderProductTern((int) Math.ceil((float) unitEnums.get(number - 1).getCost() / selectedCity.getProductionRate()));
                }
            } else message.addLine(getInvalidCommand());
        }
    }


    private void attackUnits(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        if (processor.getSubSection() == null ||
                (!processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")) ||
                processor.getNumberOfFields() != 2 ||
                x == null || y == null)
            message.addLine(getInvalidCommand());
        else if (!CivilizationController.getInstance().isPositionValid(new int[]{Integer.parseInt(x), Integer.parseInt(y)}))
            message.addLine("Invalid coordinates!");
        else {
            int[] unitPosition = new int[]{Integer.parseInt(x), Integer.parseInt(y)};
            Tile tile = CivilizationController.getInstance().getTileByPosition(unitPosition);

            if (processor.getSection() == null) {
            } else if (processor.getSection().equals("combat")) {
                if (tile.getCombatUnit() == null) message.addLine("There is no combat unit in that place");
            } else if (processor.getSection().equals("noncombat")) {
                if (tile.getNonCombatUnit() == null) message.addLine("There is no noncombat unit in that place");
            } else {
                if (CivilizationController.getInstance().doBFSAndReturnDistances(selectedCity.getPosition(), true).get(tile) > 2)
                    message.addLine("Unit is not in city's range");

            }
        }
    }

    private void handleTurnCategory(Processor processor) {
        // TODO: 4/21/2022
    }

    /*
    cheat research technology --name <name>
    cheat finish research
     */
    private void handleCheatCategoryCommand(Processor processor) {
        if (processor.getSection() == null) message.addLine(getInvalidCommand());
        else if (processor.getSection().equals("increase"))
            increaseCheatCommand(processor.get("amount"), processor.getSubSection());
        else if (processor.getSection().equals("teleport"))
            teleportCheatCommand(processor.get("x"), processor.get("y"));
        else if (processor.getSection().equals("finish")) finishCheatCommand(processor.getSubSection());
        else if (processor.getSection().equals("research"))
            researchCheatCommand(processor.getSubSection(), processor.get("name"));
        else if (processor.getSection().equals("reveal")) revealCheatCommand(processor.get("x"), processor.get("y"));
        else message.addLine(getInvalidCommand());

    }

    private void increaseCheatCommand(String amountField, String subSection) {
        if (amountField == null) {
            message.addLine("field 'amount' required\n");
            return;
        } else if (!amountField.matches(NON_NEGATIVE_NUMBER_REGEX)) {
            message.addLine("amount must be a valid non-negative integer\n");
            return;
        }
        int amount = Integer.parseInt(amountField);
        if (Objects.equals(subSection, "gold")) {
            MainServer.getCheatControllerByToken(mySocketHandler.getGameToken()).increaseGold(amount);
            message.addLine("Done");
        } else if (Objects.equals(subSection, "beaker")) {
            MainServer.getCheatControllerByToken(mySocketHandler.getGameToken()).increaseBeaker(amount);
            message.addLine("Done");
        } else message.addLine(getInvalidCommand());
    }

    private void teleportCheatCommand(String xField, String yField) {
        Unit selectedUnit = selectedCombatUnit;
        if (selectedUnit == null) selectedUnit = selectedNonCombatUnit;
        if (selectedUnit == null) {
            System.out.print("no unit selected\n");
            return;
        }
        if (xField == null || yField == null) System.out.print("fields 'x' and 'y' required\n");
        else if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX))
            System.out.print("x, y must be valid non-negative integers");
        else {
            int x = Integer.parseInt(xField);
            int y = Integer.parseInt(yField);
            message.addLine(String.format("%s\n", MainServer.getCheatControllerByToken(mySocketHandler.getGameToken()).teleport(selectedUnit, x, y)));
        }
    }

    private void finishCheatCommand(String subSection) {
        if (!subSection.equals("research")) message.addLine(getInvalidCommand());
        else message.addLine(MainServer.getCheatControllerByToken(mySocketHandler.getGameToken()).finishResearch());
    }

    private void researchCheatCommand(String subSection, String name) {
        if (!subSection.equals("technology")) message.addLine(getInvalidCommand());
        else message.addLine(MainServer.getCheatControllerByToken(mySocketHandler.getGameToken()).researchTechnology(name));
    }

    private void revealCheatCommand(String xField, String yField) {
        if (xField == null || yField == null) System.out.print("fields 'x' and 'y' required\n");
        else if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX))
            System.out.print("x, y must be valid non-negative integers");
        else {
            int x = Integer.parseInt(xField);
            int y = Integer.parseInt(yField);
            message.addLine(String.format("%s\n", MainServer.getCheatControllerByToken(mySocketHandler.getGameToken()).reveal(x, y)));
        }
    }


    private void handleCityInfo(Scanner scanner) {
        // TODO: 4/21/2022
    }






    private void handleEndCategoryCommand(Processor processor) {
        if (processor.getSection() == null) message.addLine(getInvalidCommand());
        else if (processor.getSection().equals("turn")) MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).startTurn();
        else message.addLine(getInvalidCommand());
    }

    private void handleMapCategoryCommand(Processor processor) {
        if (processor.getSection() == null)
            message.addLine(getInvalidCommand());
        else if (processor.getSection().equals("show")) {
            // TODO : subsection may be null.
            if (processor.getSubSection().equals("position")) {
                String xField = processor.get("x");
                String yField = processor.get("y");
                if (!xField.matches(NON_NEGATIVE_NUMBER_REGEX) || !yField.matches(NON_NEGATIVE_NUMBER_REGEX)) {
                    message.addLine("x and y must be non-negative\n");
                    return;
                }
                int x = Integer.parseInt(xField);
                int y = Integer.parseInt(yField);
                if (x >= MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getGame().getMainGameMap().getMapHeight()) {
                    message.addLine("x out of bounds\n");
                    return;
                }
                if (y >= MainServer.getGameControllerByToken(mySocketHandler.getGameToken()).getGame().getMainGameMap().getMapWidth()) {
                    message.addLine("y out of bounds\n");
                    return;
                }
                mapX = x;
                mapY = y;
            } else message.addLine(getInvalidCommand());
        } else if (processor.getSection().equals("move")) {
            if (processor.getSubSection().equals("right")) {
                if (processor.get("c") != null && processor.get("c").matches(NON_NEGATIVE_NUMBER_REGEX))
                    mapY += Integer.parseInt(processor.get("c"));
                else mapY++;
            }
            if (processor.getSubSection().equals("left")) {
                if (processor.get("c") != null && processor.get("c").matches(NON_NEGATIVE_NUMBER_REGEX))
                    mapY -= Integer.parseInt(processor.get("c"));
                else mapY--;
                mapY = Math.max(0, mapY);
            }
            if (processor.getSubSection().equals("up")) {
                if (processor.get("c") != null && processor.get("c").matches(NON_NEGATIVE_NUMBER_REGEX))
                    mapX -= Integer.parseInt(processor.get("c"));
                else mapX--;
                mapX = Math.max(0, mapX);
            }
            if (processor.getSubSection().equals("down")) {
                if (processor.get("c") != null && processor.get("c").matches(NON_NEGATIVE_NUMBER_REGEX))
                    mapX += Integer.parseInt(processor.get("c"));
                else mapX++;
            }
        } else if (processor.getSection().equals("get")) {
            if (processor.getSubSection().equals("x")) {
                message.addData("mapX", mapX);
            } else if (processor.getSubSection().equals("y")) {
                message.addData("mapY", mapY);
            } else if (processor.getSubSection().equals("data")) {
                message.addData("mapX", mapX);
                message.addData("mapY", mapY);
                String[] hashMap = GMini.getInstance().startMiniSave(MainServer.getGameControllerByToken(mySocketHandler.getGameToken()));
                String str = new Gson().toJson(hashMap);
                //str = str.replace("false","|").replace("true","~");
                //str = str.replace("\\\"","^");
                message.addData("GameController", str);
            }
        } else
            message.addLine(getInvalidCommand());
    }

}
