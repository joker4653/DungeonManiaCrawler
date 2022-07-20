package dungeonmania;

import java.util.List;
import java.util.UUID;

import dungeonmania.util.Position;

public class Treasure extends CollectableEntity {


    public Treasure(int x, int y) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("treasure");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
        super.setIsConsumable(false);
    }
}