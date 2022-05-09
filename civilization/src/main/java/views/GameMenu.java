package views;

import controllers.CivilizationController;
import controllers.GameController;
import models.City;
import models.Civilization;
import models.Game;
import models.tile.Tile;
import models.unit.CombatUnit;
import models.unit.NonCombatUnit;
import models.unit.Unit;

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


    //Handles commands that start with "select unit"
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

    //Checks what to print based on the position and unit type
    private static void selectUnit(int[] position, String unitType) {
        switch (CivilizationController.getInstance().selectUnit(position, unitType)) {
            case "invalid position":
                System.out.println("Position is invalid");
                break;
            case "no such unit":
                System.out.format("No %s unit exists in position[%d %d]\n",
                        unitType.substring(0, 1).toUpperCase() + unitType.substring(1), position[0], position[1]);
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

    //Checks conditions for selecting city in position
    private static void selectCity(int[] position) {
        Tile tile = CivilizationController.getInstance().getTileByPosition(position);
        City city = tile.getCity();

        if (tile.getCity() == null)
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

    //Checks conditions for selecting city with "name"
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


    //Handles commands that start with "unit"
    private static void handleUnitCategoryCommand(Processor processor) {

        if (processor.getSection() == null)
            invalidCommand();
        else if (processor.getSection().equals("moveto"))
            movetoCommand(processor);
        else
            invalidCommand();
    }


    //Checks what to print based on the destination and selected unit
    private static void movetoCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");
        Unit unit;

        if (x == null || y == null || processor.getNumberOfFields() != 2) {
            invalidCommand();
            return;
        }
        if (selectedNonCombatUnit != null) unit = selectedNonCombatUnit;
        else if (selectedCombatUnit != null) unit = selectedCombatUnit;
        else {
            System.out.println("First select a unit");
            return;
        }

        String response = CivilizationController.getInstance().moveUnit(unit, new int[]{Integer.parseInt(x), Integer.parseInt(y)});

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
            System.out.println("Unit move to destination successfully");
            selectedCombatUnit = null;
            selectedNonCombatUnit = null;
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
