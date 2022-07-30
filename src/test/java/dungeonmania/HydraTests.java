package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static dungeonmania.TestUtils.getEntities;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class HydraTests {
    // Helper function:
    private boolean isActualPosAdjacent(Position currHydraPos, Position actualHydraPos) {
        List<Position> listOfAdjPos = new ArrayList<>();
        listOfAdjPos.add(new Position(currHydraPos.getX(), currHydraPos.getY()));
        listOfAdjPos.add(new Position(currHydraPos.getX(), currHydraPos.getY() - 1));
        listOfAdjPos.add(new Position(currHydraPos.getX(), currHydraPos.getY() + 1));
        listOfAdjPos.add(new Position(currHydraPos.getX() - 1, currHydraPos.getY()));
        listOfAdjPos.add(new Position(currHydraPos.getX() + 1, currHydraPos.getY()));

        return listOfAdjPos.contains(actualHydraPos);
    }


    // Hydra move tests:
    @Test
    @DisplayName("Test hydras can't move into a wall, zombie toast spawner, boulder and locked doors.")
    public void testHydraMovementConstraints() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_movementBlocked", "c_hydraTest_basic");
        Position expectedPos = new Position(1, 2);

        // hydra is forced to stay where it is, since it is surrounded by entities it can't step on.
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.DOWN);
            res = dmc.tick(Direction.DOWN);
            res = dmc.tick(Direction.DOWN);

            Position actualHydraPos = getEntities(res, "hydra").get(0).getPosition();
            assertEquals(expectedPos, actualHydraPos);
        }

        assertEquals(1, getEntities(res, "hydra").size());
    }


    /*  TODO UNCOMMENT THIS WHEN DOOR/KEY HAVE BEEN IMPLEMENTED
    @Test
    @DisplayName("Test hydras can move through open doors")
    public void testHydraWalkThroughOpenDoor() {
        //
        //  player   key      door    
        //           wall     hydra     zombie_toast_spawner
        //                    wall      exit
        //
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_moveOpenDoor", "c_hydraTest_basic");
        Position expectedPosInitial = getEntities(res, "hydra").get(0).getPosition();

        // currently, the hydra is stuck. It stays where it is.
        res = dmc.tick(Direction.RIGHT);
        Position actualHydraPos1 = getEntities(res, "hydra").get(0).getPosition();
        assertEquals(expectedPosInitial, actualHydraPos1);

        res = dmc.tick(Direction.RIGHT);
        Position playerPos = getPlayer(res).get().getPosition();
        // since the player has opened the door, the hydra can move onto the open door tile to fight the player and win,
        // OR the hydra can stay where it is currently.
        Position actualHydraPos2 = getEntities(res, "hydra").get(0).getPosition();
        assertTrue(actualHydraPos2.equals(playerPos) || actualHydraPos2.equals(expectedPosInitial));
    }*/

    @Test
    @DisplayName("Test hydras can move up, down, left, right or stay at their current location")
    public void testHydraCorrectMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_moveBasic", "c_hydraTest_basic");
        Position currHydra1Pos;
        Position currHydra2Pos;
        Position actualHydra1Pos;
        Position actualHydra2Pos;
        boolean isHydra1AdjPosCorrect;
        boolean isHydra2AdjPosCorrect;

        // check that the 2 hydras' movements are correct for 5 ticks.
        for (int i = 0; i < 4; i++) {
            // current hydra positions
            currHydra1Pos = getEntities(res, "hydra").get(0).getPosition();
            currHydra2Pos = getEntities(res, "hydra").get(1).getPosition();

            res = dmc.tick(Direction.DOWN);

            // new hydra positions (should be adjacent to the current hydra positions)
            actualHydra1Pos = getEntities(res, "hydra").get(0).getPosition();
            actualHydra2Pos = getEntities(res, "hydra").get(1).getPosition();

            isHydra1AdjPosCorrect = isActualPosAdjacent(currHydra1Pos, actualHydra1Pos);
            isHydra2AdjPosCorrect = isActualPosAdjacent(currHydra2Pos, actualHydra2Pos);

            assertTrue(isHydra1AdjPosCorrect);
            assertTrue(isHydra2AdjPosCorrect);
        }
    }

}
