package app.models;

import java.util.HashMap;

public class Technology {


    public static final HashMap<TechnologyEnum,Technology> allTechnologies = new HashMap<>();

    private String name;

    public String getName() {
        return name;
    }

    public static Technology getTechnologyByTechnologyEnum(TechnologyEnum technologyEnum){
        return  allTechnologies.get(technologyEnum);
    }

}
