package dungeonmania.Entities.Static;

import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.util.Position;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Moving.Player;
import dungeonmania.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Boulder extends StaticEntity {
    
    public Boulder(int x, int y) {
        super();
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("boulder");
        super.setInteractable(false);
        this.setCanBlockPlayerMovement(false);
    }

    public void move(List<Entity> listOfEntities, Direction movementDirection, Player player) {
        Position current = this.getCurrentLocation();
        Position next = current.translateBy(movementDirection);
       
        if (legalMove(listOfEntities, next)) {
            this.setCurrentLocation(next);
        }
    }

    public boolean legalMove(List<Entity> listOfEntities, Position next) {
        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(next)).collect(Collectors.toList());

        for (Entity e : entitiesHere) {
            if (e.getEntityType().equals("wall") || e.getEntityType().equals("boulder")) {
                this.setCanBlockPlayerMovement(true);
                return false;
            } else {
                this.setCanBlockPlayerMovement(false);
            }
        }

        return true;
    }
}
