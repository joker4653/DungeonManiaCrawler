package dungeonmania.Entities.Static;

import java.util.List;
import java.util.UUID;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Moving.Player;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String colour;

    public Portal(int x, int y, String colour) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("portal");
        super.setCurrentLocation(new Position(x, y));
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

                    // Maybe just check if theres a wall in otherAdj and then if there is 
                    // make a new large else branch below with the walls removed from otherAdj via a helper function
                    // and then just set player position to the first available position in that new modified list.
                    if (wallCheck(listOfEntities, otherAdj) == false) {
                        if (player.getPrevPos().equals(currentAdj.get(7))) {
                            player.setCurrentLocation(otherAdj.get(3));
                        } else if (player.getPrevPos().equals(currentAdj.get(3))) {
                            player.setCurrentLocation(otherAdj.get(7));
                        } else if (player.getPrevPos().equals(currentAdj.get(1))) {
                            player.setCurrentLocation(otherAdj.get(5));
                        } else if (player.getPrevPos().equals(currentAdj.get(5))) {
                            player.setCurrentLocation(otherAdj.get(1));
                        }
                    } else if (wallCheck(listOfEntities, otherAdj)) {
                        player.setCurrentLocation(findFreePos(otherAdj));
                    }
                }
            }
        }
    }

    public boolean wallCheck(List<Entity> listOfEntities, List<Position> otherAdj) {
        for (Entity e : listOfEntities) {
            if (otherAdj.contains(e.getCurrentLocation()) && e.getEntityType().equals("wall")) {
                otherAdj.remove(e.getCurrentLocation());
                return true;
            }
        }
        return false;
    }

    // Loops through adjacent positions around second portal if wallCheck returns true and finds free position.
    // Returns free position if one is available. If it isn't it returns null.
    public Position findFreePos(List<Position> otherAdj) {
        otherAdj.remove(0);
        otherAdj.remove(2);
        otherAdj.remove(4);
        otherAdj.remove(6);

        return otherAdj.get(0);
    }
}
