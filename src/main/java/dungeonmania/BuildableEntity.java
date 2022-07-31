package dungeonmania;
import java.util.List;


import dungeonmania.Entities.Entity;
import java.util.ArrayList;
import dungeonmania.Entities.Inventory;
//import dungeonmania.util.Position;


public class BuildableEntity extends Entity{
    



public BuildableEntity() {
    super.setMovingEntity(false);

    // NEED TO WAIT FOR TUTOR RESPONSE FROM FORUM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Probably as well for builables but doesn't really matter
    
}


public boolean isMakable(Inventory inventory,String item) {
    if(item.equals("shield")) {
        Shield sh = new Shield(4, 4);
        if (sh.isBuildableshield(inventory)) {
            return true;
        }
    }
    if (item.equals("bow")) {
        Bow bow = new Bow(4);
        if (bow.isBuildableBow(inventory)) {
            return true;
        }    
    }
    if (item.equals("midnight_armour")) {
        MidnightArmour midnightArmour = new MidnightArmour(2, 2);
        if (midnightArmour.isBuildableArmour(inventory)) {
            return true;
        }    
    }
    /* 
    if (item.equals("sceptre")) {
        Sceptre sceptre = new Sceptre(4);
        if (sceptre.isBuilableSceptre(inventory)) {
            return true;
        }    
    } 
    */
    return false;
}

 
public ArrayList<String> getBuilables(Inventory inventory) {
    ArrayList<String> buildables = new ArrayList<String>();
    Shield sh = new Shield(4, 4);
    Bow bow = new Bow(4);
    if (sh.isBuildableshield(inventory)) {
        buildables.add("shield");
    }
    if (bow.isBuildableBow(inventory)) {
        buildables.add("bow");    
    }
    return buildables;
}

public void Build(List<Entity> listOfEntities, Inventory inventory,Entity buildable) {
    if(buildable.getEntityType().equals("shield")) {
        Shield sh = new Shield(4, 4);
        sh.BuildShield(listOfEntities, inventory, buildable);
    } else if (buildable.getEntityType().equals("bow")) {
        Bow bow = new Bow(4);
        bow.BuildBow(listOfEntities,inventory, bow);
    } else if (buildable.getEntityType().equals("midnight_armour")) {
        MidnightArmour midnightArmour = new MidnightArmour(2, 2);
        midnightArmour.BuildArmour(listOfEntities, inventory, midnightArmour); 
    /* 
    } else if (buildable.getEntityType().equals("sceptre")) {
        Sceptre sceptre = new Sceptre(4);
        sceptre.BuildSceptre(listOfEntities, inventory, sceptre);
    }
    */
    }
    return;
}




 







}

