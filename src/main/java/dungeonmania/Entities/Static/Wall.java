package dungeonmania.Entities.Static;

import java.util.UUID;

import dungeonmania.util.Position;

public class Wall extends StaticEntity {
    
    public Wall(int x, int y) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("wall");
        super.setCurrentLocation(new Position(x, y));
        this.setCanBlockPlayerMovement(true);
    }
    
}
