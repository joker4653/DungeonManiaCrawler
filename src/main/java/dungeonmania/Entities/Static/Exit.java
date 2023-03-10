package dungeonmania.Entities.Static;

import java.util.UUID;

import dungeonmania.util.Position;

public class Exit extends StaticEntity {
    private boolean exitState;

    public Exit(int x, int y) {
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("exit");
        super.setInteractable(false);
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
