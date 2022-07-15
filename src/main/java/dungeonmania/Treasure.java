package dungeonmania;

import java.util.List;
import java.util.UUID;

import dungeonmania.util.Position;

public class Treasure extends CollectableEntity {
    private Position location;


    public Treasure(int x, int y) {
        location = new Position(x, y);

        //super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("treasure");
        super.setCurrentLocation(location);
        super.setCollectableEntity(true);
        super.setIsConsumable(false);
    }

    public Position getPosition() {
        return location;
    }

    public void onPickup() {
        
    }
}
