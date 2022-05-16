package models;

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


    Technology(TechnologyEnum technologyEnum) {

        this.name = technologyEnum.getName();
        this.cost = technologyEnum.getCost();
        this.isResearching = technologyEnum.isResearching();
        this.isResearchable = technologyEnum.isResearchable();
        this.isResearched = technologyEnum.isResearched();
        this.prerequisiteTechnologies = new ArrayList<>();
        this.prerequisiteTechnologies.addAll(technologyEnum.getPrerequisiteTechnologies());


    }


    public static void createAllInstances(){
        for (TechnologyEnum technologyEnum : TechnologyEnum.values())
            allTechnologies.put(technologyEnum,new Technology(technologyEnum));
    }

    public static HashMap<TechnologyEnum, Technology> getAllTechnologiesCopy() {

        if (allTechnologies == null)
            createAllInstances();

        HashMap<TechnologyEnum,Technology> allTechnologiesCopy = new HashMap<>();
        assert allTechnologies != null;
        allTechnologies.forEach((key, technology) -> allTechnologiesCopy.put(key,technology.cloneTechnology()));
        return allTechnologiesCopy;
    }

    private Technology cloneTechnology() {

        Technology technologyCopy = new Technology(TechnologyEnum.Agriculture);

        technologyCopy.setName(this.getName());
        technologyCopy.setCost(this.getCost());
        technologyCopy.setRemainingTerm (this.getRemainingTerm());
        technologyCopy.setResearchable  (this.isResearchable());
        technologyCopy.setResearching   (this.isResearching());
        technologyCopy.setResearched    (this.isResearched());

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

    public void setResearching(boolean researching) {
        isResearching = researching;
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



    public void research(){
        if (isResearchable())
            setResearching(true);
    }

    public int updateTechnology(HashMap<TechnologyEnum,Technology> technologyHashMap, int tern){

        if (!isResearchable) {
            prerequisiteTechnologies.removeIf(technologyHashMap::containsKey);
            if (prerequisiteTechnologies.isEmpty()){
                isResearchable = true;
                return 1;
            }
        }

        if (isResearching) {
            this.remainingTerm -= tern;
        }
        if (remainingTerm == 0) {
            isResearched = true;
            isResearching = false;
            return 1;
        }

        return 0;
    }

    public int updateTechnology(HashMap<TechnologyEnum,Technology> technologyHashMap){
        return updateTechnology(technologyHashMap,1);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
