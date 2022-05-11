package views;

import controllers.CivilizationController;
import controllers.GameController;
import models.City;
import models.Civilization;
import models.tile.Tile;
import models.unit.*;

import java.util.Scanner;

public class GameMenu extends Menu {
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


    /*Handles commands that start with "unit"
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

    private static void cancelCommand(){
        Unit unit = selectedCombatUnit;
        if (unit==null) unit = selectedNonCombatUnit;

        if (unit.getDestination()==unit.getPosition())
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

    private static void pillageCommand(){
        if (selectedCombatUnit == null)
            System.out.println("Selected unit is not a military unit");
        else if (selectedCombatUnit.getPosition().getImprovementName()==null)
            System.out.println("There is no improvement in this tile");
        else {
            selectedCombatUnit.makeUnitAwake();
            selectedCombatUnit.getPosition().setLooted(true);
            selectedCombatUnit = null;
            System.out.println("Tile is pillaged");
        }
    }


    private static void handleInfoCategoryCommand(Processor processor) {
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

    private static void handleResearchInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleUnitsInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleCitiesInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleDiplomacyInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleVictoryInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleDemographicsInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleNotificationsInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleMilitaryInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleEconomicInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleDiplomaticInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleDealsInfoMenu(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void handleCityInfo(Scanner scanner) {
        // TODO: 4/21/2022
    }

    private static void showMap() {
        // TODO: 4/21/2022
    }
}
