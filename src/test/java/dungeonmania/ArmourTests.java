package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.Entities.Collectables.*;
import dungeonmania.exceptions.InvalidActionException;


public class ArmourTests {
    @Test
    @DisplayName("Testing determining viability of building")
    public void isBuildableArmour() throws InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        //DungeonResponse res = 
        dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        SunStone sunStone = new SunStone(0,0);
        Sword sword = new Sword(0, 1, 2, 2);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sword);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, dmc.getBuildables().contains("midnight_armour"));
        dmc.build("midnight_armour");
        assertEquals(true, dmc.getInventory().itemExists("midnight_armour"));
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
    }
    @Test
    @DisplayName("Testing zombie prevents building")
    public void CannotbuildWithZombie() throws InvalidActionException {
       
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_armourTest_zombieexists", "c_swordTest_basicSpawn");
        
      
        SunStone sunStone = new SunStone(0,0);
        Sword sword = new Sword(0, 1, 2, 2);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sword);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
        dmc.build("midnight_armour");
        assertEquals(false, dmc.getInventory().itemExists("midnight_armour"));
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
    } 
}
