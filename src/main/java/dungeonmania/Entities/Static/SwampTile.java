package dungeonmania.Entities.Static;

import java.util.HashMap;
import java.util.UUID;

import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {
    public SwampTile(int x, int y, HashMap<String, String> configMap) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("swamp_tile");
        super.setCurrentLocation(new Position(x, y));
        this.setCanBlockPlayerMovement(false);
        super.setCost(Integer.parseInt(configMap.get("movement_factor") + 1)); // DOUBLE CHECKKKKKKKK!!!!!!!!!!!!!!!
    }
}
