package app.views.commandLineMenu;

import app.views.graphicalMenu.MenuController;

import java.util.Scanner;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);


    protected static void waitForResponse(int limit) {
        int k = 0;
        for (int i = 0; i != limit && !MenuController.isReceivedResponse; i++) {
            if (i % 4 == 0) k++;
        }
    }

    public static String getInput(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

}