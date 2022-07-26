package dungeonmania.Entities.Moving;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dungeonmania.Statistics;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    private transient Position spawnLocation;
    private transient Position spawnerLocation;
    
    public ZombieToast(int x, int y, HashMap<String, String> configMap) {
        super();
        this.spawnLocation = new Position(x, y);
        super.setCurrentLocation(spawnLocation);
        initialise(configMap);
    }

    public ZombieToast(int zombieSpawnerX, int zombieSpawnerY, boolean hasSpawned, HashMap<String, String> configMap) {
        super();
        this.spawnerLocation = new Position(zombieSpawnerX, zombieSpawnerY);
        initialise(configMap);
    }

    private void initialise(HashMap<String, String> configMap) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("zombie_toast");
        super.setEnemyHealth(Double.parseDouble(configMap.get("zombie_health")));
        super.setAlly(false);
        super.setCanStepOn("zombie_toast");
        super.setEnemyDamage(Double.parseDouble(configMap.get("zombie_attack")));
    }

    public void spawn(List<Entity> listOfEntities) {
        List<Position> spawnablePositions = createListOfCardinalPos(spawnerLocation);
        super.updateAvailablePosList(listOfEntities, spawnablePositions);

        // if spawnablePositions is empty, don't spawn any zombies.
        // Otherwise, get the zombie's random spawn location and add the newly spawned zombie to listOfEntities.
        if (spawnablePositions.size() == 0)
            return;

        Position spawnLocation = super.getRandPos(spawnablePositions); 
        setSpawnLocation(spawnLocation);
        listOfEntities.add(this);

        swampAffectEnemyMovement(listOfEntities);
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics) {
        super.moveRandomly(listOfEntities, dir, player, inventory, statistics);
    }

    /* Getters and Setters */

    public Position getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Position spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Position getSpawnerLocation() {
        return spawnerLocation;
    }

    public void setSpawnerLocation(Position spawnerLocation) {
        this.spawnerLocation = spawnerLocation;
    }
}
