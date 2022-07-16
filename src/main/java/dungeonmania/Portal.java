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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    // Check whether or not player is in a portal location in controller and THEN call this method.
    public void teleport(List<Entity> listOfEntities, Player player) {
        for (Entity p : listOfEntities) {
            if (p.getEntityType().equals("portal")) {
                if (((Portal) p ).getColour().equals(this.colour) && p != this) {
                    List<Position> currentAdj = this.getCurrentLocation().getAdjacentPositions();
                    List<Position> otherAdj = p.getCurrentLocation().getAdjacentPositions();

                    if (player.getPrevPos().equals(currentAdj.get(7))) {
                        player.setCurrentLocation(otherAdj.get(3));
                    } else if (player.getPrevPos().equals(currentAdj.get(3))) {
                        player.setCurrentLocation(otherAdj.get(7));
                    } else if (player.getPrevPos().equals(currentAdj.get(1))) {
                        player.setCurrentLocation(otherAdj.get(5));
                    } else if (player.getPrevPos().equals(currentAdj.get(5))) {
                        player.setCurrentLocation(otherAdj.get(1));
                    }
                }
            }
        }
    }
}
