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
        int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_zombieSpawnOn"));
       
        res = dmc.tick(Direction.LEFT);
        Position expected = new Position(2, -1);

        for (int i = 0; i < movementFactor; i++) {
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
        int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_mercenaryStepOn"));
   
        res = dmc.tick(Direction.LEFT);
        Position expected = new Position(7, 1);
        Position actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(expected, actualMerPosition);

        for (int i = 0; i < movementFactor; i++) {
            res = dmc.tick(Direction.LEFT);
            actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
            assertEquals(expected, actualMerPosition);
        }
        assertEquals(new Position(-3, 1), getPlayer(res).get().getPosition());

        res = dmc.tick(Direction.LEFT);
        actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
        assertNotEquals(expected, actualMerPosition);

        for (int i = 0; i < movementFactor; i++) {
            res = dmc.tick(Direction.LEFT);
            actualMerPosition = getEntities(res, "mercenary").get(0).getPosition();
            assertEquals(new Position(5,1), actualMerPosition);
        }
    }

    @Test
    @DisplayName("Test mercenary moves onto a swamp tile, but the movement factor is 0.")
    public void testMercenaryMoveOnSwampTileMovementFactor0() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swampTileTest_mercenaryStepOn", "c_swampTileTest_movementFactor0");
   
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
        int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_spiderSpawnOn"));
       
        res = dmc.tick(Direction.UP);
        Position expected = new Position(0, 0);

        for (int i = 0; i < movementFactor; i++) {
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
        int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_spiderStepOn"));
       
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        Position expected = new Position(4, 4);

        for (int i = 0; i < movementFactor; i++) {
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
        int movementFactor = Integer.parseInt(getValueFromConfigFile("movement_factor", "c_swampTileTest_zombieSpawnOn"));
       
        res = dmc.tick(Direction.DOWN);
        Position expected = new Position(1, 2);

        for (int i = 0; i < movementFactor; i++) {
            res = dmc.tick(Direction.DOWN);
            Position actualHydraPosition = getEntities(res, "hydra").get(0).getPosition();
            Position actualAssassinPosition = getEntities(res, "hydra").get(0).getPosition();
            assertEquals(expected, actualHydraPosition);
            assertEquals(expected, actualAssassinPosition);
        }
     
        res = dmc.tick(Direction.DOWN);
        Position hydraPosition = getEntities(res, "hydra").get(0).getPosition();
        Position assassinPosition = getEntities(res, "hydra").get(0).getPosition();
        assertNotEquals(expected, hydraPosition);
        assertNotEquals(expected, assassinPosition);
    }
}