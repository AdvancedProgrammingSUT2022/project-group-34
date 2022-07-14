package app.models.save;

import app.GSave;
import app.models.Citizen;

public class CitizenMock extends Mock {

    private boolean isWorking;
    private Integer workPosition;

    public CitizenMock(Citizen citizen, Integer id) {
        super(id);
        isWorking = citizen.isWorking();
        workPosition = GSave.getInstance().save(citizen.getWorkPosition());

    }

    @Override
    public Object getOriginalObject() {
        return null;
    }
}
