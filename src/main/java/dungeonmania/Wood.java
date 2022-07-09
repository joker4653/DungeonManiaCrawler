package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Wood extends CollectableEntity {

    private Position location;


    public Wood(int x, int y) {
        location = new Position(x, y);

        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("wood");
        super.setCurrentLocation(location);

        super.setIsConsumable(false);
    }

    public Position getPosition() {
        return location;
    }

    public void onPickup() {
        
    }
}
