//besmellah

package controllers;

public class CheatController {
    private static CheatController instance = null;

    public static CheatController getInstance() {
        if (instance == null) instance = new CheatController();
        return instance;
    }
}