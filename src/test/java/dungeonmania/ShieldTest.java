package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.Entities.Collectables.*;
import dungeonmania.exceptions.InvalidActionException;



public class ShieldTest {
   
    

    @Test
    @DisplayName("Testing determining viability of build function")
    public void BuildTestTreasure() throws InvalidActionException{
        Shield shield = new Shield(4, 4);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
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
        assertEquals(true, dmc.getBuildables().contains("shield"));
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.build("shield");
        assertEquals(true, dmc.getInventory().itemExists("shield"));
        assertEquals(false, dmc.getBuildables().contains("shield"));
    }
    @Test
    @DisplayName("Testing determining viability of build function")
    public void BuildTestKey() throws InvalidActionException{
        Shield shield = new Shield(4, 4);
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_swordTest_basicSpawn", "c_swordTest_basicSpawn");
        Wood wood = new Wood(0,0);
        Wood wood2 = new Wood(0,1);
        Akey key = new Akey(0, 2, 4);
        Wood wood3 = new Wood(0,3);
        //List<Entity> entities = dmc.getListOfEntities();
        dmc.getInventory().addItem(wood);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood2);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(wood3);
        dmc.setTickCount(dmc.getTickCount() + 1);
        dmc.getInventory().addItem(key);
        dmc.setTickCount(dmc.getTickCount() + 1);
        //System.out.println(inventory);
        assertEquals(true, dmc.getBuildables().contains("shield"));
        dmc.build("shield");
        assertEquals(true, dmc.getInventory().itemExists("shield"));
        assertEquals(false, dmc.getBuildables().contains("shield"));
    }

}
    

