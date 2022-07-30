package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;


public class SwordTests {
 
    @Test
    @DisplayName("Placing Sword into Map at (1,1)") 
    public void placeSword() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "sword").get(0).getPosition();
        assertEquals(expectedPos, realPos);
    }

    @Test
    @DisplayName("Sword drop in durability after battle")
    public void DropDurabilityAfterBattle() {

    }
}