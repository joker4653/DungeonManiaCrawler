package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isLocked;

    // remove!!!!!!!!!!!!!!!
    private int key;

    public Door(int x, int y, int key) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("door");
        super.setCurrentLocation(new Position(x, y));
        
        // remove
        this.key = key;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        if (!isLocked) {
            super.setEntityType("door_open");
        }

        this.isLocked = isLocked;
    }

}
