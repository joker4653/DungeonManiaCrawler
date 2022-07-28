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

public class PortalTests {
    @Test
    @DisplayName("Tests if portal spawned successfully")
    public void portalSpawnTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_basicTeleport", "c_playerTest_basicMovement");

        Position expectedPos = new Position(3, 2);
        Position actualPos = getEntities(res, "portal").get(0).getPosition();
        assertEquals(expectedPos, actualPos);  

        expectedPos = new Position(5, 2);
        actualPos = getEntities(res, "portal").get(1).getPosition();  
        assertEquals(expectedPos, actualPos);

        expectedPos = new Position(1, 2);
        actualPos = getEntities(res, "portal").get(2).getPosition();  
        assertEquals(expectedPos, actualPos);

        expectedPos = new Position(6, 8);
        actualPos = getEntities(res, "portal").get(3).getPosition();  
        assertEquals(expectedPos, actualPos);
    }

    @Test
    @DisplayName("Tests player teleport 1")
    public void testPlayerTeleport_1() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_basicTeleport", "c_playerTest_basicMovement");

        Position expectedPos = new Position(2, 2);
        Position actualPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, actualPos); 

        // Steps into blue portal from it's left hand side.
        res = dmc.tick(Direction.RIGHT);
        // Teleports to right hand side of second blue portal.
        expectedPos = new Position(6, 2);
        actualPos = getEntities(res, "player").get(0).getPosition();  
        assertEquals(expectedPos, actualPos);
    }

    @Test
    @DisplayName("Tests player teleport 2")
    public void testPlayerTeleport_2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_basicTeleport", "c_playerTest_basicMovement");

        Position expectedPos = new Position(2, 2);
        Position actualPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, actualPos); 

        // Steps into red portal from it's right hand side.
        res = dmc.tick(Direction.LEFT);
        // Teleports to left hand side of second red portal.
        expectedPos = new Position(5, 8);
        actualPos = getEntities(res, "player").get(0).getPosition();  
        assertEquals(expectedPos, actualPos);
    }


    @Test
    @DisplayName("Tests player can't teleport into a wall")
    public void testPlayerWallTeleport() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_wallTeleport", "c_playerTest_basicMovement");

        Position expectedPos = new Position(2, 2);
        Position actualPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, actualPos); 

        // Steps into blue portal from it's left hand side.
        res = dmc.tick(Direction.RIGHT);

        // Teleports to only free position due to blue portal being nearly surrounded by walls.
        expectedPos = new Position(8, 2);
        actualPos = getEntities(res, "player").get(0).getPosition();  
        assertNotEquals(new Position(10, 2), actualPos);
        assertNotEquals(new Position(9, 3), actualPos);
        assertNotEquals(new Position(9, 1), actualPos);
        assertEquals(expectedPos, actualPos);
    }

    @Test
    @DisplayName("Tests player can't teleport into completely surrounded portal")
    public void testPlayerBlockWallTeleport() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_wallTeleport", "c_playerTest_basicMovement");

        Position expectedPos = new Position(2, 2);
        Position actualPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, actualPos); 

        // Steps into red portal from it's right hand side.
        res = dmc.tick(Direction.LEFT);

        // Player shouldn't teleport due to portal being completely blocked.
        assertEquals(expectedPos, actualPos); 
    }

    @Test
    @DisplayName("Another wall portal test with 2 free positions available")
    public void testPlayerWallTeleport2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_portalTest_wallTeleport2", "c_playerTest_basicMovement");

        Position expectedPos = new Position(2, 2);
        Position actualPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPos, actualPos); 

        // Steps into blue portal from it's left hand side.
        res = dmc.tick(Direction.RIGHT);

        // Teleports to first freely available position around portal.
        expectedPos = new Position(10, 2);
        actualPos = getEntities(res, "player").get(0).getPosition();  
        assertNotEquals(new Position(8, 2), actualPos);
        assertNotEquals(new Position(9, 3), actualPos);
        assertEquals(expectedPos, actualPos);
    }
}
