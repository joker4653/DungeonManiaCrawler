package dungeonmania;

import dungeonmania.util.HowMany;
import dungeonmania.util.Position;
import dungeonmania.BuildableEntity;
import java.util.List;

public abstract class Entity {

    private Position currentLocation;
    private String entityID;
    private String entityType;
    private boolean isInteractable;
    private boolean canSpiderBeOnThisEntity;
    private boolean canZombieBeOnThisEntity;
    private boolean isMovingEntity;
    private boolean canMercBeOnThisEntity;
    private boolean isCollectableEntity;

    /* Getters and Setters */

    public String getEntityID() {
        return entityID;
    }

    public boolean isCollectableEntity() {
        return isCollectableEntity;
    }

    public void setCollectableEntity(boolean isCollectableEntity) {
        this.isCollectableEntity = isCollectableEntity;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public boolean getCanSpiderBeOnThisEntityBool() {
        return canSpiderBeOnThisEntity;
    }

    public void setCanSpiderBeOnThisEntity(boolean canSpiderBeOnThisEntity) {
        this.canSpiderBeOnThisEntity = canSpiderBeOnThisEntity;
    }

    public Position getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Position currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public void setCanZombieBeOnThisEntity(boolean canZombieBeOnThisEntity) {
        this.canZombieBeOnThisEntity = canZombieBeOnThisEntity;
    }

    public boolean getCanZombieBeOnThisEntityBool() {
        return canZombieBeOnThisEntity;
    }

    public boolean isMovingEntity() {
        return isMovingEntity;
    }

    public void setMovingEntity(boolean isMovingEntity) {
        this.isMovingEntity = isMovingEntity;
    }

    public boolean getCanMercBeOnThisEntityBool() {
        return canMercBeOnThisEntity;
    }

    public void setCanMercBeOnThisEntity(boolean canMercBeOnThisEntity) {
        this.canMercBeOnThisEntity = canMercBeOnThisEntity;
    }

    public void BuildItem(List<Entity> listOfEntities, Inventory inventory,Entity entity) {
        BuildableEntity itemTobuild = new BuildableEntity();
        itemTobuild.Build(listOfEntities, inventory, entity);
    }
}
