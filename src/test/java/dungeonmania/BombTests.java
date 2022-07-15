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


public class BombTests {
    // bomb can be picked up
    // bomb can be placed
    // bomb has variable radius based on config
    // bomb removes entities when explodes
    // bomb does not remove player
    // bomb does not remove entities outside of its range

    @Test
    @DisplayName("Placing bomb into level at (1,1)")
    public void PlaceBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_basicSpawn", "c_bombTest_placeBombRadius2");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "bomb").get(0).getPosition();
        assertEquals(expectedPos, realPos);
    }

    @Test
    @DisplayName("Bomb can be picked up")
    public void PickUpBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_basicSpawn", "c_bombTest_placeBombRadius2");

        res = dmc.tick(Direction.RIGHT);

        assertFalse(getInventory(res, "bomb").isEmpty());
    }

    @Test
    @DisplayName("bomb sits in map if switch not active")
    public void SwitchNotActive() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2", "c_bombTest_placeBombRadius2");
        String str = getEntities(res, "bomb").get(0).getId();

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        
        res = dmc.tick(str);

        // asserting the bomb is now back in list of entities, (removed from list when picked up)
        assertEquals(str,getEntities(res, "bomb").get(0).getId());

        // switch is not active, so bomb should just be placed at same position on map as player
        assertEquals(getPlayer(res).get().getPosition(), getEntities(res, "bomb").get(0).getPosition());

    }

    @Test
    @DisplayName("bomb removes surrounding entities")
    public void ExplodeSurroundingEntities() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_placeBombRadius2", "c_bombTest_placeBombRadius2");
        String str = getEntities(res, "bomb").get(0).getId();

        // compress floor switch
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        res = dmc.tick(str);

        assertTrue(getEntities(res, "wall").isEmpty());
        assertTrue(getEntities(res, "treasure").isEmpty());
        assertTrue(getEntities(res, "boulder").isEmpty());
        assertTrue(getEntities(res, "switch").isEmpty());
        
    }

    @Test
    @DisplayName("Assert bomb does NOT remove entities outside of radius")
    public void DoesNotExplodeOutsideOfRange() {

    }

    @Test
    @DisplayName("Entities still move after tick")
    public void MovementOccursAfterTick() {

    }

    @Test
    @DisplayName("Entities still move if failed to use bomb")
    public void MovementOccursAfterFailure() {

    }
}
