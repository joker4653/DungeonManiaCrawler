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
    @DisplayName("Basic boulder push")
    public void basicBoulderPush() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_boulderTest_basicPush", "c_playerTest_basicMovement");

        // Player will push boulder to right
        res = dmc.tick(Direction.RIGHT);
        Position expectedBoulderPos = new Position(3, 1);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        Position expectedPlayerPos = new Position(2, 1);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedBoulderPos, actualBoulderPos);    
        assertEquals(expectedPlayerPos, actualPlayerPos); 
    }  
}
