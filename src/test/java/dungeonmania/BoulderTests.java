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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderTests {
    @Test
    @DisplayName("Tests that player is able to move boulder successfully")
    public void testBoulderMovementBlockage() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_push", "c_WallTest_movementBlockage");
        
        Position expectedPlayerPos = new Position(4, 1);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        Position expectedBoulderPos = new Position(5, 1);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();

        // Player should be able to push boulder
        dmc.tick(Direction.RIGHT);
        assertEquals(expectedPlayerPos, actualPlayerPos);
        assertEquals(expectedBoulderPos, actualBoulderPos);
    }

    @Test
    @DisplayName("Tests that player is unable to move multiple boulders at once successfully")
    public void testMultipleBouldersMovementBlockage() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_push", "c_WallTest_movementBlockage");
        
        Position expectedPlayerPos = new Position(3, 1);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        Position expectedBoulderPos = new Position(2, 1);
        Position actualBoulderPos = getEntities(res, "boulder").get(1).getPosition();

        // Player should not be able to push boulder
        dmc.tick(Direction.LEFT);
        assertEquals(actualPlayerPos, expectedPlayerPos);
        assertEquals(actualBoulderPos, expectedBoulderPos);
    }
}
