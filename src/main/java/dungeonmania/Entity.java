package dungeonmania;

import java.io.Serializable;

import dungeonmania.util.Position;

public abstract class Entity implements Serializable {

    private Position currentLocation;
    private String entityID;
    private String entityType;
    private boolean isInteractable;
    private boolean isMovingEntity;
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

    public boolean isMovingEntity() {
        return isMovingEntity;
    }

    public void setMovingEntity(boolean isMovingEntity) {
        this.isMovingEntity = isMovingEntity;
    }
}
