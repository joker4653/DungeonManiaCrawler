package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.util.Position;

public class InvisibilityPotion extends CollectableEntity {
    Integer PotionDur;

    public InvisibilityPotion(int x, int y) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("invisibility_potion");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
        super.setConsumable(true);
    }
}
