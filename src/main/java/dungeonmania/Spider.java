package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Spider extends MovingEntity {
    private boolean isClockwise = true;
    private Position nextPosition;
    private int currHealth;
    private int damagePoints;

    //private int spiderID;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private Position spawnLocation;

    public Spider(int x, int y) {
        this.spawnLocation = new Position(x, y);
        super.setCurrentLocation(spawnLocation); // or should I do super.super.currentLocation = new Position(x, y); ?????????????????????????? (obv method forwarding when referring to currLocaiton)
        initialiseSpider();
    }

    public Spider(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        initialiseSpider();
    }

    private void initialiseSpider() {
        super.setCanSpiderBeOnThisEntity(true);
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("spider");
    }

    public void spawn(List<Entity> listOfEntities, Player player) {
        List<Position> possibleSpiderLocations = new ArrayList<>();

        // get a list of possible spawn locations within the map
        for (int row = xMin; row <= xMax; row++) {
            for (int col = yMin; col <= yMax; col++) {
                possibleSpiderLocations.add(new Position(row, col));
            }
        }
        
        // exclude locations of boulders since spiders can't spawn on top of them
        for (Entity currEntity : listOfEntities) {
            if (!currEntity.getCanSpiderBeOnThisEntityBool() && possibleSpiderLocations.contains(currEntity.getCurrentLocation())) {
                possibleSpiderLocations.remove(currEntity.getCurrentLocation());
            }
        }

        Random rand = new Random();
        int randNum = rand.nextInt(possibleSpiderLocations.size());
        Position spawnLocation = possibleSpiderLocations.get(randNum);

        super.setCurrentLocation(spawnLocation);
        setSpawnLocation(spawnLocation);

        // add to entity list
        listOfEntities.add(this);
        // From the spec: "When the spider spawns, they immediately move the 1 square upwards"
        //move(listOfEntities, Direction.DOWN, player);
    }

    public void move(List<Entity> listOfEntities, Direction dir, Player player) {
        Position nextPosition = getNextPosition();

        // Get the next position and check if it's a boulder. If so, change direction and move. Else, move normally.

        if (checkIfNextPositionIsAllowed(nextPosition, listOfEntities)) {
            super.setCurrentLocation(nextPosition);
        } else {
            isClockwise = !isClockwise;
            nextPosition = getNextPosition();
            if (checkIfNextPositionIsAllowed(nextPosition, listOfEntities)) {
                super.setCurrentLocation(nextPosition);
            }
        }

        Entity entity = listOfEntities.stream().filter(e -> e.getEntityID() == this.getEntityID()).findFirst().get();
        if (entity != null)
            entity.setCurrentLocation(nextPosition);

    }

    public Position getNextPosition() {
        int spawnPositionX = spawnLocation.getX();
        int spawnPositionY = spawnLocation.getY();
        int currPositionX = super.getCurrentLocation().getX();
        int currPositionY = super.getCurrentLocation().getY();

        if (currPositionX == spawnPositionX && currPositionY == spawnPositionY - 1) {
            return isClockwise ? new Position(spawnPositionX + 1, spawnPositionY - 1) : new Position(spawnPositionX - 1, spawnPositionY - 1);
        }

        if (currPositionX == spawnPositionX + 1 && currPositionY == spawnPositionY - 1) {
            return isClockwise ? new Position(spawnPositionX + 1, spawnPositionY) : new Position(spawnPositionX, spawnPositionY - 1);
        }

        if (currPositionX == spawnPositionX + 1 && currPositionY == spawnPositionY) {
            return isClockwise ? new Position(spawnPositionX + 1, spawnPositionY + 1) : new Position(spawnPositionX + 1, spawnPositionY - 1);
        }

        if (currPositionX == spawnPositionX + 1 && currPositionY == spawnPositionY + 1) {
            return isClockwise ? new Position(spawnPositionX, spawnPositionY + 1) : new Position(spawnPositionX + 1, spawnPositionY);
        }

        if (currPositionX == spawnPositionX && currPositionY == spawnPositionY + 1) {
            return isClockwise ? new Position(spawnPositionX - 1, spawnPositionY + 1) : new Position(spawnPositionX + 1, spawnPositionY + 1);
        }

        if (currPositionX == spawnPositionX - 1 && currPositionY == spawnPositionY + 1) {
            return isClockwise ? new Position(spawnPositionX - 1, spawnPositionY) : new Position(spawnPositionX, spawnPositionY + 1);
        }

        if (currPositionX == spawnPositionX - 1 && currPositionY == spawnPositionY) {
            return isClockwise ? new Position(spawnPositionX - 1, spawnPositionY - 1) : new Position(spawnPositionX - 1, spawnPositionY + 1);
        }

        if (currPositionX == spawnPositionX - 1 && currPositionY == spawnPositionY - 1) {
            return isClockwise ? new Position(spawnPositionX, spawnPositionY - 1) : new Position(spawnPositionX - 1, spawnPositionY);
        }

        // this means we are currently at the spawnLocation, so go up
        return new Position(spawnPositionX, spawnPositionY - 1);
    }

    public boolean checkIfNextPositionIsAllowed(Position nextPosition, List<Entity> listOfEntities) {
        for (Entity currEntity : listOfEntities) {
            Position currPosition = currEntity.getCurrentLocation();
            if (currPosition.equals(nextPosition) && !currEntity.getCanSpiderBeOnThisEntityBool()) {
                return false;
            }
        }

        return true;
    }

    /* Getters and Setters */

    public Position getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Position spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}