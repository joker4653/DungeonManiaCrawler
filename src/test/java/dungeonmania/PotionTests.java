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



public class PotionTests {
    
    @Test
    @DisplayName("Invisibility and Invincibility potions can be picked up")
    public void PickupPotions() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_potionTest_basicSpawn", "c_treasureTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position expectedPosInvinc = new Position(2, 1);
        Position realPos = getEntities(res, "invisibility_potion").get(0).getPosition();
        Position realPosInvinc = getEntities(res, "invincibility_potion").get(0).getPosition();

        assertEquals(expectedPos, realPos);
        assertEquals(expectedPosInvinc, realPosInvinc);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertFalse(getInventory(res, "invisibility_potion").isEmpty());
        assertFalse(getInventory(res, "invincibility_potion").isEmpty());

    }
}
