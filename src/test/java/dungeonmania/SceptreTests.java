/*
package dungeonmania;


import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.Entities.Collectables.*;
import dungeonmania.Entities.Moving.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.Entities.Entity;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;



public class SceptreTests {
    @Test
    @DisplayName("Testing determining viability of build function")
    public void TestBuildsinRightOrder1() throws InvalidActionException{
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        Treasure tre = new Treasure(0,2);
        SunStone sunStone = new SunStone(0,3);
        Wood wood = new Wood(0,0);
        Akey key = new Akey(0, 2, 4);
        dmc.getInventory().addItem(arrow1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(arrow2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(tre);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(key);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, dmc.getBuildables().contains("sceptre"));
        dmc.build("sceptre");
        assertEquals(true, dmc.getInventory().itemExists("sceptre"));
        assertEquals(false, dmc.getInventory().itemExists("wood"));
        assertEquals(true, dmc.getInventory().itemExists("arrow"));
        assertEquals(true, dmc.getInventory().itemExists("key"));
        assertEquals(false, dmc.getInventory().itemExists("treasure"));
        assertEquals(false, dmc.getInventory().itemExists("sun_stone"));
        assertEquals(false, dmc.getBuildables().contains("sceptre"));
    } 
    @Test
    @DisplayName("Testing determining viability of build function")
    public void TestBuildsinRightOrder2() throws InvalidActionException{
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        Treasure tre = new Treasure(0,2);
        SunStone sunStone = new SunStone(0,3);
        Akey key = new Akey(0, 2, 4);
        dmc.getInventory().addItem(arrow1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(arrow2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(tre);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(key);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, dmc.getBuildables().contains("sceptre"));
        dmc.build("sceptre");
        assertEquals(true, dmc.getInventory().itemExists("sceptre"));
        assertEquals(false, dmc.getInventory().itemExists("arrow"));
        assertEquals(true, dmc.getInventory().itemExists("key"));
        assertEquals(false, dmc.getInventory().itemExists("treasure"));
        assertEquals(false, dmc.getInventory().itemExists("sun_stone"));
        assertEquals(false, dmc.getBuildables().contains("sceptre"));
    }
    @Test
    @DisplayName("Testing determining viability of build function")
    public void TestBuildsinRightOrder3() throws InvalidActionException{
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        SunStone sunStone = new SunStone(0,3);
        Wood wood = new Wood(0,0);
        Akey key = new Akey(0, 2, 4);
        dmc.getInventory().addItem(arrow1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(arrow2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(key);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, dmc.getBuildables().contains("sceptre"));
        dmc.build("sceptre");
        assertEquals(true, dmc.getInventory().itemExists("sceptre"));
        assertEquals(false, dmc.getInventory().itemExists("wood"));
        assertEquals(false, dmc.getInventory().itemExists("key"));
        assertEquals(false, dmc.getInventory().itemExists("sun_stone"));
        assertEquals(false, dmc.getBuildables().contains("sceptre"));
    }
    @Test
    @DisplayName("Testing determining viability of build function")
    public void TestBuildsinRightOrder4() throws InvalidActionException{
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        SunStone sunStone = new SunStone(0,3);
        Akey key = new Akey(0, 2, 4);
        dmc.getInventory().addItem(arrow1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(arrow2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(sunStone);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(key);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, dmc.getBuildables().contains("sceptre"));
        dmc.build("sceptre");
        assertEquals(true, dmc.getInventory().itemExists("sceptre"));
        assertEquals(false, dmc.getInventory().itemExists("arrow"));
        assertEquals(false, dmc.getInventory().itemExists("key"));
        assertEquals(false, dmc.getInventory().itemExists("sun_stone"));
        assertEquals(false, dmc.getBuildables().contains("sceptre"));
    }


    @Test
    @DisplayName("Test bewitch mercenary from afar")
    public void testBewitchMercenary() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_nogold2", "c_mercenaryTest_followPlayer2");

        EntityResponse merc = getEntities(res, "mercenary").get(0);

        assertThrows(InvalidActionException.class, () -> {
            dmc.interact(merc.getId());
        });

        assertEquals(true, dmc.getunwittingVictimsofMagicalTyranny().size() == 1);
    }
}

*/