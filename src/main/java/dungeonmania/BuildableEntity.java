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

public boolean isMakable(Inventory inventory,String item) {
    if(item == "shield") {
        Shield sh = new Shield(4, 4);
        if (isBuildable(sh.Components, inventory) || isBuildable(sh.Components2, inventory)) {
            return true;
        }
    }
    if (item == "bow") {
        Bow bow = new Bow(4);
        if (isBuildable(bow.Components,inventory)) {
            return true;
        }    
    }
    return false;
}

 
public ArrayList<String> getBuilables(Inventory inventory) {
    ArrayList<String> buildables = new ArrayList<String>();
    Shield sh = new Shield(4, 4);
    Bow bow = new Bow(4);
    if (sh.isBuildable(sh.getComponents(), inventory)) {
        buildables.add("shield");
    } else if (sh.isBuildable(sh.getComponents2(), inventory)) {
        buildables.add("shield");
    }   
    if (bow.isBuildable(bow.Components, inventory)) {
        buildables.add("bow");    
    }
    return buildables;
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




 







}

