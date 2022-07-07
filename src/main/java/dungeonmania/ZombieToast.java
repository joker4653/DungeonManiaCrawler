package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    private int currHealth;
    private int damagePoints;
    private Position spawnLocation;
    private Position spawnerLocation;

    public ZombieToast(int zombieSpawnerX, int zombieSpawnerY) {
        this.spawnerLocation = new Position(zombieSpawnerX, zombieSpawnerY);
    }

    public void spawn(List<Entity> listOfEntities) {
        Position positionAboveSpawner = new Position(spawnerLocation.getX(), spawnerLocation.getY() - 1);
        Position positionBelowSpawner = new Position(spawnerLocation.getX(), spawnerLocation.getY() + 1);
        Position positionLeftSpawner = new Position(spawnerLocation.getX() - 1, spawnerLocation.getY());
        Position positionRightSpawner = new Position(spawnerLocation.getX() + 1, spawnerLocation.getY());


        // REFACTORING: COMMON FUNCTION CALLED getListOfSpawnableEntities which returns a list checking if the next positions are valid FOR MOVING ENTITIES BELOW?????????????????????????????????????????????????????????????????????????????????????????????
        List<Position> spawnablePositions = Arrays.asList(positionAboveSpawner, positionBelowSpawner, positionLeftSpawner, positionRightSpawner);

        for (Entity currEntity : listOfEntities) {
            Position currEntityPosition = currEntity.getCurrentLocation();
            if (spawnablePositions.contains(currEntityPosition) && !currEntity.getCanZombieBeOnThisEntityBool()) {
                spawnablePositions.remove(currEntityPosition);
            }
        }

        // if spawnablePositions IS empty, don't spawn
        if (spawnablePositions.size() == 0)
            return;

        // else, this means spawnablePositions isn't empty, so get a random spawnablePosition
        Random rand = new Random();
        int randNum = rand.nextInt(spawnablePositions.size());
        Position spawnLocation = spawnablePositions.get(randNum);
        super.setCurrentLocation(spawnLocation);
        setSpawnLocation(spawnLocation);

        // add newly spawned zombie to listOfEntities
        listOfEntities.add(this);
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir) {
        Position locationAboveCurrPos = new Position(getCurrentLocation().getX(), getCurrentLocation().getY() - 1);
        Position locationBelowCurrPos = new Position(getCurrentLocation().getX(), getCurrentLocation().getY() + 1);
        Position locationLeftCurrPos = new Position(getCurrentLocation().getX() - 1, getCurrentLocation().getY());
        Position locationRightCurrPos = new Position(getCurrentLocation().getX() + 1, getCurrentLocation().getY());

        List<Position> moveLocations = Arrays.asList(locationAboveCurrPos, locationBelowCurrPos, locationLeftCurrPos, locationRightCurrPos, getCurrentLocation());
        
        // REFACTORING: can also create a common function in MovingEntity --> it checks if the positions are any of the ones in the moveLocations list and if the zombieToast can move there. Then, it returns the modified list
        for (Entity currEntity : listOfEntities) {
            Position currEntityPosition = currEntity.getCurrentLocation();
            if (moveLocations.contains(currEntityPosition) && !getCanZombieBeOnThisEntityBool()) {
                moveLocations.remove(currEntityPosition);
            }
        }

        Random rand = new Random();
        int randNum = rand.nextInt(moveLocations.size());
        Position newPosition = moveLocations.get(randNum);
        super.setCurrentLocation(newPosition);
        String zombieID = this.getEntityID();

        // update this entity's position in the listOfEntities
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityID().equals(zombieID))
                currEntity.setCurrentLocation(newPosition);
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
