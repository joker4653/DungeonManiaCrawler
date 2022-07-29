package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.util.Position;

public class Akey extends CollectableEntity {
    private int pairNum = 0;

    public Akey(int x, int y, int k) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("key");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);

        pairNum = k;
    }

    public Integer getKey() {
        return pairNum;
    }
}