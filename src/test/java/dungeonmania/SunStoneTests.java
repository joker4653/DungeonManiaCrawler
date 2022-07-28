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
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SunStoneTests {
 
    @Test
    @DisplayName("Placing Sun Stone into Map at (1,1)") 
    public void placeStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_basicSpawn", "c_treasureTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "sun_stone").get(0).getPosition();
        assertEquals(expectedPos, realPos);

    }

    @Test
    @DisplayName("Adding Stone to Inventory") 
    public void StoneToInventory() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_basicSpawn", "c_treasureTest_basicSpawn");

        res = dmc.tick(Direction.RIGHT);

        assertFalse(getInventory(res, "sun_stone").isEmpty());
    }

    @Test
    @DisplayName("Using Sun Stone as key to a door")
    public void StoneAsKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_askey", "c_treasureTest_basicSpawn");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertFalse(getEntities(res, "door").isEmpty());
    }

    @Test
    @DisplayName("Using Sun Stone as key to a door while having the corresponding key in inv too")
    public void StoneAsKeyWhileHoldingKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sunStoneTest_askey", "c_treasureTest_basicSpawn");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertFalse(getInventory(res, "key").isEmpty());
    }
}