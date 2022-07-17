package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

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
import dungeonmania.DungeonManiaController;
import dungeonmania.Shield;
import dungeonmania.Treasure;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.CollectableEntity;
import dungeonmania.Wood;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.BuildableEntity;


public class BowTests {
    @Test
    @DisplayName("Testing determining viability of building")
    public void isBuildableBow() throws InvalidActionException{
        Bow bow = new Bow(4);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, bow.isBuildable(bow.bowMaterials(), dmc.inventory));
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        Arrow arrow3 = new Arrow(0,2);
        Wood wood = new Wood(0,0);
        dmc.inventory.addItem(arrow1);
        dmc.inventory.addItem(arrow2);
        dmc.inventory.addItem(arrow3);
        dmc.inventory.addItem(wood);
        assertEquals(true, bow.isBuildable(bow.bowMaterials(), dmc.inventory));
        dmc.build("bow");
        assertEquals(true, dmc.inventory.itemExists("bow"));
        assertEquals(false, bow.isBuildable(bow.bowMaterials(), dmc.inventory));
    } 

}
