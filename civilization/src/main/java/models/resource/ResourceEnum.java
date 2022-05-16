package models.resource;

public enum ResourceEnum {

    Banana  ("Banana"),
    Cow     ("Cow"  ),
    Gazelle ("Gazelle"),
    Sheep   ("Sheep"),
    Wheat   ("Wheat"),

    Coal    ("Coal" ),
    Horses  ("Horse"),
    Iron    ("Iron" ),

    Cotton  ("Cotton"),
    Dye     ("Dye"  ),
    Fur     ("Fur"  ),
    Gemstones("Gemstones"),
    Gold    ("Gold" ),
    Eat     ("Eat"  ),
    Ivory   ("Ivory"),
    Marble  ("Marble"),
    Silk    ("Silk" ),
    Silver  ("Silver"),
    Sugar   ("Sugar"),
    ;

    public String name;

    ResourceEnum(String name) {
        this.name = name;
    }
}
