package views;

import java.util.Scanner;

public class MainMenu extends Menu{

    void processOneCommand(Scanner scanner){
        Processor processor = new Processor(getInput());

        while (Menu.getCurrentMenu().equals("main")){
            if (processor.getCategory().equals("user"))
                handleUserCategoryCommand();
            else if (processor.getCategory().equals("play"))
                playCategoryCommand(processor);

        }
    }

    private void handleUserCategoryCommand(){
        // TODO: 4/21/2022
    }

    private void playCategoryCommand(Processor processor){
        // TODO: 4/21/2022
    }

    private void logout(){
        // TODO: 4/21/2022
    }

    private void startNewGame(Processor processor){
        // TODO: 4/21/2022
    }

    private void loadGame(Processor processor){
        // TODO: 4/21/2022
    }
}
