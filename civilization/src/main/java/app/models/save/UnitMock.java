package app.models.save;

import app.models.unit.Unit;

public class UnitMock extends Mock {
    public UnitMock(Unit unit, Integer id) {
        super(id);
        //todo
    }

    public UnitMock() {
        super(0);
    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
