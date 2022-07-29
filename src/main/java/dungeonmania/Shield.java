package dungeonmania;
import dungeonmania.util.HowMany;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import dungeonmania.Entities.Inventory;
import dungeonmania.Entities.Entity;


public class Shield extends BuildableEntity{
    private int durability;
    private int defenceFactor;
    ArrayList<HowMany> Components = new ArrayList<HowMany>();
    ArrayList<HowMany> Components2 = new ArrayList<HowMany>();


    public Shield(int durability, int defenceFactor) {
        this.durability = durability;
        this.defenceFactor= defenceFactor;
        super.setEntityType("shield");
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        this.Components = getComponents();
        this.Components2 = getComponents2();
    }
    
    //player.inventory.stream().filter((t) -> t.getEntityType() ==  )

    public ArrayList<HowMany> shieldMaterialsKey() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany wood = new HowMany();
        wood.setAmount(2);
        wood.setType("wood");
        stuff.add(wood);
        HowMany key = new HowMany();
        key.setAmount(1);
        key.setType("key");
        stuff.add(key);
        return stuff;
    }

    public ArrayList<HowMany> shieldMaterialsTreasure() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany wood = new HowMany();
        wood.setAmount(2);
        wood.setType("wood");
        stuff.add(wood);
        HowMany treasure = new HowMany();
        treasure.setAmount(1);
        treasure.setType("treasure");
        stuff.add(treasure);
        return stuff;
    }

    public void BuildShield(List<Entity> listOfEntities, Inventory inventory, Entity shield) {
        Components = getComponents();
        for (HowMany component: Components) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(shield);
                return;
            }
        }
        Components2 = getComponents2();
        for (HowMany component: Components2) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(shield);
                return;
            }
        }
        return;
        
    }

    
    public ArrayList<HowMany> getComponents() {
        return shieldMaterialsTreasure();
    }
    

    
    public ArrayList<HowMany> getComponents2() {
        return shieldMaterialsKey();
    }
    
    public int getCurrDurability() {
        return durability;
    }

    public boolean isDestroyed() {
        // shouldnt expect to be < 0 but just in case
        if (durability <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getDefenceFactor() {
        return defenceFactor;
    }

    public void reduceDurabilityAfterBattle() {
        durability--;
    }

}
