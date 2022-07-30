package dungeonmania.Entities.Collectables;

import java.util.UUID;

import dungeonmania.util.Position;

public class InvincibilityPotion extends CollectableEntity {
    
    
    public InvincibilityPotion(int x, int y) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("invincibility_potion");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);
        super.setConsumable(true);

    }
}
