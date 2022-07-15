package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Wood extends CollectableEntity {


    public Wood(int x, int y) {

        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("wood");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
        super.setIsConsumable(false);
    }
}
