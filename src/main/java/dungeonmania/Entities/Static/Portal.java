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
                    List<Position> allowablePos = otherAdj;

                    // Doing this changes the list length. Check where the Position indexes go after each .remove!
                    allowablePos.remove(0);
                    allowablePos.remove(2);
                    allowablePos.remove(4);
                    allowablePos.remove(6);
    
                    if (wallCheck(listOfEntities, allowablePos)) {
                        player.setCurrentLocation(findFreePos(allowablePos));
                    } else {
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

    // Checks if the other portal has a Wall in any of the "teleportable" cardinally adjacent positions.
    public boolean wallCheck(List<Entity> listOfEntities, List<Position> allowablePos) {
        for (Entity e : listOfEntities) {
            if (allowablePos.contains(e.getCurrentLocation()) && e.getEntityType().equals("wall")) {
                allowablePos.remove(e.getCurrentLocation());
                return true;
            }
        }

        return false;
    }

    // Loops through adjacent positions around second portal if wallCheck returns true and finds free position.
    // Returns free position if one is available. If it isn't it returns null.
    public Position findFreePos(List<Position> allowablePos) {
        return allowablePos.get(0);
    }
}
