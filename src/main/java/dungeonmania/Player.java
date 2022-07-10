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
    HashMap<String, Integer> ActiveStates = new HashMap<String, Integer>();

    public Player(int x, int y) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("player");
        super.setCurrentLocation(new Position(x, y));
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


    public void move(List<Entity> listOfEntities, Direction dir, Player player) {
        Position curr = super.getCurrentLocation();
        Position next = curr.translateBy(dir);

        if (legalMove(listOfEntities, next)) {
            super.setCurrentLocation(next);
        }
    }


    private boolean legalMove(List<Entity> listOfEntities, Position next) {
        // TODO Reimplement the checking w/ duck typing from jsons, or alternate. Currently dodge.


        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(next)).collect(Collectors.toList());

        ArrayList<Entity> items = new ArrayList<Entity>();
        for (Entity currEntity : entitiesHere) {
            if (!canStepOn(currEntity.getEntityType())) {
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

    // TEMPORARY FUNCTION!!!
    private boolean canStepOn(String type) {
        ArrayList<String> legal = new ArrayList<String>();
        legal.add("floor");
        legal.add("player");
        legal.add("exit");
        legal.add("switch");
        legal.add("portal");
        legal.add("door_open");
        legal.add("spider");
        legal.add("mercenary");
        legal.add("treasure");

        for (String legalType : legal) {
            if (type == legalType) {
                return true;
            }
        }
        
        return false;
    }

}


