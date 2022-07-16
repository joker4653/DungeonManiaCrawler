package dungeonmania;

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

public class StatisticsTests {

    @Test
    @DisplayName("Test response when no goals have been completed.")
    public void testNoGoalsCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_statsTest_noGoalsCompleted");

        String goals = getGoals(res); 

        String[] goalsList = goals.split("\\s+");

        assertTrue(goalsList.contains(":exit");
    }

    @Test
    @DisplayName("Test response when enemies goal has been completed.")
    public void testEnemiesGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_statsTest_enemiesComplete", "c_statsTest_enemiesCompleted");

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "spider"));

        dmc.tick(Direction.RIGHT);

        String goals = getGoals(res); 

        String[] goalsList = goals.split("\\s+");

        assertFalse(goalsList.contains(":enemies");
    }

    @Test
    @DisplayName("Test response when enemies (including spawner) goal has been completed.")
    public void testEnemiesAndSpawnerGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_statsTest_enemySpawnerComplete", "c_statsTest_enemySpawnerComplete");

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "zombie"));

        dmc.tick(Direction.RIGHT);

        EntityResponse spawner = getEntities(res, "zombie_toast_spawner").get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(spawner.getId());
        });

        String goals = getGoals(res); 

        String[] goalsList = goals.split("\\s+");

        assertFalse(goalsList.contains(":enemies");
    }


    @Test
    @DisplayName("Test response when treasure goal has been completed.")
    public void testTreasureGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_treasureComplete", "c_statsTest_basicTreasure");
        dmc.tick(Direction.RIGHT);

        String goals = getGoals(res); 

        String[] goalsList = goals.split("\\s+");

        assertFalse(goalsList.contains(":treasure");
    }


    @Test
    @DisplayName("Test response when boulders goal has been completed.")
    public void testBouldersGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_boulderGoal", "c_statsTest_noGoalsCompleted");

        dmc.tick(Direction.RIGHT);

        // Boulder goal should be complete.
        String goals = getGoals(res); 
        String[] goalsList = goals.split("\\s+");

        assertFalse(goalsList.contains(":boulders");

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

        // After moving again, boulder goal should once again be incomplete.
        String goals = getGoals(res); 
        String[] goalsList = goals.split("\\s+");

        assertTrue(goalsList.contains(":exit");
    }


    @Test
    @DisplayName("Test response when exit goal has been completed.")
    public void testExitGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_exit", "c_statsTest_noGoalsCompleted");

        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);

        String goals = getGoals(res); 

        String[] goalsList = goals.split("\\s+");

        assertFalse(goalsList.contains(":exit");
    }

    @Test
    @DisplayName("Test response when large treasure goal has been completed.")
    public void testLargeTreasureGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_treasureComplete", "c_statsTest_largeTreasure");
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

        // Treasure goal should not yet be completed
        String goals = getGoals(res); 
        String[] goalsList = goals.split("\\s+");

        assertTrue(goalsList.contains(":treasure");
        
        dmc.tick(Direction.RIGHT);

        // Treasure should now be complete.
        String goals = getGoals(res); 
        String[] goalsList = goals.split("\\s+");

        assertFalse(goalsList.contains(":treasure");
    }
}

