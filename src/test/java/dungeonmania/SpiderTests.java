package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static dungeonmania.TestUtils.getEntities;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTests {
    // Spider spawn tests:
    @Test
    @DisplayName("Test spider doesn't spawn on boulder")
    public void testSpiderDoesntSpawnOnBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulders", "c_spiderTest_boulders");

        res = dmc.tick(Direction.LEFT);
        
        // spiders should only spawn at (0, 0).
        Position expectedPos = new Position(0, 0);
        Position actualSpider1Pos = getEntities(res, "spider").get(0).getPosition();
        assertEquals(expectedPos, actualSpider1Pos);

        res = dmc.tick(Direction.LEFT);
        Position actualSpider2Pos = getEntities(res, "spider").get(1).getPosition();
        assertEquals(actualSpider1Pos, expectedPos);
        assertEquals(actualSpider2Pos, expectedPos);
    }

    @Test
    @DisplayName("Test multiple spiders spawning when spawn_rate is 1 tick")
    public void testMultipleSpidersSpawnEvery1Tick() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_spawnEveryTick", "c_spiderTest_spawnEveryTick");

        // spiders can only spawn at (1,1)
        int spiderCount = 0;

        // create 20 spiders
        for (int numTicks = 0; numTicks < 20; numTicks++) {
            res = dmc.tick(Direction.UP);
            spiderCount++;
        }
        assertEquals(spiderCount, getEntities(res, "spider").size());
    }

    @Test
    @DisplayName("Test multiple spiders spawning when spawn_rate is 0 ticks")
    public void testMultipleSpidersSpawnEvery0Ticks() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_spawn0Ticks", "c_spiderTest_spawn0Ticks");

        for (int i = 0; i < 50; i++) {
            res = dmc.tick(Direction.UP);
        }

        assertEquals(0, getEntities(res, "spider").size());
    }


    @Test
    @DisplayName("Test multiple spiders spawning when spawn_rate is 5 ticks and ensure they spawn within the map boundaries")
    public void testMultipleSpidersSpawnEvery5Ticks() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_spawn5Ticks", "c_spiderTest_spawn5Ticks");
        Position playerPos = new Position(0, 0);
        int spiderCount = 0; // there is already 1 spider at position (2, 0) on the map
        int spiderIndex = 0;
    
        for (int i = 1; i <= 50; i++) {
            res = dmc.tick(Direction.UP);
            playerPos = getEntities(res, "player").get(0).getPosition();

            if (i % 5 == 0) {
                Position actualSpiderPos = getEntities(res, "spider").get((i % 5) + spiderIndex).getPosition();
                // check that the x and y coordinates are within the map's boundaries
                assertTrue(actualSpiderPos.getX() >= 0 && actualSpiderPos.getX() <= 2);
                assertTrue(actualSpiderPos.getY() >= 0 && actualSpiderPos.getX() <= 2);

                if (!actualSpiderPos.equals(playerPos)) {
                    spiderCount++;
                    assertEquals(spiderCount, getEntities(res, "spider").size());
                }

                spiderIndex++;
            }
        }

        assertEquals(spiderCount, getEntities(res, "spider").size());
    }

    // Spider movement tests:

    @Test
    @DisplayName("Test spider running into boulder and then reversing direction")
    public void testSpiderSwitchingDirections() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_changeSpiderDirections", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();

        List<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x  , y-1));
        movementTrajectory.add(new Position(x-1, y-1));
        movementTrajectory.add(new Position(x-1, y));
        movementTrajectory.add(new Position(x-1, y+1));
        movementTrajectory.add(new Position(x,   y+1));
        movementTrajectory.add(new Position(x+1, y+1));
        movementTrajectory.add(new Position(x+1, y));
        movementTrajectory.add(new Position(x+1, y+1));

        // Assert Circular Movement of Spider
        for (int i = 0; i <= 7; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            
            nextPositionElement++;
        }
    }

    @Test
    @DisplayName("Test spider can't move at all if there is a boulder above it")
    public void testSpiderCantMoveWhenBoulderIsAboveIt() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulderIsAbove", "c_spiderTest_boulders");

        res = dmc.tick(Direction.LEFT);
        
        int actualSpider1Row = getEntities(res, "spider").get(0).getPosition().getY();
        assertNotEquals(actualSpider1Row, 0);
        assertNotEquals(actualSpider1Row, 2);
    }
 
    /* @Test
    @DisplayName("Test spider can move if there is a boulder above it AFTER it is pushed by the player")
    public void testSpiderCanMoveIfBoulderAboveItIsMoved() {
        //  player(-1,0)        boulder(1,0)      
        //                      spider(1,1)
        //             exit                     wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_playerPushBoulder", "c_spiderTest_playerPushBoulder");

        res = dmc.tick(Direction.RIGHT);
        
        Position actualSpiderPos= getEntities(res, "spider").get(0).getPosition();
        assertEquals(actualSpiderPos, new Position(1, 1)); //not move

        res = dmc.tick(Direction.RIGHT); // push boulder

        assertEquals(null, getEntities(res, "spider")); //spider moves up and dies
    } */
}
