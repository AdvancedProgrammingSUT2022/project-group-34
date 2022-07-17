package app.models.save;

import app.controllers.GLoad;
import app.controllers.GSave;
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
    private ArrayList<Integer> prerequisiteTechnologies = new ArrayList<>();

    public TechnologyMock(Technology technology, Integer id) {
        super(id);
        this.name = technology.getName();
        this.remainingTerm = technology.getRemainingTerm();
        this.cost = technology.getCost();
        this.isResearching = technology.isResearching();
        this.isResearchable = technology.isResearchable();
        this.isResearched = technology.isResearched();
        technology.getPrerequisiteTechnologies().forEach(technologyEnum -> this.prerequisiteTechnologies.add(GSave.getInstance().save(technologyEnum)));
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
        this.prerequisiteTechnologies.forEach(id -> prerequisiteTechnologies.add((TechnologyEnum) GLoad.gIn().loadEnum(id)));
        technology.setPrerequisiteTechnologies(prerequisiteTechnologies);

        return null;
    }
}
