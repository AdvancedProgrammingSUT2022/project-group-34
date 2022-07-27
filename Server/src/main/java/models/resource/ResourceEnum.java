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
    Dyes    ("Dyes"),
    Furs    ("Furs"),
    Gems    ("Gems"),
    Gold    ("Gold"),
    Incense ("Incense"),
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

    public static ResourceEnum getResourceEnumByResource(Resource resource){
        if (resource == null)
            return null;
        for (ResourceEnum resourceEnum : ResourceEnum.values()) {
            if (resourceEnum.name.equals(resource.getName()))
                return resourceEnum;
        }
        return null;
    }
}
