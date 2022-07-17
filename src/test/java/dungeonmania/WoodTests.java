package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;


public class WoodTests {
 
    @Test
    @DisplayName("Placing wood into Map at (1,1)") 
    public void placeWood() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_woodTest_basicSpawn", "c_woodTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "wood").get(0).getPosition();
        assertEquals(expectedPos, realPos);

    }
}