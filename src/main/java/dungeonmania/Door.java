package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isLocked;

    public Door(int x, int y) {
        super.setCanZombieBeOnThisEntity(false);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("door");
        super.setCurrentLocation(new Position(x, y));
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        if (isLocked) {
            super.setCanZombieBeOnThisEntity(false);
        } else {
            super.setCanZombieBeOnThisEntity(true);
        }

        this.isLocked = isLocked;
    }

}
