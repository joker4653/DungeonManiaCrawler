package dungeonmania.Entities.Static;

import java.util.UUID;

import dungeonmania.util.Position;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Moving.Player;
import dungeonmania.util.Direction;
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

    // public boolean legalMove(List<Entity> listOfEntities, Direction dir, Player player) {
    //     for (Entity currEntity : listOfEntities) {
    //         if (currEntity.getEntityType().equals("wall")) {
    //             List<Position> positions = currEntity.getCurrentLocation().getAdjacentPositions();
    //             if (positions.get(1).equals(this.getCurrentLocation()) && dir.equals(Direction.DOWN)) {
    //                 return false;
    //             } else if (positions.get(3).equals(this.getCurrentLocation()) && dir.equals(Direction.LEFT)) {
    //                 return false;
    //             } else if (positions.get(5).equals(this.getCurrentLocation()) && dir.equals(Direction.UP)) {
    //                 return false;
    //             } else if (positions.get(7).equals(this.getCurrentLocation()) && dir.equals(Direction.RIGHT)) {
    //                return false;
    //             }
    //         }
    //     }    
        
    //     return true;
    // }


    public void move(List<Entity> listOfEntities, Direction dir, Player player) {
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("boulder")) {
                List<Position> positions = currEntity.getCurrentLocation().getAdjacentPositions();
                if (positions.get(1).equals(player.getCurrentLocation()) && dir.equals(Direction.DOWN)) {
                    currEntity.setCurrentLocation(currEntity.getCurrentLocation().translateBy(Direction.DOWN));
                } else if (positions.get(3).equals(player.getCurrentLocation()) && dir.equals(Direction.LEFT)) {
                    currEntity.setCurrentLocation(currEntity.getCurrentLocation().translateBy(Direction.LEFT));
                } else if (positions.get(5).equals(player.getCurrentLocation()) && dir.equals(Direction.UP)) {
                    currEntity.setCurrentLocation(currEntity.getCurrentLocation().translateBy(Direction.UP));
                } else if (positions.get(7).equals(player.getCurrentLocation()) && dir.equals(Direction.RIGHT)) {
                    currEntity.setCurrentLocation(currEntity.getCurrentLocation().translateBy(Direction.RIGHT));
                }
            }
        }
    }
}