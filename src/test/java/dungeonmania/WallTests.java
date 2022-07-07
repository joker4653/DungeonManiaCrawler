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

public class WallTests {

    @Test
    @DisplayName("Test that player doesn't move through a wall")
    public void testPlayerMovementBlockage() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_WallTest_movementBlockage", "c_WallTest_movementBlockage");
        
        Position expectedPos = new Position(1, 1);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();

        // Player should not be able to move in any direction due to being boxed in by walls and should stay in its
        // original position.
        dmc.tick(Direction.UP);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.RIGHT);
        assertEquals(actualPlayerPos, expectedPos);
    }

    @Test
    @DisplayName("Test that zombie doesn't move through a wall")
    public void testEnemyMovementBlockage() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_WallTest_enemyMovementBlockage", "c_WallTest_movementBlockage");
        
        Position expectedPos = new Position(3, 1);
        Position actualZombiePos = getEntities(res, "zombie").get(0).getPosition();

        // Zombie should not be able to move in any direction due to being boxed in by walls and should stay in its
        // original position.
        dmc.tick(Direction.UP);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.RIGHT);
        assertEquals(actualZombiePos, expectedPos);     
    }

    // Implement this test after implementing Boulder
    @Test
    public void testBoulderMovementBlockage() {
        
    }
}
