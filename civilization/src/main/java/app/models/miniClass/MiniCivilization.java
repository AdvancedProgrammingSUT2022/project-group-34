package app.models.miniClass;

import app.controllers.GMini;
import app.models.Civilization;

import java.util.ArrayList;

public class MiniCivilization {

    private String civN;
    private Integer pMI;
    private ArrayList<String> cRT = new ArrayList<>();
    private int turn = 0;

    public MiniCivilization(Civilization civilization) {
        this.civN = civilization.getCivilizationName();
        this.pMI = GMini.getInstance().miniSave(civilization.getPersonalMap());
        this.turn = civilization.getTurn();
        civilization.getCivilizationResearchedTechnologies().forEach((technologyEnum, technology) ->
                this.cRT.add(technologyEnum.getName()));

    }

}
