package dungeonmania;

import java.util.List;
import java.util.ArrayList;

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
import dungeonmania.Treasure;
import dungeonmania.BuildableEntity;


public class ShieldTest {

    @Test
    @DisplayName("Testing determining viability of building");
    public void isBuildable() {
        Shield shield = new Shield(4, 4);
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        System.out.println(inventory);
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Treasure tre = new Treasure(0,2);
        inventory.add(wood);
        inventory.add(wood2);
        inventory.add(tre);
        System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        shield.isBuildable(shield.shieldMaterialsTreasure(), inventory);
    }
    
}