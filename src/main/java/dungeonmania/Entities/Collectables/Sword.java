package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.util.Position;

public class Sword extends CollectableEntity {
    private int durability;
    private int attackFactor;

    public Sword(int x, int y, int durability, int attackFactor) {
        super();
        this.durability = durability;
        this.attackFactor = attackFactor;
        
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("sword");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
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

    public int getAttackFactor() {
        return attackFactor;
    }

    public void reduceDurability() {
        durability--;
    }
}
