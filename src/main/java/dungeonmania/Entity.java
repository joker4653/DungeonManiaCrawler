package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

public abstract class Entity {

    private Position currentLocation;
    private String entityType;
    private boolean canSpiderBeOnThisEntity;
    private UUID entityID;

    /* Getters and Setters */

    public UUID getEntityID() {
        return entityID;
    }

    public void setEntityID(UUID entityID) {
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
    
}
