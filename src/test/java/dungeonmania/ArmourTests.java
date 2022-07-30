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
        
        MidnightArmour armour = new MidnightArmour(1, 1);
        DungeonManiaController dmc = new DungeonManiaController();
        //DungeonResponse res = 
        dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
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
        assertEquals(false, armour.isBuildable(armour.getComponents(), dmc.getInventory()));
        assertEquals(true, dmc.getInventory().itemExists("midnight_armour"));
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
    }
    @Test
    @DisplayName("Testing zombie prevents building")
    public void CannotbuildWithZombie() throws InvalidActionException {
       
        MidnightArmour armour = new MidnightArmour(1, 1);
        DungeonManiaController dmc = new DungeonManiaController();
        //DungeonResponse res = 
        dmc.newGame("d_armourTest_zombieexists", "c_swordTest_basicSpawn");
        //System.out.println(inventory);
        //System.out.println(shield.shieldMaterialsTreasure());
        assertEquals(false, armour.isBuildable(armour.getComponents(), dmc.getInventory()));
        SunStone sunStone = new SunStone(0,0);
        Sword sword = new Sword(0, 1, 2, 2);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sword);
        dmc.setTickCount(dmc.getTickCount() + 1);
        //Checking components are sufficient to build armour but it won't be in list anyway because of zombie
        assertEquals(true, armour.isBuildable(armour.Components, dmc.getInventory()));
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
        dmc.build("midnight_armour");
        //as parts are not consumed as build failed parts should still be sufficient to build it
        assertEquals(true, armour.isBuildable(armour.getComponents(), dmc.getInventory()));
        //Item should not be in inventory nor in buildables
        assertEquals(false, dmc.getInventory().itemExists("midnight_armour"));
        assertEquals(false, dmc.getBuildables().contains("midnight_armour"));
    } 
}
