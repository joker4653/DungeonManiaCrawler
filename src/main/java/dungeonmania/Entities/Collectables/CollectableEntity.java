package dungeonmania.Entities.Collectables;

import java.util.List;

import dungeonmania.Entities.Entity;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {
    private boolean isConsumable;

    public CollectableEntity() {
        super.setMovingEntity(false);
        super.setCollectableEntity(true);
        super.setInteractable(false);
    }

    public void setIsConsumable(boolean boo) {
        isConsumable = boo;
    }

    public boolean isConsumable() {
        return isConsumable;
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
