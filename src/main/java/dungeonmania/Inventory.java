package dungeonmania;

import java.util.ArrayList;

public class Inventory {

    ArrayList<Entity> inventory;

    public Inventory() {
        this.inventory = new ArrayList<Entity>();
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

    public Entity getItem(String type) {
        for (Entity entity : inventory) {
            if (entity.getEntityType().equalsIgnoreCase(type)) {
                return entity;
            }
        }

        return null;
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







}


