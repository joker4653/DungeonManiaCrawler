package dungeonmania;
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
    public void isBuildabletresure() {
        Bow bow = new Bow(4, 4);
        ArrayList<Entity> inventory = new ArrayList<Entity>();
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        Arrow arrow3 = new Arrow(0,2);
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        inventory.add(arrow1);
        inventory.add(arrow2);
        inventory.add(arrow3);
        inventory.add(wood);
        System.out.println(inventory);
        assertEquals(true, shield.isBuildable(shield.shieldMaterialsTreasure(), inventory));
        
    }    
}
