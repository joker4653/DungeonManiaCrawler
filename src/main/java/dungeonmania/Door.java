package dungeonmania;

import java.util.UUID;


import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isLocked;

    // remove!!!!!!!!!!!!!!!
    private int key;

    public Door(int x, int y, int key) {
        super.setCanZombieBeOnThisEntity(true);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("door");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(true);

        // remove
        this.key = key;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        if (isLocked) {
            super.setCanZombieBeOnThisEntity(false);
            super.setCanMercBeOnThisEntity(false);
        } else {
            super.setEntityType("door_open");
            super.setCanZombieBeOnThisEntity(true);
            super.setCanMercBeOnThisEntity(true);
        }

        this.isLocked = isLocked;
    }

}
