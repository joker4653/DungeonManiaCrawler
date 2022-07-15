package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class key extends CollectableEntity {
    private int pairNum;

    public key(int x, int y, int key) {
        super.setCanZombieBeOnThisEntity(true);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("key");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(true);
        super.setCollectableEntity(true);

        pairNum = key;
    }

    public int getKey() {
        return pairNum;
    }
}
