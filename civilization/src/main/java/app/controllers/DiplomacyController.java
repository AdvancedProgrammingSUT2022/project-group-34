package app.controllers;

import app.models.City;
import app.models.tile.Tile;
import app.models.unit.CombatUnit;

public class DiplomacyController {
    //Singleton Pattern
    private static DiplomacyController instance;

    private DiplomacyController() {
    }

    public static DiplomacyController getInstance() {
        if (instance == null) instance = new DiplomacyController();
        return instance;
    }


    public void attack(CombatUnit unit, Tile destination) {
        // TODO: 4/25/2022
    }

    public void pillage(CombatUnit unit) {
        // TODO: 4/25/2022
    }

    public void cityAttack(City city, CombatUnit unit) {
        // TODO: 4/25/2022
    }
}
