package app.models.miniClass;

import app.models.unit.Unit;

public class MiniUnit extends Mini{
    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Object getOriginal() {
        return new Unit(this.name,this.index);
    }
}
