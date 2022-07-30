package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getInventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
    @DisplayName("Test player can't move through walls.")
    public void testPlayerVSWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_playerTest_basicMovement");

        res = dmc.tick(Direction.UP);
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

    @Test
    @DisplayName("Test player inventory adding.")
    public void testPlayerInventoryAdding() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_inventoryAdding", "c_playerTest_inventoryAdding");
        assertEquals(0, res.getInventory().size());

        res = dmc.tick(Direction.LEFT);
      
        assertEquals(1, getInventory(res, "treasure").size());
    }


}

// Player Things To Test!
    // Spawning (in a certain spot, and having requisite info (courtesy of EntityResponse)).
    // Moving to different locations
    // [nyi] Inventory, picking stuff up.
    // [nyi] Statistics.
    // [nyi] Battling, etc.
