package dungeonmania;

import java.util.ArrayList;
import java.util.List;


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

    // For checking if a certain type of entity (e.g. a sword) exists in a certain quantity
    public boolean numitemExists(String type,int num) {
        int count = 0;

        for (Entity entity : inventory) {
            if (entity.getEntityType().equalsIgnoreCase(type)) {
                count = count + 1;
                if (count == num) {
                    return true;
                }
            }
        }

        return false;
    }



    //For removing item of certain type from inventory
    public void RemovingItemOfType(List<Entity> listOfEntities, String type) {
        ArrayList<Entity> del= new ArrayList<>();
        if(this.itemExists(type)) {
            for (Entity entity : inventory) {
                if (entity.getEntityType().equalsIgnoreCase(type)) {
                    del.add(entity);
                    break;
                }
            }
            inventory.remove(del.get(0));
            listOfEntities.remove(del.get(0));
        }
        

        return;
    }

    //Removing number of items from inventory by type
    public void RemovingnumItemOfType(List<Entity> listOfEntities, int num,String type) {
        for(int i = 0; i != num;i++) {
            RemovingItemOfType(listOfEntities, type);
        }
        return;
    }








}


