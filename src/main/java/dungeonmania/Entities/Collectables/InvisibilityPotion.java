package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.Entities.Moving.Player;
import dungeonmania.util.Position;

public class InvisibilityPotion extends CollectableEntity {
    private int potionDuration;

    public InvisibilityPotion(int x, int y, int potionDur) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("invisibility_potion");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
        super.setConsumable(true);

        potionDuration = potionDur;
    }

    public int getPotionDuration() {
        return potionDuration;
    }

    public void setPotionDuration(int potionDuration) {
        this.potionDuration = potionDuration;
    }

    public void use(Player player) {
        player.addtoPotionQueue(this.getEntityType(), potionDuration);
    }
}
