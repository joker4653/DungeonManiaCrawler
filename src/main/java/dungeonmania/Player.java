package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Player extends MovingEntity {

    public Player(int x, int y) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("player");
        super.setCurrentLocation(new Position(x, y));
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

        for (Entity currEntity: listOfEntities) {
            Position currPos = currEntity.getCurrentLocation();
            if (currPos.equals(next) && !canStepOn(currEntity.getEntityType())) {
                return false;
            } 
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

        for (String legalType : legal) {
            if (type == legalType) {
                return true;
            }
        }
        
        return false;
    }

}


