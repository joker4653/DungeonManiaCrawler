package dungeonmania;

import java.util.List;
import java.util.UUID;
import dungeonmania.util.Position;

public class Exit extends StaticEntity {
    private boolean exitState;

    public Exit(int x, int y) {
        super.setCanSpiderBeOnThisEntity(false);
        super.setCanZombieBeOnThisEntity(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("exit");
        super.setInteractable(false);
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockPlayerMovement(false); 
        this.setExitState(false);
    }

    public boolean isExitState() {
        return exitState;
    }

    public void setExitState(boolean exitState) {
        this.exitState = exitState;
    }
}
