package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Wall extends StaticEntity {
    
    public Wall(int x, int y) {
        super.setCanZombieBeOnThisEntity(false);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("wall");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockPlayerMovement(true);
    }
    
}
