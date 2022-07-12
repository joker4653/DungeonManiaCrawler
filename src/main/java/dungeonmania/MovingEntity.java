package dungeonmania;

import java.util.List;
import java.util.ArrayList;

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

    public abstract void move(List<Entity> listOfEntities, Direction dir, Player player);

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

}
