package views;

import controllers.CivilizationController;
import models.City;
import models.Civilization;
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


    private static void handleInfoCategoryCommand(Processor processor) {
        // TODO: 4/21/2022
    }


    //Handles commands that start with "select unit"
    private static void handleSelectCategoryCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");

        if (processor.getSection() == null ||
                processor.getSubSection() == null ||
                !processor.getSection().equals("unit") ||
                (!processor.getSubSection().equals("combat") && !processor.getSubSection().equals("noncombat")) ||
                x == null ||
                y == null ||
                processor.getNumberOfFields() != 2) {
            invalidCommand();
            return;
        }

        int[] position = {Integer.parseInt(x), Integer.parseInt(y)};
        selectUnit(position, processor.getSubSection());
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
                System.out.println("Combat unit selected");
                break;
            case "noncombat":
                selectedNonCombatUnit = CivilizationController.getInstance().getNonCombatUnitByPosition(position);
                selectedCombatUnit = null;
                System.out.println("Noncombat unit selected");
                break;
        }
    }


    //Handles commands that start with "unit"
    private static void handleUnitCategoryCommand(Processor processor) {
        String x = processor.get("x");
        String y = processor.get("y");

        if (processor.getSection() == null ||
                x == null ||
                y == null ||
                processor.getNumberOfFields() != 2)
            invalidCommand();
        else if (processor.getSection().equals("moveto")) {
            int[] destination = {Integer.parseInt(x), Integer.parseInt(y)};

            if (selectedNonCombatUnit == null) movetoCommand(selectedCombatUnit, destination);
            else if (selectedCombatUnit == null) movetoCommand(selectedNonCombatUnit, destination);
            else System.out.println("First select a unit");
        } else
            invalidCommand();
    }

    //Checks what to print based on the destination and selected unit
    private static void movetoCommand(Unit unit, int[] destination) {
        switch (CivilizationController.getInstance().moveUnit(unit, destination)) {
            case "invalid destination":
                System.out.println("Destination is not valid.");
                break;
            case "fog of war":
                System.out.println("Destination is in fog of war");
                break;
            case "already at the same tile":
                System.out.println("We already are at the tile you want to move to");
                break;
            case "destination occupied":
                System.out.println("There is already a unit in the tile you want to move to");
                break;
            case "success":
                System.out.println("Unit move to destination successfully");
                selectedCombatUnit = null;
                selectedNonCombatUnit = null;
                break;
        }
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
