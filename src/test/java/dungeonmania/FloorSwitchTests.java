package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getEntities;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Static.FloorSwitch;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;


public class FloorSwitchTests {
    @Test
    @DisplayName("Tests basic floor switch press when boulder is pushed ontop")
    public void testFloorSwitchBoulderPress() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_floorSwitchTest_basicPress", "c_playerTest_basicMovement");
        List<Entity> list = dmc.getListOfEntities();

        for (Entity currEntity : list) {
            if (currEntity.getEntityType().equals("switch")) {
                assertEquals(((FloorSwitch) currEntity).getDepressedState(), ((FloorSwitch) currEntity).getState());
            }
        }

        // This will push boulder onto the switch
        res = dmc.tick(Direction.RIGHT);
        Position expectedBoulderPos = new Position(3, 1);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(expectedBoulderPos, actualBoulderPos);

        for (Entity currEntity : list) {
            if (currEntity.getEntityType().equals("switch")) {
                assertEquals(((FloorSwitch) currEntity).getPressedState(), ((FloorSwitch) currEntity).getState());
            }
        }
    }

    @Test
    @DisplayName("Tests boulder moving off floorswitch")
    public void testFloorSwitchBoulderMove() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_floorSwitchTest_basicPress", "c_playerTest_basicMovement");
        List<Entity> list = dmc.getListOfEntities();

        for (Entity currEntity : list) {
            if (currEntity.getEntityType().equals("switch")) {
                assertEquals(((FloorSwitch) currEntity).getDepressedState(), ((FloorSwitch) currEntity).getState());
            }
        }

        // This will push boulder onto the switch
        res = dmc.tick(Direction.RIGHT);
        Position expectedBoulderPos = new Position(3, 1);
        Position actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(expectedBoulderPos, actualBoulderPos);

        for (Entity currEntity : list) {
            if (currEntity.getEntityType().equals("switch")) {
                assertEquals(((FloorSwitch) currEntity).getPressedState(), ((FloorSwitch) currEntity).getState());
            }
        }        

        res = dmc.tick(Direction.RIGHT);
        expectedBoulderPos = new Position(4, 1);
        actualBoulderPos = getEntities(res, "boulder").get(0).getPosition();
        assertEquals(expectedBoulderPos, actualBoulderPos);

        for (Entity currEntity : list) {
            if (currEntity.getEntityType().equals("switch")) {
                assertEquals(((FloorSwitch) currEntity).getDepressedState(), ((FloorSwitch) currEntity).getState());
            }
        }    
    }
}
