package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {
    
    public ZombieToastSpawner(int x, int y) {
        super.setCanSpiderBeOnThisEntity(true);
        super.setCanZombieBeOnThisEntity(false); // i think zombies cant spawn on these zombie toast spawners... but they can move through them (screenshots)

        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("zombie_toast_spawner");
        super.setInteractable(true);
    }

    public void spawnZombie() {
        new ZombieToast(getCurrentLocation().getX(), getCurrentLocation().getY());
    }

}
