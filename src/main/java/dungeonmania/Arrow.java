package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Arrow extends CollectableEntity {
    private Position location;


    public Arrow(int x, int y) {
        super();
        location = new Position(x, y);

        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("arrow");
        super.setCurrentLocation(location);
        super.setCollectableEntity(true);

        super.setIsConsumable(false);
    }

}
