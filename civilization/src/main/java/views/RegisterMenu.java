package views;

import java.util.Scanner;

public class RegisterMenu extends Menu{

    void processOneCommand(Scanner scanner){
        String command = getInput();
        Processor processor = new Processor(command);

        while (!command.equals("menu exit")){
            if (processor.getCategory().equals("user"))
                handleUserCategoryCommand(processor);
            else if (processor.getCategory().equals("menu"))
                handleMenuCategoryCommand(processor);
            else
                invalidCommand();
        }
    }

    private void handleUserCategoryCommand(Processor processor){
        if (processor.getSection().equals("register"))
            register(processor);
        else if (processor.getSection().equals("login"))
            login(processor);
        else
            invalidCommand();
    }

    private void register(Processor processor){
        // TODO: 4/21/2022
    }

    private void login(Processor processor){
        // TODO: 4/21/2022  
    }
}
