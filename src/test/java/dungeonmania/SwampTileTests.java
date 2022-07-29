package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.exceptions.InvalidActionException;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTileTests {
    @Test
    @DisplayName("Test zombie spawning on a swamp tile.")
    public void testZombieSpawnOnSwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_zombieSpawnOn", "c_swampTileTest_zombieSpawnOn");
        //int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_zombieSpawnOn"));
       
        res = dmc.tick(Direction.LEFT);
        Position expected = new Position(2, -1);

        for (int i = 0; i < 2; i++) {
            res = dmc.tick(Direction.LEFT);
            Position actualZombiePosition = getEntities(res, "zombie_toast").get(0).getPosition();
            assertEquals(expected, actualZombiePosition);
        }
     
        res = dmc.tick(Direction.LEFT);
        Position zombiePosition = getEntities(res, "zombie_toast").get(0).getPosition();
        assertNotEquals(expected, zombiePosition);
    }

    @Test
    @DisplayName("Test mercenary moves onto a swamp tile.")
    public void testMercenaryMoveOnSwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_mercenaryStepOn", "c_swampTileTest_mercenaryStepOn");
       // int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_mercenaryStepOn"));
   
        res = dmc.tick(Direction.LEFT);
        Position expected = new Position(7, 1);
        Position actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(expected, actualMerPosition);

        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.LEFT);
            actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
            assertEquals(expected, actualMerPosition);
        }
        assertEquals(new Position(-3, 1), getPlayer(res).get().getPosition());

        res = dmc.tick(Direction.LEFT);
        actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
        assertNotEquals(expected, actualMerPosition);

        for (int i = 0; i < 4; i++) {
            res = dmc.tick(Direction.LEFT);
            actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
            assertEquals(new Position(5,1), actualMerPosition);
        }
    }

    @Test
    @DisplayName("Test mercenary moves onto a swamp tile, but the movement factor is 0.")
    public void testMercenaryMoveOnSwampTileMovementFactor0() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_movementFactor0", "c_swampTileTest_movementFactor0");
   
        res = dmc.tick(Direction.LEFT);
        Position expected = new Position(7, 1);
        Position actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(expected, actualMerPosition);

        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.LEFT);
        }
        actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(new Position(4, 1), actualMerPosition);
        assertEquals(new Position(-3, 1), getPlayer(res).get().getPosition());
    }

    @Test
    @DisplayName("Test spider spawning on a swamp tile.")
    public void testSpiderSpawnOnSwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_spiderSpawnOn", "c_swampTileTest_spiderSpawnOn");
        //int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_spiderSpawnOn"));
       
        res = dmc.tick(Direction.UP);
        Position expected = new Position(0, 0);

        for (int i = 0; i < 1; i++) {
            res = dmc.tick(Direction.UP);
            Position actualSpiderPosition = getEntities(res, "spider").get(0).getPosition();
            assertEquals(expected, actualSpiderPosition);
        }
     
        res = dmc.tick(Direction.UP);
        Position spiderPosition = getEntities(res, "spider").get(0).getPosition();
        assertNotEquals(expected, spiderPosition);
    }

    @Test
    @DisplayName("Test spider steps on swamp tile.")
    public void testSpiderStepOnSwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_spiderStepOn", "c_swampTileTest_spiderStepOn");
        //int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_spiderStepOn"));
       
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        Position expected = new Position(4, 4);

        for (int i = 0; i < 2; i++) {
            res = dmc.tick(Direction.UP);
            Position actualSpiderPosition = getEntities(res, "spider").get(0).getPosition();
            assertEquals(expected, actualSpiderPosition);
        }
     
        res = dmc.tick(Direction.UP);
        Position spiderPosition = getEntities(res, "spider").get(0).getPosition();
        assertNotEquals(expected, spiderPosition);
    }

    @Test
    @DisplayName("Test hydra and assassin stepping on a swamp tile.")
    public void testHydraAndAssassinStepOnSwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_hydraAndAssassin", "c_swampTileTest_zombieSpawnOn");
        //int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_zombieSpawnOn"));
       
        res = dmc.tick(Direction.DOWN);
        Position expected = new Position(1, 3);

        for (int i = 0; i < 2; i++) {
            res = dmc.tick(Direction.DOWN);
            Position actualHydraPosition = getEntities(res, "hydra").get(0).getPosition();
            Position actualAssassinPosition = getEntities(res, "assassin").get(0).getPosition();
            assertEquals(expected, actualHydraPosition);
            assertEquals(expected, actualAssassinPosition);
        }
     
        res = dmc.tick(Direction.DOWN);
        Position hydraPosition = getEntities(res, "hydra").get(0).getPosition();
        Position assassinPosition = getEntities(res, "assassin").get(0).getPosition();
        assertNotEquals(expected, hydraPosition);
        assertNotEquals(expected, assassinPosition);
    }

    @Test
    @DisplayName("Test the movement of a mercenary affected by swamp tiles")
    public void testMercShortestPathAffectedBySwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_mercShortestPathAffected", "c_swampTileTest_movementFactor5");
        int expectedX = 2;
        int expectedY = 4;

        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.RIGHT);
            Position expected = new Position(expectedX, expectedY);
            assertEquals(expected, getEntities(res, "mercenary").get(0).getPosition());
            expectedY--;
        }

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(2, 1), getEntities(res, "mercenary").get(0).getPosition());

        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 0), getEntities(res, "mercenary").get(0).getPosition());

        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 0), getEntities(res, "mercenary").get(0).getPosition());
    }

    @Test
    @DisplayName("Test that the shortest path of an assassin changes due to the presence of swamp tiles with a movement factor of 5.")
    public void testAssassinShortestPathAffectedBySwampTile() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_assassinShortestPathAffected", "c_swampTileTest_movementFactor5");
        int expectedX = 2;
        int expectedY = 4;

        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.RIGHT);
            Position expected = new Position(expectedX, expectedY);
            assertEquals(expected, getEntities(res, "assassin").get(0).getPosition());
            expectedY--;
        }

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(2, 1), getEntities(res, "assassin").get(0).getPosition());

        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 0), getEntities(res, "assassin").get(0).getPosition());

        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 0), getEntities(res, "assassin").get(0).getPosition());
    }
}
