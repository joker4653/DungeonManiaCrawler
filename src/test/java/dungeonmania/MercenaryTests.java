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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTests {

    // Helper function for testing ally movement
    private boolean checkAllyPos(Direction dir, DungeonResponse res, DungeonManiaController dmc) {
        Position playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(dir);
        return getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos);
    }

    // Mercenary enemy movement tests:
    @Test
    @DisplayName("Test mercenary follows the player")
    public void testMercFollowsPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_followPlayer", "c_mercenaryTest_followPlayer");

        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(pos, new Position(8, 1));

        res = dmc.tick(Direction.LEFT);

        Position expectedPos = new Position(7, 1);
        Position mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertEquals(expectedPos, mPos);

        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.UP);
        
        expectedPos = new Position(4, 1);
        mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertEquals(expectedPos, mPos);
    }

    @Test
    @DisplayName("Test that the mercenary is blocked by walls, doors and boulders")
    public void testMercenaryIsBlocked() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_blocked", "c_mercenaryTest_followPlayer");

        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(pos, new Position(2, 2));

        res = dmc.tick(Direction.LEFT);

        Position expectedPos = new Position(2, 2);
        Position mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertEquals(expectedPos, mPos);

        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.UP);
        
        expectedPos = new Position(2, 2);
        mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertEquals(expectedPos, mPos);
    
    }

    @Test
    @DisplayName("Test mercenary movement when they are close to the player")
    public void testMercenaryCloseToPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_closeToPlayer", "c_mercenaryTest_followPlayer");

        //Position pos = getEntities(res, "mercenary").get(0).getPosition();
        res = dmc.tick(Direction.UP);

        Position expectedPos1 = new Position(1, 0);
        Position expectedPos2 = new Position(0, 1);

        Position mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertTrue(mPos.equals(expectedPos1) || mPos.equals(expectedPos2));

    /*     for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.UP);
        
        expectedPos = new Position(2, 2);
        mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertEquals(expectedPos, mPos);*/
    
    }

    // TODO: mercenary moves through open door !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    // Mercenary ally movement tests:

    @Test
    @DisplayName("Test the movement of a bribed mercenary")
    public void testMercenaryAllyMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_followPlayer", "c_mercenaryTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        Position mercPos = getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(mercPos, new Position(5, 1));
                
        for (int i = 0; i < 5; i++) {
            assertTrue(checkAllyPos(Direction.UP, res, dmc));
            assertTrue(checkAllyPos(Direction.DOWN, res, dmc));
            assertTrue(checkAllyPos(Direction.LEFT, res, dmc));
            assertTrue(checkAllyPos(Direction.RIGHT, res, dmc));
        }

    }

}
