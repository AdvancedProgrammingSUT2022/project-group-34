package app.models.save;

import app.GSave;
import app.models.Technology;

import java.util.ArrayList;

public class MockTechnology extends Mock{

    private String name;
    private int remainingTerm;
    private int cost; // TODO : Using cost instead of remainingTerm
    private boolean isResearching;
    private boolean isResearchable;
    private boolean isResearched;
    private ArrayList<Integer> prerequisiteTechnologies = new ArrayList<>();

    public MockTechnology(Technology technology, Integer id) {
        super(id);
        this.name = technology.getName();
        this.remainingTerm = technology.getRemainingTerm();
        this.cost = technology.getCost();
        this.isResearching = technology.isResearching();
        this.isResearchable = technology.isResearchable();
        this.isResearched = technology.isResearched();
        technology.getPrerequisiteTechnologies().forEach(technologyEnum -> this.prerequisiteTechnologies.add(GSave.getInstance().save(technologyEnum)));
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
