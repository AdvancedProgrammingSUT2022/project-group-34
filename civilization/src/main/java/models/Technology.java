package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Technology {

    public static ArrayList<HashMap<String, String>> dataSheet = new ArrayList<>();
    static HashMap<String,Technology> allTechnologies = new HashMap<>();

    public int progress;
    public ArrayList<String> prerequisites;


    public Technology(HashMap<String, String> stringStringHashMap) {

        this.progress = 0;
        int numberOfPrerequisites = Integer.parseInt(stringStringHashMap.get("numberOfPrerequisites"));
        for (int i = 0; i < numberOfPrerequisites; i++)
            this.prerequisites.add("Technology" + (i + 1));

    }






    public static void loadDataSheet(){
        //todo : ReadFromFile
    }

    public static int createAllInstances(){

        for (HashMap<String, String> stringIntegerHashMap : dataSheet)
            allTechnologies.put(stringIntegerHashMap.get("name"),new Technology(stringIntegerHashMap));

        return 0;
    }






    public void research(){
        //todo : what should I do?
    }

    public Technology getTechnologyByName(String name){
        return allTechnologies.get(name);
    }



}
