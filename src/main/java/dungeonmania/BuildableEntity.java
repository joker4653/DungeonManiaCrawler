package dungeonmania;
import java.util.List;

import dungeonmania.util.HowMany;
import dungeonmania.Entities.Entity;
import java.util.ArrayList;
import dungeonmania.Entities.Inventory;
//import dungeonmania.util.Position;


public class BuildableEntity extends Entity{
    



public BuildableEntity() {
    super.setMovingEntity(false);

    // NEED TO WAIT FOR TUTOR RESPONSE FROM FORUM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Probably as well for builables but doesn't really matter
    
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
        if (sh.isBuildableshield(inventory)) {
            return true;
        }
    }
    if (item == "bow") {
        Bow bow = new Bow(4);
        if (isBuildable(bow.Components,inventory)) {
            return true;
        }    
    }
    if (item == "midnight_armour") {
        MidnightArmour midnightArmour = new MidnightArmour(2, 2);
        if (isBuildable(midnightArmour.Components,inventory)) {
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
    } else if (buildable.getEntityType() == "midnight_armour") {
        MidnightArmour midnightArmour = new MidnightArmour(2, 2);
        midnightArmour.BuildArmour(listOfEntities, inventory, midnightArmour); 
    }
    return;
}




 







}

