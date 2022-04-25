package views;

import models.City;
import models.unit.CombatUnit;
import models.unit.NonCombatUnit;

import java.util.Scanner;

public class GameMenu extends Menu{

    private static CombatUnit selectedCombatUnit;
    private static NonCombatUnit selectedNonCombatUnit;
    private static City selectedCity;
    private static int mapX;
    private static int mapY;

    static void processOneCommand(){
        Processor processor;

        while (getCurrentMenu().equals("game")){
            processor = new Processor(getInput());

            if (!processor.isValid() || processor.getCategory() == null)
                invalidCommand();
            else if (processor.getCategory().equals("menu"))
                handleMenuCategoryCommand(processor);
            else
                invalidCommand();
        }
    }

    private static void handleInfoCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleSelectCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleUnitCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleCityCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleMapCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleTurnCategory(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleCheatCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private static void handleResearchInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleUnitsInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleCitiesInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleDiplomacyInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleVictoryInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleDemographicsInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleNotificationsInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleMilitaryInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleEconomicInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleDiplomaticInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleDealsInfoMenu(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void handleCityInfo(Scanner scanner){
        // TODO: 4/21/2022
    }

    private static void showMap(){
        // TODO: 4/21/2022
    }
}
