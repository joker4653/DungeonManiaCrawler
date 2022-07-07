package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {

    private Position currentLocation;

    private String entityID;
    private String entityType;
    private boolean isInteractable;
    private boolean canSpiderBeOnThisEntity;
    
    /* Getters and Setters */

    public String getEntityID() {
        return entityID;
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

}
