package dungeonmania;

import java.util.UUID;

import com.google.gson.JsonObject;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isLocked;
    private int keyID;

    public Door(int x, int y, int keyID) {
        super.setCanZombieBeOnThisEntity(false);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("door");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(false);
        this.keyID = keyID;
        this.setLocked(true);
        this.setCanBlockMovement(false);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        if (isLocked) {
            super.setCanZombieBeOnThisEntity(false);
            super.setCanMercBeOnThisEntity(false);
        } else {
            super.setCanZombieBeOnThisEntity(true);
            super.setCanMercBeOnThisEntity(true);
        }

        this.isLocked = isLocked;
    }

    public int getKeyID() {
        return keyID;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }
}
