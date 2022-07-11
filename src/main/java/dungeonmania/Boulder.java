package dungeonmania;

import java.util.UUID;
import java.util.List;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    
    public Boulder(int x, int y) {
        super.setCanSpiderBeOnThisEntity(false);
        super.setCanZombieBeOnThisEntity(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("boulder");
        super.setInteractable(false);
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockMovement(true);
    }

    // Should only be allowed on cardinally adjacent squares
    public boolean checkIfNextPositionIsAllowed(Position nextPosition, List<Entity> listOfEntities) {   
        // Loop checks if entity on next position is also a boulder (player can't push more than one boulder).
        for (Entity currEntity : listOfEntities) {
            Position currPosition = currEntity.getCurrentLocation();
            if ((currPosition.equals(nextPosition) && currEntity.getEntityType().equals("boulder")) ||  !(Position.isAdjacent(this.getCurrentLocation(), nextPosition))) {
                return false;    
            }
        }

        return true;
    }
}
