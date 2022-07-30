package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class WallTests {
    @Test
    @DisplayName("Tests wall has been created")
    public void testWallCreation() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_wallTest_basicCreation", "c_playerTest_basicMovement");

        Position expectedWallPos = new Position(3, 2);
        Position actualWallPos = getEntities(res, "wall").get(0).getPosition();
        assertEquals(expectedWallPos, actualWallPos);
    }

    @Test
    @DisplayName("Tests walls block player movement")
    public void testWallBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_wallTest_basicBlock", "c_playerTest_basicMovement");

        // Player should only be able to move to the left.
        Position expectedPlayerPos = new Position(2, 2);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        res = dmc.tick(Direction.UP);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos);
        res = dmc.tick(Direction.DOWN);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos);
        res = dmc.tick(Direction.LEFT);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        expectedPlayerPos = new Position(1, 2);
        assertEquals(expectedPlayerPos, actualPlayerPos);  
    }

}