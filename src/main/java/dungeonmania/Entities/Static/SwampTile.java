package dungeonmania.Entities.Static;

import java.util.HashMap;
import java.util.UUID;

import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    private int movementFactor;

    public SwampTile(int x, int y, HashMap<String, String> configMap, int movementFactor) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("swamp_tile");
        super.setCurrentLocation(new Position(x, y));
        this.setCanBlockPlayerMovement(false);
        this.movementFactor = movementFactor;
        super.setCost(movementFactor != -1 ? movementFactor + 1 : 1);
    }

    public int getMovementFactor() {
        return movementFactor;
    }
}
