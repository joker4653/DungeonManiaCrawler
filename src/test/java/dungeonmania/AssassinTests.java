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

public class AssassinTests {
    // Assassin enemy movement tests:
    @Test
    @DisplayName("Test assassin follows the player")
    public void testAssassinFollowsPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_followPlayer", "c_assassinTest_followPlayer");

        Position pos = getEntities(res, "assassin").get(0).getPosition();
        assertEquals(pos, new Position(8, 1));

        res = dmc.tick(Direction.LEFT);

        Position expectedPos = new Position(7, 1);
        Position mPos = getEntities(res, "assassin").get(0).getPosition();      
        assertEquals(expectedPos, mPos);

        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.UP);
        
        expectedPos = new Position(4, 1);
        mPos = getEntities(res, "assassin").get(0).getPosition();      
        assertEquals(expectedPos, mPos);
    }

    @Test
    @DisplayName("Test that the assassin is blocked by walls, doors and boulders")
    public void testAssassinIsBlocked() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_blocked", "c_assassinTest_followPlayer");

        Position pos = getEntities(res, "assassin").get(0).getPosition();
        assertEquals(pos, new Position(2, 2));

        res = dmc.tick(Direction.LEFT);

        Position expectedPos = new Position(2, 2);
        Position mPos = getEntities(res, "assassin").get(0).getPosition();      
        assertEquals(expectedPos, mPos);

        for (int i = 0; i < 3; i++)
            res = dmc.tick(Direction.UP);
        
        expectedPos = new Position(2, 2);
        mPos = getEntities(res, "assassin").get(0).getPosition();      
        assertEquals(expectedPos, mPos);
    
    }

    @Test
    @DisplayName("Test assassin movement when they are close to the player")
    public void testAssassinCloseToPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_closeToPlayer", "c_assassinTest_followPlayer");

        //Position pos = getEntities(res, "assassin").get(0).getPosition();
        res = dmc.tick(Direction.UP);

        Position expectedPos1 = new Position(1, 0);
        Position expectedPos2 = new Position(0, 1);

        Position mPos = getEntities(res, "assassin").get(0).getPosition();      
        assertTrue(mPos.equals(expectedPos1) || mPos.equals(expectedPos2));
    }

    /*@Test TODO UNCOMMENT WHEN DOOR & KEY COMPLETE!
    @DisplayName("Test assassin walk through open door")
    public void testAssassinWalkThroughOpenDoor() {
        //
        //  player        key      door    
        //  assassin     wall          
        //                exit     
        //
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_throughOpenDoor", "c_assassinTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        Position mPos = getEntities(res, "assassin").get(0).getPosition();
        Position expectedPos = new Position(4, 1);
        
        assertTrue(mPos.equals(expectedPos));
    }*/


    // Assassin ally movement tests:
    @Test
    @DisplayName("Test the movement of a bribed assassin")
    public void testAssassinAllyMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_followPlayer", "c_assassinTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        EntityResponse assassin = getEntities(res, "assassin").get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(assassin.getId());
        });

        // can't move this to a helper function because I need a new response every time the player moves
        Position playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.UP);
        assertTrue(getEntities(res, "assassin").get(0).getPosition().equals(playerPrevPos));

        playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.LEFT);
        assertTrue(getEntities(res, "assassin").get(0).getPosition().equals(playerPrevPos));

        playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.DOWN);
        assertTrue(getEntities(res, "assassin").get(0).getPosition().equals(playerPrevPos));
        
        playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getEntities(res, "assassin").get(0).getPosition().equals(playerPrevPos));
    }

    // Assassin bribing tests:
    @Test
    @DisplayName("Test invalid id given to assassin bribery function.")
    public void testAssassinInvalidID() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_followPlayer", "c_assassinTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> {
            dmc.interact("haim");
        });
    }

    @Test
    @DisplayName("Test assassin bribery too far away.")
    public void testAssassinBriberyOutsideRadius() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_followPlayer", "c_assassinTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);

        EntityResponse assassin = getEntities(res, "assassin").get(0);

        assertThrows(InvalidActionException.class, () -> {
            dmc.interact(assassin.getId());
        });
    }

    @Test
    @DisplayName("Test bribery insufficient gold.")
    public void testAssassinBriberyInsufficientGold() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_noGold", "c_assassinTest_followPlayer");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        EntityResponse assassin = getEntities(res, "assassin").get(0);

        assertThrows(InvalidActionException.class, () -> {
            dmc.interact(assassin.getId());
        });
    }

    
}
