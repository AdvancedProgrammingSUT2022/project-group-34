package models.resource;

public enum ResourceName {

    Banana  ("Banana"),
    Cattle("Cow"),
    Deer("Gazelle"),
    Sheep   ("Sheep"),
    Wheat   ("Wheat"),

    Coal    ("Coal"),
    Horses  ("Horse"),
    Iron    ("Iron"),

    Cotton  ("Cotton"),
    Dyes("Dye"),
    Furs("Fur"),
    Gems("Gemstones"),
    Gold    ("Gold"),
    Incense("Eat"),
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
