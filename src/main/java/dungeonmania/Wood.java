package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Wood extends CollectableEntity {


    public Wood(int x, int y) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("wood");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
        super.setIsConsumable(false);
    }
}
