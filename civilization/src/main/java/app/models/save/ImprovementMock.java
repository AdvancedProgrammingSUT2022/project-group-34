package app.models.save;

import app.models.tile.Improvement;

public class ImprovementMock extends Mock {
    public ImprovementMock(Improvement improvement, Integer id) {
        super(id);

    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
