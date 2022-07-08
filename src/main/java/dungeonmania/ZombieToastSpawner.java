package dungeonmania;

import java.util.List;
import java.util.UUID;

import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {
    
    public ZombieToastSpawner(int x, int y) {
        super.setCanSpiderBeOnThisEntity(true);
        super.setCanZombieBeOnThisEntity(false);

        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("zombie_toast_spawner");
        super.setInteractable(true);
    }

    public void spawnZombie(List<Entity> listOfEntities) {
        new ZombieToast(getCurrentLocation().getX(), getCurrentLocation().getY(), false).spawn(listOfEntities);
    }

}
