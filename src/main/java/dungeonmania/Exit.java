package dungeonmania;

import dungeonmania.util.Position;
import java.util.UUID;

public class Exit extends StaticEntity {
    private boolean exitState;
    
    public Exit(int x, int y) {
        super.setCanSpiderBeOnThisEntity(true);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("exit");
        super.setInteractable(false);
        this.setCanBlockMovement(false);
        this.setExitState(false);
    }

    public boolean isExitState() {
        return exitState;
    }

    public void setExitState(boolean exitState) {
        this.exitState = exitState;
    }
}
