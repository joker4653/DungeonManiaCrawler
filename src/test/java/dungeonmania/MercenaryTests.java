package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeonmania.exceptions.InvalidActionException;

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
/*
        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.UP);
        
        expectedPos = new Position(2, 2);
        mPos = getEntities(res, "mercenary").get(0).getPosition();      
        assertEquals(expectedPos, mPos);
 */   
    }

    // TODO: mercenary moves through open door !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @Test
    @DisplayName("Test Mercenary Bribery invalid id.")
    public void testBriberyInvalidId() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_followPlayer", "c_mercenaryTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> {
            dmc.interact("abcd");
        });
    }

    @Test
    @DisplayName("Test mercenary bribery too far away.")
    public void testBriberyOutsideRadius() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_followPlayer", "c_mercenaryTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);

        EntityResponse merc = getEntities(res, "mercenary").get(0);

        assertThrows(InvalidActionException.class, () -> {
            dmc.interact(merc.getId());
        });
    }

    @Test
    @DisplayName("Test bribery insufficient gold.")
    public void testBriberyInsufficientGold() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_nogold", "c_mercenaryTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        

        EntityResponse merc = getEntities(res, "mercenary").get(0);

        assertThrows(InvalidActionException.class, () -> {
            dmc.interact(merc.getId());
        });
    }







    /* Bribery tests:
     * - entity id is not real
     * - player is not within radius of mercenary (indicated by entity id).
     * - player does not have enough gold.
     *
     * - [Spawner] Player is not cardinally adjacent to spawner.
     * - [Spawner] Player does not have a weapon for destroying spawner.
     * - [Spawner] Destruction success.
     */

    // Mercenary ally movement tests:
<<<<<<< HEAD

    /*@Test
<<<<<<< HEAD
    @Test
=======
>>>>>>> 08e951f (commented out ally test because bribing hasnt been implemented yet)
=======
    @Test
>>>>>>> e9d4292 (Wrote tests for mercenary bribery.)
    @DisplayName("Test the movement of a bribed mercenary")
    public void testMercenaryAllyMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_followPlayer", "c_mercenaryTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        EntityResponse merc = getEntities(res, "mercenary").get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(merc.getId());
        });

        for (int i = 0; i < 5; i++) {
            assertTrue(checkAllyPos(Direction.UP, res, dmc));
            assertTrue(checkAllyPos(Direction.DOWN, res, dmc));
            assertTrue(checkAllyPos(Direction.LEFT, res, dmc));
            assertTrue(checkAllyPos(Direction.RIGHT, res, dmc));
        }

<<<<<<< HEAD
    }*/
<<<<<<< HEAD
    }
=======
>>>>>>> 08e951f (commented out ally test because bribing hasnt been implemented yet)

=======
    }
>>>>>>> e9d4292 (Wrote tests for mercenary bribery.)

