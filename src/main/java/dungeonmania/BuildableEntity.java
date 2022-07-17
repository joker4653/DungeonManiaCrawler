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
        if(!inventory.numitemExists(component.getType(), component.getAmount())) {
            return false;
        }
    }
    return true;

}

public void Build(List<Entity> listOfEntities, Inventory inventory,Entity buildable) {
    if(buildable.getEntityType() == "shield") {
        Shield sh = new Shield(4, 4);
        sh.BuildShield(listOfEntities, inventory, buildable);
    } else if (buildable.getEntityType() == "bow") {
        Bow bow = new Bow(4);
        bow.BuildBow(listOfEntities,inventory, bow);
    }
    return;
}

// For checking if requisite number of a certain item exists
public boolean numItemExists(String type, int num,Inventory inventory) {
    if(inventory.numitemExists(type, num)) {
        return true;
    }
    
    return false;    
}



 







}

