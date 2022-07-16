package dungeonmania;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private boolean isLocked = true;

    private int key;

    public Door(int x, int y, int key) {
        super.setCanZombieBeOnThisEntity(false);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("door");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(false);

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

    public Entity getMatchingKey(List<Entity> entities) {
        List<Entity> keys = entities.stream().filter(e -> (e.getEntityType().startsWith("key"))).collect(Collectors.toList());

        for (Entity k : keys) {
            Akey ke = (Akey) k;
            if (ke.getKey() == this.key) {
                return ke;
            }
        }

        return null;
    }

}
