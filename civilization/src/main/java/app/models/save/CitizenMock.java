package app.models.save;

import app.controllers.singletonController.GLoad;
import app.controllers.singletonController.GSave;
import app.models.Citizen;
import app.models.tile.Tile;

public class CitizenMock extends Mock {

    private boolean isWorking;
    private Integer workPosition;

    public CitizenMock(Citizen citizen, Integer id) {
        super(id);
        isWorking = citizen.isWorking();
        workPosition = GSave.getInstance().save(citizen.getWorkPosition());

    }

    public CitizenMock() {
        super(0);
    }

    @Override
    public Citizen getOriginalObject() {
        Citizen citizen = new Citizen(null);
        citizen.setWorking(this.isWorking);
        citizen.setWorkPosition((Tile) GLoad.getInstance().load(this,this.workPosition));
        return null;
    }
}
