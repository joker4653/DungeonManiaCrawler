package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {

    private Position currentLocation;
    private String entityType;
    private boolean canSpiderBeOnThisEntity;


    /* Getters and Setters */

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
    
}
