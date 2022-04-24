package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Technology {

    static HashMap<String,Technology> technologies = new HashMap<>();

    public int progress;
    public ArrayList<String> prerequisites;


    public static void readFromFile(){
        //todo : ReadFromFile
    }

    public Technology(int progress, ArrayList<String> prerequisites) {
        this.progress = progress;
        this.prerequisites = prerequisites;
    }

    public void research(){
        //todo : what should I do?
    }

    public Technology getTechnologyByName(String name){
        return technologies.get(name);
    }



}
