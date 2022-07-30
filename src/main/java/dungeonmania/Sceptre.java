package dungeonmania;
import dungeonmania.util.HowMany;
import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;

public class Sceptre extends BuildableEntity{
    int mind_control_duration;
    ArrayList<HowMany> Components1 = new ArrayList<HowMany>();
    ArrayList<HowMany> Components2 = new ArrayList<HowMany>();
    ArrayList<HowMany> Components3 = new ArrayList<HowMany>();
    ArrayList<HowMany> Components4 = new ArrayList<HowMany>();
    
    public Sceptre(int duration) {
        this.mind_control_duration = duration;
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("sceptre");
        this.Components1 = getComponents1();
        this.Components2 = getComponents2();
        this.Components3 = getComponents3();
        this.Components4 = getComponents4();
    }

    public ArrayList<HowMany> getComponents1() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany wood = new HowMany();
        wood.setAmount(1);
        wood.setType("wood");
        stuff.add(wood);
        HowMany treasure = new HowMany();
        treasure.setAmount(1);
        treasure.setType("treasure");
        stuff.add(treasure);
        HowMany Sunstone = new HowMany();
        Sunstone.setAmount(1);
        Sunstone.setType("sun_stone");
        stuff.add(Sunstone);
        return stuff;
    }

    public ArrayList<HowMany> getComponents2() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany arrow = new HowMany();
        arrow.setAmount(2);
        arrow.setType("arrow");
        stuff.add(arrow);
        HowMany treasure = new HowMany();
        treasure.setAmount(1);
        treasure.setType("treasure");
        stuff.add(treasure);
        HowMany Sunstone = new HowMany();
        Sunstone.setAmount(1);
        Sunstone.setType("sun_stone");
        stuff.add(Sunstone);
        return stuff;
    }

    public ArrayList<HowMany> getComponents3() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany wood = new HowMany();
        wood.setAmount(1);
        wood.setType("wood");
        stuff.add(wood);
        HowMany key = new HowMany();
        key.setAmount(1);
        key.setType("key");
        stuff.add(key);
        HowMany Sunstone = new HowMany();
        Sunstone.setAmount(1);
        Sunstone.setType("sun_stone");
        stuff.add(Sunstone);
        return stuff;
    }

    public ArrayList<HowMany> getComponents4() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany arrow = new HowMany();
        arrow.setAmount(2);
        arrow.setType("arrow");
        stuff.add(arrow);
        HowMany key = new HowMany();
        key.setAmount(1);
        key.setType("key");
        stuff.add(key);
        
        HowMany Sunstone = new HowMany();
        Sunstone.setAmount(1);
        Sunstone.setType("sun_stone");
        stuff.add(Sunstone);
        return stuff;
    }

    public void BuildSceptre(List<Entity> listOfEntities, Inventory inventory, Entity sceptre) {
        for (HowMany component: Components1) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(sceptre);
                return;
            }
        }
        
        for (HowMany component: Components2) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(sceptre);
                return;
            }
        }
        
        for (HowMany component: Components3) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(sceptre);
                return;
            }
        }

       
        for (HowMany component: Components4) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(sceptre);
                return;
            }
        }


        return;
        
    }

    public boolean isBuilableSceptre(Inventory inventory) {
        if (isBuildable(getComponents1(), inventory) || isBuildable(getComponents2(), inventory)) {
            return true;
        } else if (isBuildable(getComponents3(), inventory) || isBuildable(getComponents4(), inventory)){
            return true;
        }
        return false;
    }

    public int getDuration() {
        return this.mind_control_duration;
    }

    
        
   
}
