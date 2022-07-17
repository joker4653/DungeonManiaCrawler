package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeonmania.exceptions.InvalidActionException;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryTests {
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
    }

    /*@Test TODO UNCOMMENT WHEN DOOR & KEY COMPLETE!
    @DisplayName("Test mercenary walk through open door")
    public void testMercenaryWalkThroughOpenDoor() {
        //
        //  player        key      door    
        //  mercenary     wall          
        //                exit     
        //
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_throughOpenDoor", "c_mercenaryTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        Position mPos = getEntities(res, "mercenary").get(0).getPosition();
        Position expectedPos = new Position(4, 1);
     
        assertTrue(mPos.equals(expectedPos));
    }*/

    @Test
    @DisplayName("Test Mercenary Bribery invalid id.")
    public void testBriberyInvalidId() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_mercenaryTest_followPlayer", "c_mercenaryTest_followPlayer");

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

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

    // Mercenary ally movement tests:
    @Test
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

        // can't move this to a helper function because I need a new response every time the player moves
        Position playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.UP);
        assertTrue(getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos));

        playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.LEFT);
        assertTrue(getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos));

        playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.DOWN);
        assertTrue(getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos));
        
        playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos));
    }    
}
