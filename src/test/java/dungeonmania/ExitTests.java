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

public class ExitTests {
    @Test
    @DisplayName("Tests exit has been created")
    public void testExitCreation() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_exitTest_basicExit", "c_playerTest_basicMovement");

        Position expectedExitPos = new Position(3, 2);
        Position actualExitPos = getEntities(res, "exit").get(0).getPosition();
        assertEquals(expectedExitPos, actualExitPos);
    }

    @Test
    @DisplayName("Tests exit state when player walks through exit")
    public void testExitState() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_exitTest_basicExit", "c_playerTest_basicMovement");

        // Should be false since player hasn't moved yet.
        assertEquals(getExitStateHelper(dmc), false);
        
        res = dmc.tick(Direction.RIGHT);
        
        // Should still be false since player hasn't reached exit yet.
        assertEquals(getExitStateHelper(dmc), false);
 
        res = dmc.tick(Direction.RIGHT);
        // Checks if player is on exit
        Position expectedPlayerPos = new Position(3, 2);
        Position actualPlayerPos = getEntities(res, "player").get(0).getPosition();
        assertEquals(expectedPlayerPos, actualPlayerPos);    
        // Exit State should now be true as player is at exit.
        assertEquals(getExitStateHelper(dmc), true);
    }

    // TODO: Test when goals has been implemented.

    public boolean getExitStateHelper(DungeonManiaController dmc) {
        List<Entity> list = dmc.getListOfEntities();
        for (Entity currEntity : list) {
            if (currEntity.getEntityType().equals("exit")) {
                if (((Exit) currEntity).isExitState()) {
                    return true;
                }
            }
        }

        return false;
    }
}

