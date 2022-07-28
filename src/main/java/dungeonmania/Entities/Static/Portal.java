package dungeonmania.Entities.Static;

import java.util.ArrayList;
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
                    
                    if (player.getPrevPos().equals(currentAdj.get(7))) {
                        if (wallCheck(otherAdj.get(3), listOfEntities)) {
                            player.setCurrentLocation(findFreePos(otherAdj, listOfEntities, player).get(0));
                        } else {
                            player.setCurrentLocation(otherAdj.get(3));
                        }
                    } else if (player.getPrevPos().equals(currentAdj.get(3))) {
                        if (wallCheck(otherAdj.get(7), listOfEntities)) {
                            player.setCurrentLocation(findFreePos(otherAdj, listOfEntities, player).get(0));
                        } else {
                            player.setCurrentLocation(otherAdj.get(7));
                        }
                    } else if (player.getPrevPos().equals(currentAdj.get(1))) {
                        if (wallCheck(otherAdj.get(5), listOfEntities)) {
                            player.setCurrentLocation(findFreePos(otherAdj, listOfEntities, player).get(0));
                        } else {
                            player.setCurrentLocation(otherAdj.get(5));
                        }
                    } else if (player.getPrevPos().equals(currentAdj.get(5))) {
                        if (wallCheck(otherAdj.get(1), listOfEntities)) {
                            player.setCurrentLocation(findFreePos(otherAdj, listOfEntities, player).get(0));
                        } else {
                            player.setCurrentLocation(otherAdj.get(1));
                        }
                    }
                }
            }
        }
    }

    // public void chainTeleport(List<Entity> listOfEntities, Player player) {

    // }

    // Checks if any given position is a portal.
    // public boolean portalCheck(Position p, List<Entity> listOfEntities) {
    //     for (Entity e : listOfEntities) {
    //         if (e.getCurrentLocation().equals(p) && e.getEntityType().equals("portal")) {
    //             return true;
    //         }
    //     }

    //     return false;
    // }   

    // Checks if any given position is a wall.
    public boolean wallCheck(Position p, List<Entity> listOfEntities) {
        for (Entity e : listOfEntities) {
            if (e.getCurrentLocation().equals(p) && e.getEntityType().equals("wall")) {
                return true;
            }
        }

        return false;
    }

    // Loops through adjacent positions around second portal if wallCheck returns true and finds free position.
    public List<Position> findFreePos(List<Position> otherAdj, List<Entity> listOfEntities, Player player) {
        List<Position> free = new ArrayList<>();

        for (Position p : otherAdj) {
            if (!(wallCheck(p, listOfEntities)) && (!(p.equals(otherAdj.get(0)) || p.equals(otherAdj.get(2)) || p.equals(otherAdj.get(4)) || p.equals(otherAdj.get(6))))) {
                free.add(p);
            }
        }

        if (free.size() == 0) {
            free.add(player.getPrevPos());
        }

        return free;
    }
}
