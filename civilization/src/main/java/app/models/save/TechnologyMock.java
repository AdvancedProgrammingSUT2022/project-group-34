package app.models.save;

import app.models.Technology;
import app.models.TechnologyEnum;

import java.util.ArrayList;

public class TechnologyMock extends Mock{

    private String name;
    private int remainingTerm;
    private int cost; // TODO : Using cost instead of remainingTerm
    private boolean isResearching;
    private boolean isResearchable;
    private boolean isResearched;
    private ArrayList<String> prerequisiteTechnologies = new ArrayList<String>();

    public TechnologyMock(Technology technology, Integer id) {
        super(id);
        this.name = technology.getName();
        this.remainingTerm = technology.getRemainingTerm();
        this.cost = technology.getCost();
        this.isResearching = technology.isResearching();
        this.isResearchable = technology.isResearchable();
        this.isResearched = technology.isResearched();
        technology.getPrerequisiteTechnologies().forEach(technologyEnum -> this.prerequisiteTechnologies.add(technologyEnum.getName()));
    }

    public TechnologyMock() {
        super(0);
    }

    @Override
    public Technology getOriginalObject() {

        Technology technology = Technology.getOneInstance();

        technology.setName(this.name);
        technology.setRemainingTerm(this.remainingTerm);
        technology.setCost(this.cost);
        technology.setResearchable(this.isResearchable);
        technology.setResearching(this.isResearching);
        technology.setResearched(this.isResearched);

        ArrayList<TechnologyEnum> prerequisiteTechnologies = new ArrayList<>();
        this.prerequisiteTechnologies.forEach(name -> prerequisiteTechnologies.add(TechnologyEnum.getTechnologyEnumByName(name)));
        technology.setPrerequisiteTechnologies(prerequisiteTechnologies);

        return null;
    }
}
