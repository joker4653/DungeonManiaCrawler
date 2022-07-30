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
import dungeonmania.Entities.Collectables.Treasure;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Entities.Collectables.CollectableEntity;
import dungeonmania.Entities.Collectables.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.BuildableEntity;



public class ArmourTests {
    @Test
    @DisplayName("Testing determining viability of building")
    public void isBuildableArmour() throws InvalidActionException, NumberFormatException{
        MidnightArmour armour = new MidnightArmour(1, 1);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, armour.isBuildable(armour.getComponents(), dmc.getInventory()));
        SunStone sunStone = new SunStone(0,0);
        Sword sword = new Sword(0, 1, 2, 2);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sword);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, armour.isBuildable(armour.Components, dmc.getInventory()));
        assertEquals(true, dmc.getBuildables().contains("midnight_armour"));
        dmc.build("midnight_armour");
        assertEquals(true, dmc.getInventory().itemExists("midnight_armour"));
        assertEquals(false, armour.isBuildable(armour.getComponents(), dmc.getInventory()));
        assertEquals(true, dmc.getInventory().itemExists("midnight_armour"));
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
    } 
}
