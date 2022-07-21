package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.EnemyBattleStrategy.MercenaryAllyStrategy;
import dungeonmania.EnemyBattleStrategy.MercenaryEnemyStrategy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    private static final int UPPER_LIMIT = 60;
    private boolean isNeighbour;
    private HashMap<String, String> configMap;

    public Mercenary(int x, int y, HashMap<String, String> configMap) {
        super();
        super.setAlly(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(true);
        super.setEntityType("mercenary");
        super.setEnemyHealth(Double.parseDouble(configMap.get("mercenary_health")));
        super.enemyChangeStrategy(new MercenaryEnemyStrategy(configMap));
        this.isNeighbour = false;
        this.configMap = configMap;
        super.setCanStepOn("mercenary");
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics) {
        if (!super.isAlly()) {
            enemyMovementDS(listOfEntities, player);
        } else {
            super.enemyChangeStrategy(new MercenaryAllyStrategy(configMap));
            allyMovement(listOfEntities, player); 
        }
    }

    // If the ally is in any of the player's neighbouring positions, they move to the player's previous position.
    // Otherwise, the ally still moves like an enemy (it still moves towards the player).
    private void allyMovement(List<Entity> listOfEntities, Player player) {
        if (this.isNeighbour) {
            super.setCurrentLocation(player.getPrevPos());
        } else {
            enemyMovementDS(listOfEntities, player);
        }
    }

    // Mercenaries move according to Dijkstra's algorithm.
    private void enemyMovementDS(List<Entity> listOfEntities, Player player) {
        HashMap<String, Integer> gridBoundaries = Helper.findMinAndMaxValues(listOfEntities);
        Map<Position, Double> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();
        HashSet<Position> visited = new HashSet<Position>();
        List<Position> grid =  getGrid(gridBoundaries);

        for (Position currPos : grid) {
            dist.put(currPos, Double.MAX_VALUE);
            prev.put(currPos, null);
        }
        dist.put(player.getCurrentLocation(), 0.0);

        List<Position> queue = new ArrayList<>(grid);
        boolean mercFound = false;
        while (!queue.isEmpty() && mercFound == false) {
            Position u = findEleSmallestDist(queue, dist);
            if (u == null)
                break;
            
            queue.remove(u);

            List<Position> uAdjList = getAdjacentPosInDist(u, listOfEntities, dist);
            for (Position v : uAdjList) {
                if (!visited.contains(v) && dist.get(u) + 1 < dist.get(v)) {
                    dist.put(v, dist.get(u) + 1);
                    prev.put(v, u);
                    visited.add(v);
                }

                if (v.equals(getCurrentLocation())) {
                    mercFound = true;
                    if (!(isAlly() && prev.get(getCurrentLocation()).equals(player.getCurrentLocation())))
                        super.setCurrentLocation(prev.get(getCurrentLocation()));
                    List<Position> playerAdjPos = getAdjacentPos(player.getCurrentLocation(), listOfEntities);
                    if (playerAdjPos.contains(this.getCurrentLocation()))
                        this.isNeighbour = true;
                    break;
                }
            }

        }
    }

    /*// need to backtrack to find the path, and set the mercenary's next position accordingly.
    private void getShortestPathAndSetCurrLocation(Map<Position, Double> dist, Map<Position, Position> prev) {
        List<Position> shortestPath = new ArrayList<>();

        Position currPos = findEleSmallestDist(dist);
        while (prev.get(currPos) != null) {
            shortestPath.add(currPos);
            currPos = prev.get(currPos);
        }

        super.setCurrentLocation(shortestPath.get(shortestPath.size() - 1));
    }*/

    private List<Position> getAdjacentPosInDist(Position u, List<Entity> listOfEntities, Map<Position, Double> dist) {
        List<Position> listOfAdjPos = getAdjacentPos(u, listOfEntities);
        return listOfAdjPos.stream()
                           .filter(pos -> dist.containsKey(pos))
                           .collect(Collectors.toList());
    }

    private Position findEleSmallestDist(List<Position> queue, Map<Position, Double> dist) {

        Map<Position, Double> distInQueue = dist.entrySet().stream()
                                                           .filter(entry -> queue.contains(entry.getKey()))
                                                           .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
        
        double minVal = Collections.min(distInQueue.values());

       // double minVal = Collections.min((dist.entrySet().stream()
        //                    .filter(entry -> queue.contains(entry.getKey())).values()));
        if (minVal != Double.MAX_VALUE)
            return distInQueue.entrySet().stream()
                                         .filter(entry -> entry.getValue().equals(minVal))
                                         .findFirst()
                                         .get()
                                         .getKey();
        
        /*for (Entry<Position, Double> entry : dist.entrySet()) {
            if (entry.getValue().equals(minVal)) {
                return entry.getKey();
            }
        }

        return null;*/
        return null;
    }

    private List<Position> getGrid(HashMap<String, Integer> gridBoundaries) {
        List<Position> grid = new ArrayList<>();
        
        for (int x = gridBoundaries.get("minX") - 1; x < gridBoundaries.get("maxX") + 1; x++) {
            for (int y = gridBoundaries.get("minY") - 1; y < gridBoundaries.get("maxY") + 1; y++) {
                grid.add(new Position(x, y));
            }
        }

        return grid;
    }

    /*private void enemyMovement(List<Entity> listOfEntities, Player player) {
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
            setMercNextPos(reachablePos, listOfEntities, player.getCurrentLocation());
            List<Position> playerAdjPos = getAdjacentPos(player.getCurrentLocation(), listOfEntities);
            if (playerAdjPos.contains(this.getCurrentLocation()))
                this.isNeighbour = true;
        }
    }

    private boolean processAdjPosAndCheckIfMerc(Map<Position, Integer> reachablePos, Position front,
    List<Entity> listOfEntities, List<Position> queue, int distance) {
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

    private void setMercNextPos(HashMap<Position, Integer> reachablePos, List<Entity> listOfEntities, Position playerPos) {
        // find the merc's neighbour that has the minimum distance in the map
        List<Position> mercNeighbours = getAdjacentPos(this.getCurrentLocation(), listOfEntities);

        if (super.isAlly() && mercNeighbours.contains(playerPos))
           mercNeighbours.remove(playerPos);

        int minDistance = UPPER_LIMIT;
        Position minPosition = this.getCurrentLocation();
        for (Position currNeighbour : mercNeighbours) {
            if (reachablePos.containsKey(currNeighbour) && reachablePos.get(currNeighbour) < minDistance) {
                minDistance = reachablePos.get(currNeighbour);
                minPosition = currNeighbour;
            }
        }

        setCurrentLocation(minPosition);
    }*/

    // gets cardinally adjacent possible positions that the mercenary can be on
    private List<Position> getAdjacentPos(Position currPos, List<Entity> listOfEntities) {
        List<Position> possiblePos = createListOfCardinalPos(currPos);

        listOfEntities.stream()
                      .filter((currEntity) -> possiblePos.contains(currEntity.getCurrentLocation()) && !super.canStep(currEntity.getEntityType()))
                      .forEach((ent) -> possiblePos.remove(possiblePos.indexOf(ent.getCurrentLocation())));

        return possiblePos;
    }
}
