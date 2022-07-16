package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;

public class DoorTests {
    @Test
    @DisplayName("Tests that door spawns on map in correct position")
    public void testDoorSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_doorTest_basic", "c_treasureTest_basicSpawn");

        Position expectedPos = new Position(4, 2);
        Position realPos = getEntities(res, "door").get(0).getPosition();
        assertEquals(expectedPos, realPos);
    }

    @Test
    @DisplayName("Tests that key opens door")
    public void testDoorKeyInteraction() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_doorTest_basic", "c_treasureTest_basicSpawn");

        // cant move to the left due to door being locked
        res = dmc.tick(Direction.LEFT);

        Position expectedPos = new Position(2, 2);
        Position realPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, realPos);

        // moves to the right to pick up key
        res = dmc.tick(Direction.RIGHT);
        expectedPos = new Position(3, 2);
        realPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, realPos);

        // moves to the right to go through door
        res = dmc.tick(Direction.RIGHT);
        expectedPos = new Position(4, 2);
        realPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, realPos);

        // moves to the right to pick up second key
        res = dmc.tick(Direction.RIGHT);
        expectedPos = new Position(5, 2);
        realPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, realPos);

        // Should now be able to walk through initial door
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        expectedPos = new Position(1, 2);
        realPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, realPos);
    }
}
