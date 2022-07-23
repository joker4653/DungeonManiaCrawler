package dungeonmania.Entities.Moving;

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

import dungeonmania.Helper;
import dungeonmania.Statistics;
import dungeonmania.Battling.EnemyBattleStrategy.AllyStrategy;
import dungeonmania.Battling.EnemyBattleStrategy.StandardBattlingStrategy;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
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
        super.enemyChangeStrategy(new StandardBattlingStrategy(configMap, "mercenary"));
        this.isNeighbour = false;
        this.configMap = configMap;
        super.setCanStepOn("mercenary");
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics) {
        if (!super.isAlly()) {
            enemyMovementDS(listOfEntities, player);
        } else {
            super.enemyChangeStrategy(new AllyStrategy(configMap, "mercenary"));
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
        initialiseDijStructures(grid, dist, prev, player);

        List<Position> queue = new ArrayList<>(grid);
        boolean mercFound = false;
        while (!queue.isEmpty() && mercFound == false) {
            Position u = findEleSmallestDist(queue, dist);
            if (u == null)
                break;
            
            queue.remove(u);
            mercFound = calcShortestPath(u, listOfEntities, dist, prev, visited, player);
        }
    }

    // Finds the shortest path and also checks if the player has reached the mercenary.
    private boolean calcShortestPath(Position u, List<Entity> listOfEntities, Map<Position, Double> dist,
    Map<Position, Position> prev, HashSet<Position> visited, Player player) {

        boolean mercFound = false;
        List<Position> uAdjList = getAdjacentPosInDist(u, listOfEntities, dist);
        for (Position v : uAdjList) {
            if (!visited.contains(v) && dist.get(u) + 1 < dist.get(v)) {
                dist.put(v, dist.get(u) + 1);
                prev.put(v, u);
                visited.add(v);
            }

            // if the neighbour is a mercenary...
            if (v.equals(getCurrentLocation())) {
                mercFound = true;
                mercenaryReached(player, prev, listOfEntities);
                break;
            }
        }
        
        return mercFound;
    }

    private void mercenaryReached(Player player, Map<Position, Position> prev, List<Entity> listOfEntities) {
        // if the ally's next position is the player's position, the ally doesn't move ---> NEED FORUM RESPONSE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (!(isAlly() && prev.get(getCurrentLocation()).equals(player.getCurrentLocation())))
            super.setCurrentLocation(prev.get(getCurrentLocation()));
    
        List<Position> playerAdjPos = getAdjacentPos(player.getCurrentLocation(), listOfEntities);
        if (playerAdjPos.contains(this.getCurrentLocation()))
            this.isNeighbour = true;
    }

    private void initialiseDijStructures(List<Position> grid, Map<Position, Double> dist, Map<Position, Position> prev, Player player) {
        for (Position currPos : grid) {
            dist.put(currPos, Double.MAX_VALUE);
            prev.put(currPos, null);
        }

        dist.put(player.getCurrentLocation(), 0.0);
    }

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

        if (minVal != Double.MAX_VALUE)
            return distInQueue.entrySet().stream()
                                         .filter(entry -> entry.getValue().equals(minVal))
                                         .findFirst()
                                         .get()
                                         .getKey();
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

    // gets cardinally adjacent possible positions that the mercenary can be on
    private List<Position> getAdjacentPos(Position currPos, List<Entity> listOfEntities) {
        List<Position> possiblePos = createListOfCardinalPos(currPos);

        listOfEntities.stream()
                      .filter((currEntity) -> possiblePos.contains(currEntity.getCurrentLocation()) && !super.canStep(currEntity.getEntityType()))
                      .forEach((ent) -> possiblePos.remove(possiblePos.indexOf(ent.getCurrentLocation())));

        return possiblePos;
    }

    /* Getters & Setters */
    public boolean isNeighbour() {
        return isNeighbour;
    }

    public void setNeighbour(boolean isNeighbour) {
        this.isNeighbour = isNeighbour;
    }

    public HashMap<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(HashMap<String, String> configMap) {
        this.configMap = configMap;
    }
}
