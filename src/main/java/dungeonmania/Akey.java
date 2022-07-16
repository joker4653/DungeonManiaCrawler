package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Akey extends CollectableEntity {
    private int pairNum = 0;

    public Akey(int x, int y, int k) {
        super.setCanZombieBeOnThisEntity(true);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("key");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(true);
        super.setCollectableEntity(true);

        pairNum = k;
    }

    public int getKey() {
        return pairNum;
    }
}