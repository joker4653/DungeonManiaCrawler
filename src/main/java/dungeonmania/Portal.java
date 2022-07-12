package dungeonmania;

import java.util.UUID;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class Portal extends StaticEntity {
    private String colour;

    public Portal(int x, int y, String colour) {
        super.setCanZombieBeOnThisEntity(false);
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("portal");
        super.setCurrentLocation(new Position(x, y));
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockMovement(false);
        this.colour = colour;
    }

    public void teleport(List<Entity> listOfEntities, Player player) {
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("portal"))
                if ((Portal)currEntity.) {
            }
        }
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
