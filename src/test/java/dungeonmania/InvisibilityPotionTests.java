package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import spark.utils.Assert;

public class InvisibilityPotionTests {
    
    @Test
    @DisplayName("Placing InvisPot into map at (1,1)")
    public void PlaceInvisPotion() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_InvisTest_basicSpawn", "c_InvisTest_basicSpawn");

        Position expectedPos = new Position(1, 1);
        Position realPos = getEntities(res, "invisibility_potion").get(0).getPosition();
        assertEquals(expectedPos, realPos);
    }

    @Test
    @DisplayName("Picking up Invis Potion") 
    public void PickUpInvisPotion() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_InvisTest_basicSpawn", "c_InvisTest_basicSpawn");

        res = dmc.tick(Direction.RIGHT);

        assertFalse(getInventory(res, "invisibility_potion").isEmpty());
    }

    @Test
    @DisplayName("Test Mercenary movement while player under effects of Invis")
    public void MercMovementUnderInvis() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_InvisTest_MercMove", "c_InvisTest_MercMove");
        String potion = getEntities(res, "invisibility_potion").get(0).getId();

        res = dmc.tick(Direction.RIGHT);

        res = dmc.tick(potion);


        for (int i = 0; i < 5; i++) {
            Position mercPos = getEntities(res, "mercenary").get(0).getPosition();
            List<Position> AdjPositions = new ArrayList<Position>(Arrays.asList(new Position(mercPos.getX() + 1, mercPos.getY()), 
                                                                                new Position(mercPos.getX() - 1, mercPos.getY()),
                                                                                new Position(mercPos.getX(), mercPos.getY() + 1),
                                                                                new Position(mercPos.getX(), mercPos.getY() - 1)
                                                                                ));
            res = dmc.tick(Direction.RIGHT);

            assertTrue(AdjPositions.contains(getEntities(res, "mercenary").get(0).getPosition()));
        }
    }

    /* 
    @Test
    @DisplayName("Potion only lasts for limited time")
    public void PotionOnlyLastsLimitedTime() {
        assertTrue(false);
    } */

    @Test
    @DisplayName("Exception thrown when attempting to use item not in inventory")
    public void attemptUseItemNotInInv() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_InvisTest_MercMove", "c_InvisTest_MercMove");

        assertThrows(InvalidActionException.class, () -> {
                                                                    dmc.tick("I dont exist");
                                                                        });
    }

    @Test
    @DisplayName("Exception thrown when attempting to use unusable item")
    public void attempttoUseUnusableItem() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_woodTest_basicSpawn", "c_woodTest_basicSpawn");
        String entity = getEntities(res, "wood").get(0).getId();

        dmc.tick(Direction.RIGHT);

        assertThrows(IllegalArgumentException.class, () -> {
                                                                    dmc.tick(entity);
                                                                        });

        res = dmc.tick(Direction.RIGHT);
    }
}
