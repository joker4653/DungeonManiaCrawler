package dungeonmania;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends MovingEntity {
    public Hydra(int x, int y, HashMap<String, String> configMap) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("hydra");
        super.setEnemyHealth(Double.parseDouble(configMap.get("hydra_health")));
        super.setAlly(false);
        super.setCanStepOn("hydra");
        super.setCurrentLocation(new Position(x, y));
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory,
                     Statistics statistics) {
        super.moveRandomly(listOfEntities, dir, player, inventory, statistics);
    }
}
