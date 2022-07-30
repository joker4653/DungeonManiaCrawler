package dungeonmania.Entities.Collectables;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {

    public CollectableEntity() {
        super.setMovingEntity(false);
        super.setCollectableEntity(true);
        super.setInteractable(false);
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
    
}
