package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    private int currHealth;
    private int damagePoints;
    private boolean isAlly;
    private static final int UPPER_LIMIT = 60;

    public Mercenary(int x, int y) {
        this.isAlly = false;
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(true);
        super.setEntityType("mercenary");
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player) {
        if (!isAlly) {
            enemyMovement(listOfEntities, player);
        } else {
            allyMovement(listOfEntities, player);
        }
    }

    // If the ally is in any of the player's neighbouring positions, they move to the player's previous position.
    // Otherwise, the ally still moves like an enemy (it still moves towards the player).
    private void allyMovement(List<Entity> listOfEntities, Player player) {
        List<Position> playerAdjPos = getAdjacentPos(player.getCurrentLocation(), listOfEntities);

        if (playerAdjPos.contains(this.getCurrentLocation())) {
            super.setCurrentLocation(player.getPrevPos());
        } else {
            enemyMovement(listOfEntities, player);
        }
    }

    private void enemyMovement(List<Entity> listOfEntities, Player player) {
        List<Position> queue = new ArrayList<>(Arrays.asList(player.getCurrentLocation()));
        HashMap<Position, Integer> reachablePos = new HashMap<>();
        reachablePos.put(player.getCurrentLocation(), 0);
        
        int distance = 1;
        boolean mercFound = false;

        while (queue.size() != 0 && !mercFound && distance <= UPPER_LIMIT) {
            Position front = queue.get(0);
            queue.remove(0);
            distance = reachablePos.get(front) + 1; // this is front's neighbours' distance from front
            mercFound = processAdjPosAndCheckIfMerc(reachablePos, front, listOfEntities, queue, distance);            
        }

        // next, find the path from mercenary to player
        if (mercFound) {
            setMercNextPos(reachablePos, listOfEntities);
        }
    }

    private boolean processAdjPosAndCheckIfMerc(Map<Position, Integer> reachablePos, Position front, List<Entity> listOfEntities, List<Position> queue, int distance) {
        List<Position> adjacentPos = getAdjacentPos(front, listOfEntities);

        for (Position adjPos : adjacentPos) {
            // if the adjacent position is NOT in the hashmap, I add it to the queue and hashmap
            if (reachablePos.get(adjPos) == null) {
                queue.add(adjPos);
                reachablePos.put(adjPos, distance);
            }

            // if the adjacent position is a mercenary, return true
            if (adjPos.equals(this.getCurrentLocation()))
                return true;
        }

        return false;
    }

    private void setMercNextPos(HashMap<Position, Integer> reachablePos, List<Entity> listOfEntities) {
        // find the merc's neighbour that has the minimum distance in the map
        List<Position> mercNeighbours = getAdjacentPos(this.getCurrentLocation(), listOfEntities);

        int minDistance = UPPER_LIMIT;
        Position minPosition = this.getCurrentLocation();
        for (Position currNeighbour : mercNeighbours) {
            if (reachablePos.containsKey(currNeighbour) && reachablePos.get(currNeighbour) < minDistance) {
                minDistance = reachablePos.get(currNeighbour);
                minPosition = currNeighbour;
            }
        }

        setCurrentLocation(minPosition);
    }

    // gets cardinally adjacent possible positions
    private List<Position> getAdjacentPos(Position currPos, List<Entity> listOfEntities) {
        Position up = new Position(currPos.getX(), currPos.getY() - 1);
        Position down = new Position(currPos.getX(), currPos.getY() + 1);
        Position left = new Position(currPos.getX() - 1, currPos.getY());
        Position right = new Position(currPos.getX() + 1, currPos.getY());

        List<Position> possiblePos = new ArrayList<>(Arrays.asList(left, right, up, down));

        for (Entity currEntity : listOfEntities) {
            if (possiblePos.contains(currEntity.getCurrentLocation()) && !currEntity.getCanMercBeOnThisEntityBool())
                possiblePos.remove(possiblePos.indexOf(currEntity.getCurrentLocation()));
        }

        return possiblePos;
    }
    
}