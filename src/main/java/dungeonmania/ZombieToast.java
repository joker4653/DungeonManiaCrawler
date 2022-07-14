package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dungeonmania.EnemyBattleStrategy.ZombieBattlingStrategy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    private Position spawnLocation;
    private Position spawnerLocation;
    
    public ZombieToast(int x, int y, HashMap<String, String> configMap) {
        this.spawnLocation = new Position(x, y);
        super.setCurrentLocation(spawnLocation);
        initialise(configMap);
    }

    public ZombieToast(int zombieSpawnerX, int zombieSpawnerY, boolean hasSpawned, HashMap<String, String> configMap) {
        this.spawnerLocation = new Position(zombieSpawnerX, zombieSpawnerY);
        initialise(configMap);
    }

    private void initialise(HashMap<String, String> configMap) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("zombie_toast");
        super.enemyChangeStrategy(new ZombieBattlingStrategy(configMap));
        super.setEnemyHealth(Double.parseDouble(configMap.get("zombie_health")));
    }

    public void spawn(List<Entity> listOfEntities) {
        List<Position> spawnablePositions = createListOfCardinalPos(spawnerLocation);
        updateZombAvailablePos(listOfEntities, spawnablePositions);

        // if spawnablePositions is empty, don't spawn any zombies.
        // Otherwise, get the zombie's random spawn location and add the newly spawned zombie to listOfEntities.
        if (spawnablePositions.size() == 0)
            return;

        Position spawnLocation = super.getRandPos(spawnablePositions); 
        setSpawnLocation(spawnLocation);
        listOfEntities.add(this);

        // TODO: call the battle function if mercenary is at player's position!!!!!!!!!!!
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory) {
        List<Position> moveLocations = createListOfCardinalPos(getCurrentLocation());
        updateZombAvailablePos(listOfEntities, moveLocations);

        // update this entity's position in the listOfEntities
        Position newPosition = super.getRandPos(moveLocations);
        super.updatePosAfterMove(listOfEntities, newPosition, getEntityID());

        // TODO: call the battle function if mercenary is at player's position!!!!!!!!!!!
    }

    // updates a list of positions that zombies can be on
    private void updateZombAvailablePos(List<Entity> listOfEntities, List<Position> positions) {
        for (Entity currEntity : listOfEntities) {
            Position currEntityPosition = currEntity.getCurrentLocation();
            if (positions.contains(currEntityPosition) && !currEntity.getCanZombieBeOnThisEntityBool())
                positions.remove(currEntityPosition);
        }
    }

    /* Getters and Setters */

    public Position getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Position spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
