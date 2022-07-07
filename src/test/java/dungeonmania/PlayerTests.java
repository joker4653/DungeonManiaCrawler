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
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerTests {

    @Test
    @DisplayName("Test player creation & response.")
    public void testPlayerCreationResponse() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_playerTest_basicMovement");

        EntityResponse initPlayer = getPlayer(res).get();

        Position expectedPos = new Position(2, 2);
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), expectedPos, false);

        assertEquals(initPlayer, expectedPlayer);
    }

    @Test
    @DisplayName("Test player movement [up].")
    public void testPlayerMovementUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_playerTest_basicMovement");

        res = dmc.tick(Direction.UP);
        
        Position expectedPos = new Position(2, 1);
        Position actualPlayerPos = getPlayer(res).get().getPosition();
        assertEquals(actualPlayerPos, expectedPos);
    }


    @Test
    @DisplayName("Test player movement [down].")
    public void testPlayerMovementDown() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_playerTest_basicMovement");

        res = dmc.tick(Direction.DOWN);
        
        Position expectedPos = new Position(2, 3);
        Position actualPlayerPos = getPlayer(res).get().getPosition();
        assertEquals(actualPlayerPos, expectedPos);
    }


    @Test
    @DisplayName("Test player movement [right].")
    public void testPlayerMovementRight() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_playerTest_basicMovement");

        res = dmc.tick(Direction.RIGHT);
        
        Position expectedPos = new Position(3, 2);
        Position actualPlayerPos = getPlayer(res).get().getPosition();
        assertEquals(actualPlayerPos, expectedPos);
    }


    @Test
    @DisplayName("Test player movement [left].")
    public void testPlayerMovementLeft() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_playerTest_basicMovement");

        res = dmc.tick(Direction.LEFT);
        
        Position expectedPos = new Position(1, 2);
        Position actualPlayerPos = getPlayer(res).get().getPosition();
        assertEquals(actualPlayerPos, expectedPos);
    }

}

// Player Things To Test!
    // Spawning (in a certain spot, and having requisite info (courtesy of EntityResponse)).
    // Moving to different locations
    // [nyi] Inventory, picking stuff up.
    // [nyi] Statistics.
    // [nyi] Battling, etc.
