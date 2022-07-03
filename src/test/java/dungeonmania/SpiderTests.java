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

public class SpiderTests {

    /* DO TEST DESIGN/TEST REFACTORING LATER (CREATE TEST HELPER FUNCTIONS)!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

    // Spider spawn tests:
    @Test
    @DisplayName("Test spider doesn't spawn on boulder")
    public void testSpiderDoesntSpawnOnBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_boulders", "c_spiderTest_boulders");

        dmc.tick(Direction.UP);
        
        // spiders should only spawn at (0, 0)
        Position expectedPos = new Position(0, 0);
        Position actualSpider1Pos = getEntities(res, "spider").get(0).getPosition();
        assertEquals(actualSpider1Pos, expectedPos);

        dmc.tick(Direction.UP);
        Position actualSpider2Pos = getEntities(res, "spider").get(1).getPosition();
        assertEquals(actualSpider1Pos, expectedPos);
        assertEquals(actualSpider2Pos, expectedPos);
    }
    


    // Spider movement tests:

}
