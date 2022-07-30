package dungeonmania.Entities.Moving;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.util.Position;
import dungeonmania.Statistics;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.Entities.Collectables.Bomb;
import dungeonmania.Entities.Static.Exit;
import dungeonmania.util.Direction;

public class Player extends MovingEntity {

    private HashMap<String, Integer> activeStates = new HashMap<String, Integer>();

    private transient Position prevPos;
    private int allies = 0;

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
        super.setEnemyDamage(Double.parseDouble(configMap.get("player_attack")));
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

        if (legalMove(listOfEntities, next, inventory, statistics, player)) {
            super.setCurrentLocation(next);
        }
        
    }

    private boolean legalMove(List<Entity> listOfEntities, Position next, Inventory inventory, Statistics statistics, Player player) {

        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(next)).collect(Collectors.toList());

        List<Entity> inv = inventory.getInventory().stream().filter(e -> e.getEntityType().equals("key")).collect(Collectors.toList());

        ArrayList<Entity> items = new ArrayList<Entity>();
        for (Entity currEntity : entitiesHere) {
            if (!super.canStep(currEntity.getEntityType())) {
                return false;
            } else if (currEntity.getEntityType().equals("exit")) {
                statistics.reachedExit();
                ((Exit) currEntity).setExitState(true);
            } else if (currEntity.isCollectableEntity()) {
                if (currEntity.getEntityType().equals("key") && !inv.isEmpty()) {
                    
                } else {
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
            } else {
                // Player can step here and is not on exit.
                statistics.notOnExit();
            }

        }

        
        for (Entity curr : items) {
            inventory.addItem(curr);
            listOfEntities.remove(curr);
            
            if (curr.getEntityType().equals("treasure") || curr.getEntityType().equals("sun_stone")) {
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


