package dungeonmania;
import dungeonmania.util.HowMany;
import java.util.List;
import java.util.ArrayList;

public class Shield extends BuildableEntity{
    private int durability;
    private int defenceFactor;
    ArrayList<HowMany> Components = new ArrayList<HowMany>();
    ArrayList<HowMany> Components2 = new ArrayList<HowMany>();


    public Shield(int durability, int defenceFactor) {
        this.durability = durability;
        this.defenceFactor= defenceFactor;
        this.setComponents(shieldMaterialsTreasure());
        this.setComponents2(shieldMaterialsKey());
        super.setEntityType("shield");
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

    public void setComponents(ArrayList<HowMany> components) {
        this.Components = components;
    }
    
    public ArrayList<HowMany> getComponents() {
        return Components;
    }
    
    public void setComponents2(ArrayList<HowMany> components2) {
        Components2 = components2;
    }
    
    public ArrayList<HowMany> getComponents2() {
        return Components2;
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
