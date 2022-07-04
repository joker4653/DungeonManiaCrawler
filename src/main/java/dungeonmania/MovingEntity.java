package dungeonmania;

import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {

    public MovingEntity() {
        super.setCanSpiderBeOnThisEntity(true);
    }


    public Position getCurrentLocation() {
        return super.getCurrentLocation();
    }

    public void setCurrentLocation(Position currentLocation) {
        super.setCurrentLocation(currentLocation);
    }

    

}
