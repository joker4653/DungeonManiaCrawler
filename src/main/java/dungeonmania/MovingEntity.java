package dungeonmania;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

import dungeonmania.util.Position;
import dungeonmania.EnemyBattleStrategy.EnemyBattlingStrategy;
import dungeonmania.EnemyBattleStrategy.NoBattlingStrategy;
import dungeonmania.util.Direction;

public abstract class MovingEntity extends Entity {

    private ArrayList<String> canStepOn;
    private EnemyBattlingStrategy enemyStrategy;
    private double playerHealth;
    private double enemyHealth;

    public MovingEntity() {
        super.setCanSpiderBeOnThisEntity(true);
        super.setCanZombieBeOnThisEntity(true);
        super.setCanMercBeOnThisEntity(true);
        super.setMovingEntity(true);
    }

    public abstract void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory);

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

    public double enemyAttackModifier() {
        return enemyStrategy.attackModifier();
    }

    public double calculateEnemyHealth(double playerDmg) {
        return enemyStrategy.calculateEnemyHealth(playerDmg);
    }

    public void enemyChangeStrategy(EnemyBattlingStrategy newStrategy) {
        this.enemyStrategy = newStrategy;
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

}
