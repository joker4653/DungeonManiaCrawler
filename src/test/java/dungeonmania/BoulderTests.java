package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class BoulderTests {
    @Test
    @DisplayName("Basic boulder push right")
    public void basicBoulderPushRight() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_basicPush", "c_playerTest_basicMovement");

        // Player will push boulder to right
        res = dmc.tick(Direction.RIGHT);
        Position expectedBoulderPos = new Position(3, 2);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        Position expectedPlayerPos = new Position(2, 2);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedBoulderPos, actualBoulderPos);    
        assertEquals(expectedPlayerPos, actualPlayerPos); 

        res = dmc.tick(Direction.RIGHT);
        expectedBoulderPos = new Position(4, 2);
        actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        expectedPlayerPos = new Position(3, 2);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedBoulderPos, actualBoulderPos);    
        assertEquals(expectedPlayerPos, actualPlayerPos);
    }  


//     @Test
//     @DisplayName("Basic boulder push up")
//     public void basicBoulderPush() {
//         DungeonManiaController dmc = new DungeonManiaController();
//         DungeonResponse res = dmc.newGame("d_boulderTest_pushUp", "c_playerTest_basicMovement");

//         res = dmc.tick(Direction.UP);

//         Position expectedBoulderPos = new Position(1, 4);
//         Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
//         Position expectedPlayerPos = new Position(1, 3);
//         Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
//         assertEquals(expectedBoulderPos, actualBoulderPos);    
//         assertEquals(expectedPlayerPos, actualPlayerPos);
// 
//     }
}
