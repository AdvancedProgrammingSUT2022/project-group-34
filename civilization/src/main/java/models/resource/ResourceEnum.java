package models.resource;

public enum ResourceEnum {

    Banana  ("Banana"),
    Cattle  ("Cattle"),
    Deer    ("Deer"),
    Sheep   ("Sheep"),
    Wheat   ("Wheat"),

    Coal    ("Coal"),
    Horses  ("Horse"),
        Iron    ("Iron"),

    Cotton  ("Cotton"),
    Dyes("Dyes"),
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

    ResourceEnum(String name) {
        this.name = name;
    }

}
