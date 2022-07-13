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





 







}


