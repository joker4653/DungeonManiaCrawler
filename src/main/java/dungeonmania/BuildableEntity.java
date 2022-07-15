package dungeonmania;
import java.util.List;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.HowMany;

import java.util.ArrayList;
//import dungeonmania.util.Position;


public class BuildableEntity extends Entity{
   



public BuildableEntity() {
    super.setMovingEntity(false);

    // NEED TO WAIT FOR TUTOR RESPONSE FROM FORUM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Probably as well for builables but doesn't really matter
    super.setCanSpiderBeOnThisEntity(true);
    super.setCanZombieBeOnThisEntity(true);
    super.setCanMercBeOnThisEntity(true);
}

public boolean isBuildable(ArrayList<HowMany> components,List<Entity> inventory) {
    for (HowMany component: components) {
        if(!numItemExists(component.getType(), component.getAmount(), inventory)) {
            return false;
        }
    }
    return true;

}

public void Build(ArrayList<HowMany> components,List<Entity> inventory,BuildableEntity buildable) {
    for (HowMany component: components) {
        if(numItemExists(component.getType(), component.getAmount(), inventory)) {
            numItemDelete(component.getType(), component.getAmount(), inventory);
        }
    }
    inventory.add(buildable);
}

// For checking if requisite number of a certain item exists
public boolean numItemExists(String type, int num,List<Entity> inventory) {
    int checkNum = 0;
    for (Entity entity : inventory) {
        if (entity.getEntityType().equalsIgnoreCase(type)) {
           checkNum = checkNum + 1;
           if (checkNum == num) {
            return true;
           }
        }
    }
    return false;    
}

public void numItemDelete(String type, int num,List<Entity> inventory){
    int checkNum = 0;
    List<Entity> itemsToDelete = new ArrayList<>();
    for (Entity entity : inventory) {
        if (entity.getEntityType().equalsIgnoreCase(type)) {
           itemsToDelete.add(entity);
           checkNum = checkNum + 1;
           if (checkNum == num) {
            break;
           }
        }
    }
    for(Entity entity2: itemsToDelete) {
        inventory.remove(entity2);
    }
    return;

}





 







}


