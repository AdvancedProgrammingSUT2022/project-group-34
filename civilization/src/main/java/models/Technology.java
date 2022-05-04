package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Technology {

    public static HashMap<String,HashMap<String, String>> dataSheet = new HashMap<>();
    public static HashMap<String,Technology> allTechnologies = new HashMap<>();

    private String name;
    private int remainingTerm;
    private boolean isResearching;
    private boolean isResearchable;
    private boolean isResearched;
    private ArrayList<String> prerequisiteTechnologies;
    private ArrayList<String> relatedTechnologies;


    private Technology(HashMap<String, String> stringStringHashMap) {

        this.remainingTerm = Integer.parseInt(stringStringHashMap.get("remainingTerm"));
        this.isResearched  = false;

        int numberOfPrerequisiteTechnologies = Integer.parseInt(stringStringHashMap.get("numberOfPrerequisiteTechnologies"));
        for (int i = 0; i < numberOfPrerequisiteTechnologies; i++)
            this.prerequisiteTechnologies.add("prerequisiteTechnology" + (i + 1));

        int numberOfRelatedTechnologies = Integer.parseInt(stringStringHashMap.get("numberOfRelatedTechnologies"));
        for (int i = 0; i < numberOfRelatedTechnologies; i++)
            this.prerequisiteTechnologies.add("relatedTechnology" + (i + 1));

    }


    public static void loadDataSheet(){
        //todo : ReadFromFile
    }

    public static int createAllInstances(){

        for (HashMap<String, String> stringStringHashMap : dataSheet.values())
            allTechnologies.put(stringStringHashMap.get("name"),new Technology(stringStringHashMap));

        return 0;
    }

    public static HashMap<String, Technology> getAllTechnologiesCopy() {

        HashMap<String, Technology> allTechnologiesCopy = new HashMap<>();

        allTechnologies.forEach((name,technology)->{
            Technology technologyCopy = technology.clone();
            allTechnologiesCopy.put(name,technologyCopy);
        });

        return allTechnologiesCopy;
    }






    public Technology clone(){
        Technology technologyCopy = new Technology(dataSheet.get("Null"));

        technologyCopy.setName(this.getName());
        technologyCopy.setRemainingTerm (this.getRemainingTerm());
        technologyCopy.setResearchable  (this.isResearchable());
        technologyCopy.setResearching   (this.isResearching());
        technologyCopy.setResearched    (this.isResearched());

        for (String prerequisiteTechnology  : this.prerequisiteTechnologies)
            technologyCopy.prerequisiteTechnologies.add(prerequisiteTechnology);

        for (String relatedTechnology       : this.relatedTechnologies)
            technologyCopy.relatedTechnologies.add(relatedTechnology);

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

    public int getRemainingTerm() {
        return remainingTerm;
    }

    public void setRemainingTerm(int remainingTerm) {
        this.remainingTerm = remainingTerm;
    }

    public boolean isResearched() {
        return isResearched;
    }

    public void setResearched(boolean researched) {
        isResearched = researched;
    }




    public ArrayList<String> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public ArrayList<String> getRelatedTechnologies() {
        return relatedTechnologies;
    }




    public void removePrerequisiteTechnology(String technologyName) {
        this.prerequisiteTechnologies.remove(technologyName);
        if (this.prerequisiteTechnologies.isEmpty())
            setResearchable(true);
    }

    public void research(){
        if (isResearchable())
            setResearching(true);
    }

    public void updateTechnology(int tern){
        if (isResearching)
            this.remainingTerm--;

        if (getRemainingTerm() == 0)
            setResearched(true);
            setResearching(false);
    }

    public void updateTechnology(){
        updateTechnology(1);
    }


    public Technology getTechnologyByName(String name){
        return allTechnologies.get(name);
    }



}
