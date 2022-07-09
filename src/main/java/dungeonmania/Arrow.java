package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Arrow extends CollectableEntity {
    private Position location;


    public Arrow(int x, int y) {
        location = new Position(x, y);

        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("arrow");
        super.setCurrentLocation(location);

        super.setIsConsumable(false);
    }

    public Position getPosition() {
        return location;
    }

    public void onPickup() {
        
    }
}
