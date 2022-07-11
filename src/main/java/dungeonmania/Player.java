package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Player extends MovingEntity {

    ArrayList<Entity> inventory = new ArrayList<Entity>();
    ArrayList<String> canStepOn;
    HashMap<String, Integer> ActiveStates = new HashMap<String, Integer>();


    Position prevPos;

    public Player(int x, int y) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("player");
        super.setCurrentLocation(new Position(x, y));

        setPrevPos(new Position(x, y));
        this.canStepOn = StepOnJson.getStepLogic("player");
    }


    public ArrayList<Entity> getInventory() {
        return inventory;
    } 

    public void addItem(Entity item) {
        inventory.add(item);
    }

    public void removeItem(Entity item) {
        inventory.remove(item);
    }

    public boolean itemExists(Entity item) {
        for (Entity entity : inventory) {
            if (entity.equals(item)) {
                return true;
            }
        }

        return false;
    }

    // For checking if a certain type of entity (e.g. a sword) exists.
    public boolean itemExists(String type) {
        for (Entity entity : inventory) {
            if (entity.getEntityType().equalsIgnoreCase(type)) {
                return true;
            }
        }

        return false;
    }

    public void move(List<Entity> listOfEntities, Direction dir, Player player) {
        Position curr = super.getCurrentLocation();
        Position next = curr.translateBy(dir);

        if (legalMove(listOfEntities, next)) {
            super.setCurrentLocation(next);
        }
    }


    private boolean legalMove(List<Entity> listOfEntities, Position next) {

        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(next)).collect(Collectors.toList());

        ArrayList<Entity> items = new ArrayList<Entity>();
        for (Entity currEntity : entitiesHere) {
            if (!canStep(currEntity.getEntityType())) {
                return false;
            } else if (currEntity instanceof CollectableEntity) {
                items.add(currEntity);
            }
        }

        for (Entity curr : items) {
            this.addItem(curr);
            listOfEntities.remove(curr);
        }

        return true;
    }

    private boolean canStep(String type) {
        for (String legalType : this.canStepOn) {
            if (type.equals(legalType)) {
                return true;
            }
        }
        
        return false;
    }


    public Position getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Position prevPos) {
        this.prevPos = prevPos;
    }
}


