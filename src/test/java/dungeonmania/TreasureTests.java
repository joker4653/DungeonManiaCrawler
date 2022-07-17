package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class TreasureTests {
 
    @Test
    @DisplayName("Placing Treasure into Map at (1,1)") 
    public void placeTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_treasureTest_basicSpawn", "c_treasureTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "treasure").get(0).getPosition();
        assertEquals(expectedPos, realPos);

    }

    @Test
    @DisplayName("Adding Treasure to Inventory") 
    public void treasureToInventory() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_treasureTest_basicSpawn", "c_treasureTest_basicSpawn");

        res = dmc.tick(Direction.RIGHT);

        assertFalse(getInventory(res, "treasure").isEmpty());
    }
}
