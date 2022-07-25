package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class StatisticsTests {

    @Test
    @DisplayName("Test response when no goals have been completed.")
    public void testNoGoalsCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_playerTest_basicMovement", "c_statsTest_noGoalsCompleted");

        String goals = getGoals(res); 

        assertTrue(goals.contains(":exit"));
    }

    @Test
    @DisplayName("Test response when enemies goal has been completed.")
    public void testEnemiesGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_enemiesComplete", "c_statsTest_enemiesCompleted");

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "spider"));

        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(0, countEntityOfType(res, "spider"));

        String goals = getGoals(res); 

        assertFalse(goals.contains(":enemies"));
    }

    @Test
    @DisplayName("Test response when enemies (including spawner) goal has been completed.")
    public void testEnemiesAndSpawnerGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_enemySpawnerComplete", "c_statsTest_enemySpawnerComplete");

        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "zombie_toast"));

        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, countEntityOfType(res, "zombie_toast"));

        EntityResponse spawner = getEntities(res, "zombie_toast_spawner").get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(spawner.getId());
        });

        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, countEntityOfType(res, "zombie_toast_spawner"));

        String goals = getGoals(res); 

        assertFalse(goals.contains(":enemies"));
    }


    @Test
    @DisplayName("Test response when treasure goal has been completed.")
    public void testTreasureGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_treasureComplete", "c_statsTest_basicTreasure");
        res = dmc.tick(Direction.RIGHT);

        String goals = getGoals(res); 

        assertFalse(goals.contains(":treasure"));
    }


    @Test
    @DisplayName("Test response when boulders goal has been completed.")
    public void testBouldersGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_boulderGoal", "c_statsTest_noGoalsCompleted");

        res = dmc.tick(Direction.RIGHT);

        // Boulder goal should be complete.
        String goals = getGoals(res); 

        assertFalse(goals.contains(":boulders"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // After moving again, boulder goal should once again be incomplete.
        goals = getGoals(res); 

        assertTrue(goals.contains(":boulders"));
    }

    @Test
    @DisplayName("Test response when multiple boulders goal has been completed.")
    public void testMultiBouldersGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_boulderMultiGoal", "c_statsTest_noGoalsCompleted");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        res = dmc.tick(Direction.RIGHT);

        // Boulder goal should be complete.
        String goals = getGoals(res); 

        assertFalse(goals.contains(":boulders"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // After moving again, boulder goal should once again be incomplete.
        goals = getGoals(res); 

        assertTrue(goals.contains(":boulders"));
    }

    @Test
    @DisplayName("Test response when exit goal has been completed.")
    public void testExitGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_exit", "c_statsTest_noGoalsCompleted");

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        String goals = getGoals(res); 

        assertFalse(goals.contains(":exit"));
    }

    @Test
    @DisplayName("Test response when large treasure goal has been completed.")
    public void testLargeTreasureGoalCompleted() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_statsTest_treasureComplete", "c_statsTest_largeTreasure");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Treasure goal should not yet be completed
        String goals = getGoals(res); 

        assertTrue(goals.contains(":treasure"));
        
        res = dmc.tick(Direction.RIGHT);

        // Treasure should now be complete.
        goals = getGoals(res); 

        assertFalse(goals.contains(":treasure"));
    }
}

