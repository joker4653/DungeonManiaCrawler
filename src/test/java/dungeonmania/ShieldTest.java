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
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        inventory.add(wood);
        inventory.add(wood2);
        inventory.add(tre);
        System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        
    }
   
    @Test
    @DisplayName("Testing deleting collectables") 
    public void Testdelete(){
        Shield shield = new Shield(4, 4);
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        inventory.add(wood);
        inventory.add(wood2);
        inventory.add(wood3);
        inventory.add(tre);
        System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        shield.numItemDelete("wood", 2, inventory);
        System.out.println(inventory);
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
    }
    @Test
    @DisplayName("Testing determining viability of Build function in Buildable entity") 
    public void BuilableEntityBuildTestShield() {
        Shield shield = new Shield(4, 4);
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        inventory.add(wood);
        inventory.add(wood2);
        inventory.add(wood3);
        inventory.add(tre);
        System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        shield.BuildShieldTreasure(inventory,shield);
        System.out.println(inventory);
        assertEquals(true, inventory.contains(shield));
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
    }
    

    @Test
    @DisplayName("Testing determining viability of build function")
    public void BuildTest() throws InvalidActionException{
        Shield shield = new Shield(4, 4);
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        Wood wood3 = new Wood(0,3);
        inventory.add(wood);
        inventory.add(wood2);
        inventory.add(wood3);
        inventory.add(tre);
        //System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        

    }

}