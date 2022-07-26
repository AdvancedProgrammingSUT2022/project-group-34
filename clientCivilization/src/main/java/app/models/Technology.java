package app.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Technology {


    public static final HashMap<TechnologyEnum,Technology> allTechnologies = new HashMap<>();

    private String name;
    private int remainingTerm;
    private int cost; // TODO : Using cost instead of remainingTerm
    private boolean isResearching;
    private boolean isResearchable;
    private boolean isResearched;
    private ArrayList<TechnologyEnum> prerequisiteTechnologies;

    static {
        for (TechnologyEnum technologyEnum : TechnologyEnum.values())
            allTechnologies.put(technologyEnum,new Technology(technologyEnum));
    }

    Technology(TechnologyEnum technologyEnum) {

        this.name = technologyEnum.getName();
        this.cost = technologyEnum.getCost();
        this.isResearching = technologyEnum.isResearching();
        this.isResearchable = technologyEnum.isResearchable();
        this.isResearched = technologyEnum.isResearched();
        this.prerequisiteTechnologies = new ArrayList<>();
        this.prerequisiteTechnologies.addAll(technologyEnum.getPrerequisiteTechnologies());


    }

    private Technology() {

    }

    public static HashMap<TechnologyEnum, Technology> getAllTechnologiesCopy() {
        HashMap<TechnologyEnum,Technology> allTechnologiesCopy = new HashMap<>();
        allTechnologies.forEach((key, technology) -> allTechnologiesCopy.put(key,technology.cloneTechnology()));
        return allTechnologiesCopy;
    }

    public static Technology getOneInstance() {
        return new Technology();
    }

    private Technology cloneTechnology() {

        Technology technologyCopy = new Technology(TechnologyEnum.Agriculture);

        technologyCopy.setName(this.getName());
        technologyCopy.setCost(this.getCost());
        technologyCopy.remainingTerm = (remainingTerm);
        technologyCopy.isResearchable = (isResearchable);
        technologyCopy.isResearching =   (isResearching);
        technologyCopy.isResearched =   (isResearched);

        technologyCopy.prerequisiteTechnologies = new ArrayList<>();
        technologyCopy.prerequisiteTechnologies.addAll(this.prerequisiteTechnologies);

        return technologyCopy;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isResearching() {
        return isResearching;
    }

    public boolean isResearchable() {
        return isResearchable;
    }

    public void setResearchable(boolean researchable) {
        isResearchable = researchable;
    }

    public boolean isResearched() {
        return isResearched;
    }

    public void setResearched(boolean researched) {
        isResearched = researched;
    }

    public int getRemainingTerm() {
        return remainingTerm;
    }

    public void setRemainingTerm(int remainingTerm) {
        this.remainingTerm = remainingTerm;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static Technology getTechnologyByTechnologyEnum(TechnologyEnum technologyEnum){
        return  allTechnologies.get(technologyEnum);
    }

    public ArrayList<TechnologyEnum> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public void setPrerequisiteTechnologies(ArrayList<TechnologyEnum> prerequisiteTechnologies) {
        this.prerequisiteTechnologies = prerequisiteTechnologies;
    }
    public String toString() {
        return name;
    }
}