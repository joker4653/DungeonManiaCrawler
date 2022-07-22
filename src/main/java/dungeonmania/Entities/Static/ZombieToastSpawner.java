package dungeonmania.Entities.Static;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Moving.ZombieToast;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {
    
    public ZombieToastSpawner(int x, int y) {
        super();
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("zombie_toast_spawner");
        super.setInteractable(true);
    }

    public void spawnZombie(List<Entity> listOfEntities, HashMap<String, String> configMap) {
        new ZombieToast(getCurrentLocation().getX(), getCurrentLocation().getY(), false, configMap).spawn(listOfEntities);
    }

}
