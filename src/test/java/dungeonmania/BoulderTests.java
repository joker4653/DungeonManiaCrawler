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

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;

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


    @Test
    @DisplayName("Basic boulder push up")
    public void basicBoulderPushUp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_pushUp", "c_playerTest_basicMovement");

        Position expectedPlayerPos = new Position(1, 3);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        Position expectedBoulderPos = new Position(1, 2);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        assertEquals(expectedBoulderPos, actualBoulderPos); 

        res = dmc.tick(Direction.UP);
        
        expectedPlayerPos = new Position(1, 2);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();  
        expectedBoulderPos = new Position(1, 1);
        actualBoulderPos = getEntities(res, "boulder").get(0).getPosition(); 
        assertEquals(expectedPlayerPos, actualPlayerPos);
        assertEquals(expectedBoulderPos, actualBoulderPos);
    }

    @Test
    @DisplayName("Basic boulder push down")
    public void basicBoulderPushDown() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_pushDown", "c_playerTest_basicMovement");

        Position expectedPlayerPos = new Position(1, 2);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        Position expectedBoulderPos = new Position(1, 3);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        assertEquals(expectedBoulderPos, actualBoulderPos); 

        res = dmc.tick(Direction.DOWN);
        
        expectedPlayerPos = new Position(1, 3);
        actualPlayerPos = getEntities(res, "player").get(0).getPosition();  
        expectedBoulderPos = new Position(1, 4);
        actualBoulderPos = getEntities(res, "boulder").get(0).getPosition(); 
        assertEquals(expectedPlayerPos, actualPlayerPos);
        assertEquals(expectedBoulderPos, actualBoulderPos);
    }

    @Test
    @DisplayName("Check if player is unable to move two boulders")
    public void doubleBoulderBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_doubleBoulder", "c_playerTest_basicMovement");

        Position expectedPlayerPos = new Position(1, 2);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        Position expectedBoulder1Pos = new Position(2, 2);
        Position actualBoulder1Pos = getEntities(res, "boulder").get(0).getPosition();
        Position expectedBoulder2Pos = new Position(3, 2);
        Position actualBoulder2Pos = getEntities(res, "boulder").get(1).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        assertEquals(expectedBoulder1Pos, actualBoulder1Pos); 
        assertEquals(expectedBoulder2Pos, actualBoulder2Pos); 

        res = dmc.tick(Direction.RIGHT);
        
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        assertEquals(expectedBoulder1Pos, actualBoulder1Pos); 
        assertEquals(expectedBoulder2Pos, actualBoulder2Pos); 
    }

    @Test
    @DisplayName("Check if wall blocks boulder")
    public void boulderWallBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_wallBlock", "c_playerTest_basicMovement");

        Position expectedPlayerPos = new Position(1, 2);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        Position expectedBoulderPos = new Position(2, 2);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        assertEquals(expectedBoulderPos, actualBoulderPos); 

        res = dmc.tick(Direction.RIGHT);
        
        assertEquals(expectedPlayerPos, actualPlayerPos); 
        assertEquals(expectedBoulderPos, actualBoulderPos); 
    }
}
