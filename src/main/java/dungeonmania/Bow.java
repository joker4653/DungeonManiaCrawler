package dungeonmania;
import dungeonmania.util.HowMany;
import java.util.List;
import java.util.ArrayList;

public class Bow extends BuildableEntity{

    private int durability;
    ArrayList<HowMany> Components = new ArrayList<HowMany>();

    public Bow(int durability, int defenceFactor) {
        this.durability = durability;
        this.setComponents(bowMaterials());
        super.setEntityType("bow");
    }

    public void BuildShieldTreasure(List<Entity> inventory, Bow bow) {
        //TODO: MAKE SHIELD DURABILITY RANDOM
        Build(bowMaterials(), inventory, bow);
    }

    public ArrayList<HowMany> bowMaterials() {
        ArrayList<HowMany> stuff = new ArrayList<HowMany>();
        HowMany wood = new HowMany();
        wood.setAmount(1);
        wood.setType("wood");
        stuff.add(wood);
        HowMany arrow = new HowMany();
        arrow.setAmount(3);
        arrow.setType("arrow");
        stuff.add(arrow);
        return stuff;
    }

    public void setComponents(ArrayList<HowMany> components) {
        Components = components;
    }

    public ArrayList<HowMany> getComponents() {
        return Components;
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

    public void reduceDurabilityAfterBattle() {
        durability--;
    }
}
