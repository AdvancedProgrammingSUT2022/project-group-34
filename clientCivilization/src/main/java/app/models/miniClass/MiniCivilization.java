package app.models.miniClass;

import app.controllers.GLoad;
import app.models.Civilization;
import app.models.Technology;
import app.models.TechnologyEnum;
import app.models.map.CivilizationMap;

import java.util.ArrayList;
import java.util.HashMap;

public class MiniCivilization extends Mini{

    private String civN;
    private Integer pMI;
    private ArrayList<String> cRT = new ArrayList<>();
    private int turn = 0;


    @Override
    public Civilization getOriginal() {
        Civilization civilization = new Civilization(civN,
                (CivilizationMap) GLoad.getInstance().load(new MiniCivilizationMap(), pMI), new HashMap<>(),turn);
        cRT.forEach(name -> civilization.getCivilizationResearchedTechnologies().put(TechnologyEnum.getTechnologyEnumByName(name)
                ,Technology.getTechnologyByTechnologyEnum(TechnologyEnum.getTechnologyEnumByName(name))));

        return civilization;

    }
}
