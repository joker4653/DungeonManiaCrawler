package dungeonmania.Entities.Moving;

import dungeonmania.util.Position;
import dungeonmania.Statistics;
import dungeonmania.Battling.EnemyBattleStrategy.NoBattlingStrategy;
import dungeonmania.Battling.EnemyBattleStrategy.SpiderBattlingStrategy;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Spider extends MovingEntity {
    private boolean isClockwise = true;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private transient Position spawnLocation;
    private HashMap<String, String> configmap;

    public Spider(int x, int y, HashMap<String, String> configMap) {
        super();
        this.spawnLocation = new Position(x, y);
        super.setCurrentLocation(spawnLocation);
        initialiseSpider(configMap);
    }

    public Spider(int xMin, int xMax, int yMin, int yMax, HashMap<String, String> configMap) {
        super();
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        initialiseSpider(configMap);
        this.configmap = configMap;
    }

    private void initialiseSpider(HashMap<String, String> configMap) {
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("spider");
        super.setEnemyHealth(Double.parseDouble(configMap.get("spider_health")));
        super.enemyChangeStrategy(new SpiderBattlingStrategy(configMap));
        super.setAlly(false);
        super.setCanStepOn("spider");
    }

    public void spawn(List<Entity> listOfEntities, Player player) {
        // get a list of possible spawn locations within the map
        List<Position> possibleSpiderLocations = new ArrayList<>();
        for (int row = xMin; row <= xMax; row++) {
            for (int col = yMin; col <= yMax; col++) {
                possibleSpiderLocations.add(new Position(row, col));
            }
        }
        
        // exclude locations of boulders since spiders can't spawn on top of them
        listOfEntities.stream()
                      .filter((currEntity) -> !super.canStep(currEntity.getEntityType()) && possibleSpiderLocations.contains(currEntity.getCurrentLocation()))
                      .forEach((ent) -> possibleSpiderLocations.remove(ent.getCurrentLocation()));

        Position spawnLocation = super.getRandPos(possibleSpiderLocations);
        setSpawnLocation(spawnLocation);
        listOfEntities.add(this);

    }

    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory, Statistics statistics) {
        // Get the next position and check if it's a boulder. If so, change direction and move. Otherwise, move normally.

        Position nextPosition = getNextPosition();
        if (checkIfNextPositionIsAllowed(nextPosition, listOfEntities)) {
            super.setCurrentLocation(nextPosition);
        } else {
            isClockwise = !isClockwise;
            nextPosition = getNextPosition();
            if (checkIfNextPositionIsAllowed(nextPosition, listOfEntities))
                super.setCurrentLocation(nextPosition);
        }
    }


    private Position getNextPosition() {
        int spawnPosX = spawnLocation.getX();
        int spawnPosY = spawnLocation.getY();
        HashMap<Position, Position> clockwisePos = generateClockwisePosMap(spawnPosX, spawnPosY);
        HashMap<Position, Position> anticlockwisePos = generateAntiClockwisePosMap(spawnPosX, spawnPosY);

        // if nextPos is null, this means the spider is currently at its spawnLocation. Thus, spider moves up next.
        Position nextPos = isClockwise ? clockwisePos.get(getCurrentLocation()) : anticlockwisePos.get(getCurrentLocation());
        return nextPos != null ? nextPos : new Position(spawnPosX, spawnPosY - 1);
    }

    // generates a hashmap of the spider's new locations if it is moving anticlockwise.
    private HashMap<Position, Position> generateAntiClockwisePosMap(int spawnPosX, int spawnPosY) {
        HashMap<Position, Position> anticlockwisePos = new HashMap<>();
        anticlockwisePos.put(new Position(spawnPosX, spawnPosY - 1), new Position(spawnPosX - 1, spawnPosY - 1));
        anticlockwisePos.put(new Position(spawnPosX + 1, spawnPosY - 1), new Position(spawnPosX, spawnPosY - 1));
        anticlockwisePos.put(new Position(spawnPosX + 1, spawnPosY), new Position(spawnPosX + 1, spawnPosY - 1));
        anticlockwisePos.put(new Position(spawnPosX + 1, spawnPosY + 1), new Position(spawnPosX + 1, spawnPosY));
        anticlockwisePos.put(new Position(spawnPosX, spawnPosY + 1), new Position(spawnPosX + 1, spawnPosY + 1));
        anticlockwisePos.put(new Position(spawnPosX - 1, spawnPosY + 1), new Position(spawnPosX, spawnPosY + 1));
        anticlockwisePos.put(new Position(spawnPosX - 1, spawnPosY), new Position(spawnPosX - 1, spawnPosY + 1));
        anticlockwisePos.put(new Position(spawnPosX - 1, spawnPosY - 1), new Position(spawnPosX - 1, spawnPosY));

        return anticlockwisePos;
    }

    // generates a hashmap of the spider's new locations if it is moving clockwise.
    private HashMap<Position, Position> generateClockwisePosMap(int spawnPosX, int spawnPosY) {
        HashMap<Position, Position> clockwisePos = new HashMap<>();
        clockwisePos.put(new Position(spawnPosX, spawnPosY - 1), new Position(spawnPosX + 1, spawnPosY - 1));
        clockwisePos.put(new Position(spawnPosX + 1, spawnPosY - 1), new Position(spawnPosX + 1, spawnPosY));
        clockwisePos.put(new Position(spawnPosX + 1, spawnPosY), new Position(spawnPosX + 1, spawnPosY + 1));
        clockwisePos.put(new Position(spawnPosX + 1, spawnPosY + 1), new Position(spawnPosX, spawnPosY + 1));
        clockwisePos.put(new Position(spawnPosX, spawnPosY + 1), new Position(spawnPosX - 1, spawnPosY + 1));
        clockwisePos.put(new Position(spawnPosX - 1, spawnPosY + 1), new Position(spawnPosX - 1, spawnPosY));
        clockwisePos.put(new Position(spawnPosX - 1, spawnPosY), new Position(spawnPosX - 1, spawnPosY - 1));
        clockwisePos.put(new Position(spawnPosX - 1, spawnPosY - 1), new Position(spawnPosX, spawnPosY - 1));

        return clockwisePos;
    }

    private boolean checkIfNextPositionIsAllowed(Position nextPosition, List<Entity> listOfEntities) {
        return !listOfEntities.stream()
                              .anyMatch((currEntity) -> currEntity.getCurrentLocation().equals(nextPosition) && !super.canStep(currEntity.getEntityType()));
    }

    /* Getters and Setters */

    public Position getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Position spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
