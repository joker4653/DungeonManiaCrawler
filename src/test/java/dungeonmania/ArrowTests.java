package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;


public class ArrowTests {
 
    @Test
    @DisplayName("Placing Arrow into Map at (1,1)") 
    public void placeArrow() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_arrowTest_basicSpawn", "c_arrowTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "arrow").get(0).getPosition();
        assertEquals(expectedPos, realPos);

    }
}