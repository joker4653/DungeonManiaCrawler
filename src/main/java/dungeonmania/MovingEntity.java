package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public abstract class MovingEntity extends Entity {

    public MovingEntity() {
        super.setCanSpiderBeOnThisEntity(true);
        super.setCanZombieBeOnThisEntity(true);
        super.setMovingEntity(true);
    }

    public abstract void move(List<Entity> listOfEntities, Direction dir);


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

}
