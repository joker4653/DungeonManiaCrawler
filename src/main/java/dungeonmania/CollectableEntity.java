package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {
    private boolean isConsumable;

    public CollectableEntity() {
        // NEED TO WAIT FOR TUTOR RESPONSE FROM FORUM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        super.setCanSpiderBeOnThisEntity(true);
        super.setCanZombieBeOnThisEntity(true);
        super.setCanMercBeOnThisEntity(true);
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
    
    public void setInteractable(boolean isInteractable) {
        super.setInteractable(isInteractable);
    }

    public void PlaceObject(List<Entity> entities) {
        entities.add(this);
    }

    public void removeObject(List<Entity> inventory) {
        inventory.add(this);
    }
}
