package dungeonmania.Entities.Moving;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

import dungeonmania.util.Position;
import dungeonmania.Statistics;
import dungeonmania.StepOnJson;
import dungeonmania.Battling.EnemyBattleStrategy.EnemyBattlingStrategy;
import dungeonmania.Battling.EnemyBattleStrategy.NoBattlingStrategy;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.util.Direction;

public abstract class MovingEntity extends Entity {

    private ArrayList<String> canStepOn;
    private double playerHealth;
    private double enemyHealth;
    private boolean isAlly;
    private int tickCountOnSwampTile;
    private int movementFactor;
    private double enemyDamage;

    public MovingEntity() {
        super.setMovingEntity(true);
        this.tickCountOnSwampTile = 0; // 0 means the entity is not on the tile. If count is 1+, it means it is on the tile.
    }

    public abstract void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics);

    public Position getCurrentLocation() {
        return super.getCurrentLocation();
    }

    public void setCurrentLocation(Position currentLocation) {
        super.setCurrentLocation(currentLocation);
    }  
    
    public void setEntityID(String entityID) {
        super.setEntityID(entityID);
    }
    
    public void setInteractable(boolean isInteractable) {
        super.setInteractable(isInteractable);
    }

    public void setCanStepOn(String type) {
        this.canStepOn = StepOnJson.getStepLogic(type);
    }

    public boolean canStep(String type) {
        for (String legalType : this.canStepOn) {
            if (type.equals(legalType)) {
                return true;
            }
        }
        
        return false;
    }

    public double getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(double playerHealth) {
        this.playerHealth = playerHealth;
    }

    public double getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(double enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean isAlly) {
        this.isAlly = isAlly;
    }

    // returns a random position from a list of possible locations.
    // used by zombies, spiders and mercenaries.
    public Position getRandPos(List<Position> possibleLocations) {
        if (possibleLocations.size() <= 0)
            return this.getCurrentLocation();

        Random rand = new Random();
        int randNum = rand.nextInt(possibleLocations.size());
        Position location = possibleLocations.get(randNum);
        super.setCurrentLocation(location);

        return location;
    }

    // creates and returns a list of all cardinally adjacent positions
    public List<Position> createListOfCardinalPos(Position currPos) {
        Position up = new Position(currPos.getX(), currPos.getY() - 1);
        Position down = new Position(currPos.getX(), currPos.getY() + 1);
        Position left = new Position(currPos.getX() - 1, currPos.getY());
        Position right = new Position(currPos.getX() + 1, currPos.getY());

        return new ArrayList<>(Arrays.asList(left, right, up, down));
    }

    // after a moving entity moves, update its position in listOfEntities
    public void updatePosAfterMove(List<Entity> listOfEntities, Position nextPosition, String id) {
        Entity entity = listOfEntities.stream()
                                      .filter(e -> e.getEntityID().equalsIgnoreCase(id))
                                      .findFirst()
                                      .get();

        if (entity != null)
            entity.setCurrentLocation(nextPosition);
    }

    public void moveRandomly(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics) {
        // before the entity moves, the entity may be already stuck on the swamp tile
        swampAffectEnemyMovement(listOfEntities);
        if (tickCountOnSwampTile > 0) // if the entity is stuck on the swamp tile, they can't move, so return early.
            return;

        List<Position> moveLocations = createListOfCardinalPos(getCurrentLocation());
        updateAvailablePosList(listOfEntities, moveLocations);

        // update this entity's position in the listOfEntities
        Position newPosition = getRandPos(moveLocations);
        updatePosAfterMove(listOfEntities, newPosition, getEntityID());

        // after the entity moves, they may end up on a swamp tile.
        swampAffectEnemyMovement(listOfEntities);
    }

    // updates a list of positions that moving entities (e.g. zombies and hydras) can be on
    public void updateAvailablePosList(List<Entity> listOfEntities, List<Position> positions) {
        for (Entity currEntity : listOfEntities) {
            Position currEntityPosition = currEntity.getCurrentLocation();
            if (positions.contains(currEntityPosition) && !canStep(currEntity.getEntityType()))
                positions.remove(currEntityPosition);
        }
    }

    // swamp tiles affect enemy movement
    public void swampAffectEnemyMovement(List<Entity> listOfEntities) {
        if (tickCountOnSwampTile >= 0 && tickCountOnSwampTile <= movementFactor && isEnemyOnSwamp(listOfEntities)) {
            tickCountOnSwampTile++;
        } else {
            tickCountOnSwampTile = 0; // the enemy is off the swamp tile.
        }
    }

    private boolean isEnemyOnSwamp(List<Entity> listOfEntities) {
        Position currEnemyPos = getCurrentLocation();

        return listOfEntities.stream()
                             .anyMatch(e -> e.getCurrentLocation().equals(currEnemyPos) && e.getEntityType().equalsIgnoreCase("swamp_tile"));
    }

    public int getTickCountOnSwampTile() {
        return tickCountOnSwampTile;
    }

    public void setTickCountOnSwampTile(int tickCountOnSwampTile) {
        this.tickCountOnSwampTile = tickCountOnSwampTile;
    }

    public int getMovementFactor() {
        return movementFactor;
    }

    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
    }

    // returns enemy's attack
    public double getEnemyDamage() {
        return enemyDamage;
    }

    public void setEnemyDamage(double enemyDamage) {
        this.enemyDamage = enemyDamage;
    }

    // returns enemy's DELTA health
    public double getDeltaEnemyHealth(double playerAttack) {
        return playerAttack / 5;
    }
}
