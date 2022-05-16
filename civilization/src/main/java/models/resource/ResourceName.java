package models.resource;

import models.TechnologyEnum;
import models.tile.Improvement;

public enum ResourceName {

    Banana  ("Banana"),
    Cow     ("Cow"),
    Gazelle ("Gazelle"),
    Sheep   ("Sheep"),
    Wheat   ("Wheat"),

    Coal    ("Coal"),
    Horses  ("Horse"),
    Iron    ("Iron"),

    Cotton  ("Cotton"),
    Dye     ("Dye"),
    Fur     ("Fur"),
    Gemstones("Gemstones"),
    Gold    ("Gold"),
    Eat     ("Eat"),
    Ivory   ("Ivory"),
    Marble  ("Marble"),
    Silk    ("Silk"),
    Silver  ("Silver"),
    Sugar   ("Sugar"),
    ;

    public final String name;

    ResourceName(String name) {
        this.name = name;
    }

}
