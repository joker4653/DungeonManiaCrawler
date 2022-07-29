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
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Entities.Collectables.*;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.BuildableEntity;


public class ShieldTest {
   
    

    @Test
    @DisplayName("Testing determining viability of build function")
    public void BuildTestTreasure() throws InvalidActionException{
        Shield shield = new Shield(4, 4);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), dmc.getInventory()));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        dmc.getInventory().addItem(wood);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood3);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(tre);
        dmc.setTickCount(dmc.getTickCount() + 1);
        //System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), dmc.getInventory()));
        assertEquals(true, dmc.getBuildables().contains("shield"));
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.build("shield");
        assertEquals(true, dmc.getInventory().itemExists("shield"));
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), dmc.getInventory()));
        assertEquals(false, dmc.getBuildables().contains("shield"));
    }
    @Test
    @DisplayName("Testing determining viability of build function")
    public void BuildTestKey() throws InvalidActionException{
        Shield shield = new Shield(4, 4);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), dmc.getInventory()));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Akey key = new Akey(0, 2, 4);
        Wood wood3 = new Wood(0,3);
        List<Entity> entities = dmc.getListOfEntities();
        dmc.getInventory().addItem(wood);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood3);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(key);
        dmc.setTickCount(dmc.getTickCount() + 1);
        //System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsKey(), dmc.getInventory()));
        assertEquals(true, dmc.getBuildables().contains("shield"));
        dmc.build("shield");
        assertEquals(true, dmc.getInventory().itemExists("shield"));
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsKey(), dmc.getInventory()));
        assertEquals(false, dmc.getBuildables().contains("shield"));
    }

}
    

