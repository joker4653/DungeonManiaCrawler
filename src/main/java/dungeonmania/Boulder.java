package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    
    public Boulder(int x, int y) {
        super.setCanSpiderBeOnThisEntity(false);
        super.setCanZombieBeOnThisEntity(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("boulder");
        super.setInteractable(false);
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockMovement(true);
    }
}
