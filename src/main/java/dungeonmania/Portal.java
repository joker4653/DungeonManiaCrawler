package dungeonmania;

import java.util.List;
import java.util.UUID;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String colour;

    public Portal(int x, int y, String colour) {
        super();
        super.setCanZombieBeOnThisEntity(false);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("portal");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockPlayerMovement(false);
        this.colour = colour;
    }
}
