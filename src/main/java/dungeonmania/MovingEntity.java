package dungeonmania;

import java.util.List;
import java.util.UUID;

import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity implements MovingStrategy {

    public abstract void move(List<Entity> listOfEntities);

    public MovingEntity() {
        super.setCanSpiderBeOnThisEntity(true);
    }

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
