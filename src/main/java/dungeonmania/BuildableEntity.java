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

public boolean isBuildable(ArrayList<HowMany> components,Inventory inventory) {
    for (HowMany component: components) {
        if(inventory.numitemExists(component.getType(), component.getAmount())) {
            return true;
        }
    }
    return false;

}

public void Build(ArrayList<HowMany> components,Inventory inventory,BuildableEntity buildable) {
    for (HowMany component: components) {
        if(numItemExists(component.getType(), component.getAmount(), inventory)) {
            numItemDelete(component.getType(), component.getAmount(), inventory);
        }
    }
    inventory.addItem(buildable);
}

// For checking if requisite number of a certain item exists
public boolean numItemExists(String type, int num,Inventory inventory) {
    if(inventory.numitemExists(type, num)) {
        return true;
    }
    
    return false;    
}

public void numItemDelete(String type, int num,Inventory inventory){
    inventory.RemovingnumItemOfType(num, type);
    return;
}





 







}


