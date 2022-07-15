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


public class ShieldTest {

    @Test
    @DisplayName("Testing determining viability of building")
    public void isBuildabletresure() {
        Shield shield = new Shield(4, 4);
        Inventory inventory = new Inventory();
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false,shield.isBuildable(shield.Components,inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        inventory.addItem(wood);
        inventory.addItem(wood2);
        inventory.addItem(tre);
        //System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        
    }
   
    @Test
    @DisplayName("Testing deleting collectables") 
    public void Testdelete(){
        Shield shield = new Shield(4, 4);
        Inventory inventory = new Inventory();
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        inventory.addItem(wood);
        inventory.addItem(wood2);
        inventory.addItem(wood3);
        inventory.addItem(tre);
        assertEquals(true,inventory.numitemExists("wood", 3));
        //System.out.println(cont.getIn);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        inventory.RemovingnumItemOfType(2, "wood");
        assertEquals(false,inventory.numitemExists("wood", 2));
        inventory.RemovingnumItemOfType(1, "wood");
        assertEquals(false,inventory.itemExists("wood"));
       
    }
    @Test
    @DisplayName("Testing deleting items from inventory") 
    public void TestDeletingItems() {
        Shield shield = new Shield(4, 4);
        Inventory inventory = new Inventory();
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false,shield.isBuildable(shield.Components,inventory));
        Wood wood = new Wood(0,0);
        Treasure tre = new Treasure(0,2);
        inventory.addItem(wood);
        inventory.addItem(tre);
        assertEquals(true, inventory.itemExists("wood"));
        inventory.RemovingItemOfType("wood");
        assertEquals(false, inventory.itemExists("wood"));    
    }



    @Test
    @DisplayName("Testing determining viability of Build function in Buildable entity") 
    public void BuilableEntityBuildTestShield() {
        Shield shield = new Shield(4, 4);
        Inventory inventory = new Inventory();
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        inventory.addItem(wood);
        inventory.addItem(wood2);
        inventory.addItem(wood3);
        inventory.addItem(tre);
        //System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        shield.BuildShieldTreasure(inventory,shield);
        //System.out.println(inventory);
        assertEquals(true, inventory.itemExists(shield));
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
    }
    

    @Test
    @DisplayName("Testing determining viability of build function")
    public void BuildTest() throws InvalidActionException{
        Shield shield = new Shield(4, 4);
        DungeonManiaController dmc = new DungeonManiaController();
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), dmc.inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        dmc.inventory.addItem(wood);
        dmc.inventory.addItem(wood2);
        dmc.inventory.addItem(wood3);
        dmc.inventory.addItem(tre);
        //System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), dmc.inventory));
        dmc.build("shield");
        assertEquals(true, dmc.inventory.itemExists("shield"));
    }

}