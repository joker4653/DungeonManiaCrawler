package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Player extends MovingEntity {

    HashMap<String, Integer> ActiveStates = new HashMap<String, Integer>();

    Position prevPos;
    int allies = 0;

    public Player(int x, int y, HashMap<String, String> configMap) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("player");
        super.setCurrentLocation(new Position(x, y));
        super.setCanStepOn("player");

        setPrevPos(new Position(x, y));
        super.setPlayerHealth(Double.parseDouble(configMap.get("player_health")));
        super.setAlly(true);
    }

    public int getAllies() {
        return allies;
    }

    public void addAlly() {
        this.allies += 1;
    }

    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics) {
        Position curr = super.getCurrentLocation();
        Position next = curr.translateBy(dir);

        if (legalMove(listOfEntities, next, inventory, statistics)) {
            super.setCurrentLocation(next);
        }
        
    }

    private boolean legalMove(List<Entity> listOfEntities, Position next, Inventory inventory, Statistics statistics) {

        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(next)).collect(Collectors.toList());

        ArrayList<Entity> items = new ArrayList<Entity>();
        for (Entity currEntity : entitiesHere) {
            if (!super.canStep(currEntity.getEntityType())) {
                return false;
            } else if (currEntity.getEntityType() == "exit") {
                statistics.reachedExit();
                ((Exit) currEntity).setExitState(true);
            } else if (currEntity.isCollectableEntity()) {
                if (currEntity.getEntityType().startsWith("bomb")) {
                    Bomb entity = (Bomb) currEntity;
                    if (entity.isUsed()) {
                        continue;
                    } else {
                        items.add(currEntity);
                    }
                } else {
                    items.add(currEntity);
                } 
            }

        }

        for (Entity curr : items) {
            inventory.addItem(curr);
            listOfEntities.remove(curr);
            
            if (curr.getEntityType() == "treasure") {
                statistics.addTreasureCollected();
            }
        }

        return true;
    }

    public Position getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Position prevPos) {
        this.prevPos = prevPos;
    }
}


