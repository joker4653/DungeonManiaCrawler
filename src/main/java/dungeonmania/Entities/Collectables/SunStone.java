package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.util.Position;

public class SunStone extends CollectableEntity {
    
    public SunStone(int x, int y) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("sun_stone");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
    }

}
