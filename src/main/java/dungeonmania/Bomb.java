package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.util.Position;

public class Bomb extends CollectableEntity {
    private int radius;
    private boolean used = false;


    public Bomb(int x, int y, int radius) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("bomb");
        super.setCurrentLocation(new Position(x, y));
        super.setCollectableEntity(true);

        this.radius = radius;
    }

    public void use(Player play, List<Entity> listOfEntities, Inventory inventory) {
        

        // now need to place bomb on map at location of player
        // add to list of entities 

        used = true;
        super.setCurrentLocation(play.getCurrentLocation());
        listOfEntities.add(this);

        checkBombStatus(listOfEntities, play);

    }

    public void checkBombStatus(List<Entity> listOfEntities, Player play) {
        List<Position> positions = getSquarePositions();
        List<Entity> EntsinSquare = getEntitesInSquare(positions, listOfEntities);

        if (ifAdjacentToActive(listOfEntities)) {
            destroySurroundingEntities(play, EntsinSquare, listOfEntities);
            listOfEntities.remove(this);
        }
    }

    public void destroySurroundingEntities(Player play, List<Entity> EntsinSquare, List<Entity> listOfEntities) {
        ArrayList<Entity> toRemove = new ArrayList<Entity>();

        for (Entity e : listOfEntities) {
            if (e == play) {
                continue;
            } else if (EntsinSquare.contains(e)) {
                toRemove.add(e);
            }
        }

        for (Entity e : toRemove) {
            if (EntsinSquare.contains(e)) {
                listOfEntities.remove(e);
            }
        }
    }

    private List<Position> getSquarePositions() {
        ArrayList<Position> positions = new ArrayList<>();

        // get square locations around the player
        if (radius == 1) {
            return super.getCurrentLocation().getAdjacentPositions();
        } else { 
            for (int x = super.getCurrentLocation().getX() - radius; x <= super.getCurrentLocation().getX() + radius; x++) {
                for (int y = super.getCurrentLocation().getY() - radius; y <= super.getCurrentLocation().getX() + radius; y++) {
                    positions.add(new Position(x, y));
                }
            }
        }

        
        return positions;
    }

    private List<Entity> getEntitesInSquare(List<Position> pos, List<Entity> entities) {

        // get entities whom current location is within the square
        return entities.stream().filter(e -> (pos.contains(e.getCurrentLocation()))).collect(Collectors.toList());


    }

    public boolean isUsed() {
        return used;
    }

    public boolean ifAdjacentToActive(List<Entity> entities) {
        ArrayList<Position> positions = new ArrayList<Position>();
        int x = super.getCurrentLocation().getX();
        int y = super.getCurrentLocation().getY();

        positions.add(new Position(x + 1, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x, y - 1));

        // get entities whom are floor switches and in cardinal squares
        List<Entity> switchesInCardinalSquares = entities.stream().filter(e -> (positions.contains(e.getCurrentLocation()) && e.getEntityType().startsWith("switch"))).collect(Collectors.toList());

        // if no switch in cardinal squares otherwise check if pressed
        if (switchesInCardinalSquares.isEmpty()) {
            return false;
        } else {
            for (Entity s : switchesInCardinalSquares) {
                FloorSwitch sw = (FloorSwitch) s;
                if (sw.getState() instanceof PressedState) {
                    return true;
                }
            }
        }
        return false;
    }
}
