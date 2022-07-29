package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.util.Position;

public class Arrow extends CollectableEntity {

    public Arrow(int x, int y) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("arrow");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
    }
}
