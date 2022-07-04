package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {

    private Position currentLocation;

    private boolean canSpiderBeOnThisEntity;

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
