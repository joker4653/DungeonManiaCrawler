package dungeonmania;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;

import dungeonmania.Entities.Collectables.*;
import dungeonmania.exceptions.InvalidActionException;



public class BowTests {
    @Test
    @DisplayName("Testing determining viability of building")
    public void isBuildableBow() throws InvalidActionException{
        Bow bow = new Bow(4);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        Arrow arrow1 = new Arrow(0,0);
        Arrow arrow2 = new Arrow(0,1);
        Arrow arrow3 = new Arrow(0,2);
        Wood wood = new Wood(0,0);
        dmc.getInventory().addItem(arrow1);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(arrow2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(arrow3);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood);
        dmc.setTickCount(dmc.getTickCount() + 1);
        assertEquals(true, dmc.getBuildables().contains("bow"));
        dmc.build("bow");
        assertEquals(true, dmc.getInventory().itemExists("bow"));
        assertEquals(true, dmc.getInventory().itemExists("bow"));
        assertEquals(false, dmc.getBuildables().contains("bow"));
    } 

}
