package dungeonmania;
import dungeonmania.util.HowMany;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import dungeonmania.Entities.Inventory;
import dungeonmania.Entities.Entity;


public class MidnightArmour extends BuildableEntity{
    private int midnight_armour_defence;
    private int midnight_armour_attack;
    ArrayList<HowMany> Components = new ArrayList<HowMany>();

    public int getDefenceFactor() {
        return midnight_armour_defence;
    }

    public int getAttackFactor() {
        return midnight_armour_attack;
    }

    public MidnightArmour(int midnight_armour_defence, int midnight_armour_attack) {
        this.midnight_armour_defence = midnight_armour_defence;
        this.midnight_armour_attack = midnight_armour_attack;
        super.setEntityType("midnight_armour");
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        this.Components = getComponents();
        
    }

    public void BuildArmour(List<Entity> listOfEntities, Inventory inventory, MidnightArmour midnightArmour) {
        Components = getComponents();
        for (HowMany component: Components) {
            if(inventory.numitemExists(component.getType(), component.getAmount())) {
                inventory.RemovingnumItemOfType(listOfEntities,component.getAmount(), component.getType());
                inventory.addItem(midnightArmour);
                return;
            }
        }
        
    }

    public ArrayList<HowMany> getComponents() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany sword = new HowMany();
        sword.setAmount(1);
        sword.setType("sword");
        stuff.add(sword);
        HowMany sunStone = new HowMany();
        sunStone.setAmount(1);
        sunStone.setType("sun_stone");
        stuff.add(sunStone);
        return stuff;
    }
}
