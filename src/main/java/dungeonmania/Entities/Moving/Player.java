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

    private List<HashMap<String, Integer>> activeStates = new ArrayList<HashMap<String, Integer>>();
    private List<Observer> observers = new ArrayList<Observer>();
    private String CurrentPotion;

    private transient Position prevPos;
    private int allies = 0;

    public Player(int x, int y, HashMap<String, String> configMap) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("player");
        super.setCurrentLocation(new Position(x, y));
        super.setCanStepOn("player");
        new PlayerObserver(this);

        setPrevPos(new Position(x, y));
        super.setPlayerHealth(Double.parseDouble(configMap.get("player_health")));
        super.setAlly(true);
    }

    public String getCurrentPotion() {
        return CurrentPotion;
    }

    public void setCurrentPotion(String currentPotion, List<Entity> listofEntities) {
        CurrentPotion = currentPotion;
        notifyAllObservers(listofEntities);
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

    /**
     * Adds potions to the player queue
     * @param PotionType
     * @param PotionDuration
     */
    public void addtoPotionQueue(String PotionType, int PotionDuration) {
        HashMap<String, Integer> potion = new HashMap<String, Integer>();
        potion.put(PotionType, PotionDuration);

        activeStates.add(potion);
    }

    /**
     * 
     * @return the type of potion currently active or null if no potion active
     */
    public String getCurrentPotionState() {
        if (activeStates.isEmpty()) {
            return null;
        }

        return activeStates.get(0).keySet().stream().findFirst().get();
    }

    public void decrementCurrentPotion(List<Entity> listofEntities) {
        int counter = activeStates.get(0).get(this.getEntityType());

        activeStates.get(0).put(this.getEntityType(), counter--);

        if (counter - 1 == 0) {
            activeStates.remove(0);
            this.setCurrentPotion(getCurrentPotionState(), listofEntities);
        }
    }

    public void attach(Observer o) {
        observers.add(o);		
     }
  
     public void notifyAllObservers(List<Entity> listOfEntities) {
        for (Observer o : observers) {
           o.update(CurrentPotion, listOfEntities);
        }
     } 
}


