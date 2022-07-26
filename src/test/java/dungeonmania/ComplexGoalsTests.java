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

/*
 * config
 *  - So far, just the one will do.
 *  - New config for nested goals.
 *
 * dungeons:
 *  - one for basic and goal (same for basic or goal #1!)
 *  - one for basic or goal
 *  - one for or goal other way
 *  - one for AND goal exit done first (same as basic or goal #2 above!)
 *  - One for AND nested goal (boulder - right left treasure - down exit - right).
 *  - AND/OR nested goal - map same as above.
 *  - AND/OR nested goal testing OR - map same as above.
 *  - OR/AND nested goal testing OR - map same as above.
 */

public class ComplexGoalsTests {

    @Test
    @DisplayName("Test a basic AND complex goal completion.")
    public void testBasicANDGoal() {
        /*      player  treasure    exit
         *
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_basicAND", "c_complexGoalsTest_basic");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:treasure AND :exit)"));

        res = dmc.tick(Direction.RIGHT);

        goal = getGoals(res); 
        assertEquals(":exit", goal);

        res = dmc.tick(Direction.RIGHT);

        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }

    @Test
    @DisplayName("Test a basic OR complex goal completion (first goal).")
    public void testBasicORGoalA() {
        /*      player  treasure    exit
         *
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_basicOR_A", "c_complexGoalsTest_basic");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:treasure OR :exit)"));

        res = dmc.tick(Direction.RIGHT);

        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }

    @Test
    @DisplayName("Test a basic OR complex goal completion (second goal).")
    public void testBasicORGoalB() {
        /*      player  exit  treasure
         *
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_basicOR_B", "c_complexGoalsTest_basic");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:treasure OR :exit)"));

        res = dmc.tick(Direction.RIGHT);

        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }


    @Test
    @DisplayName("Test AND complex goal where exit found before completion.")
    public void testANDGoalExitFirst() {
        /*      player exit treasure
         *
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_ANDExitFirst", "c_complexGoalsTest_basic");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:treasure AND :exit)"));

        res = dmc.tick(Direction.RIGHT);

        goal = getGoals(res); 
        assertEquals("(:treasure AND :exit)", goal);

        res = dmc.tick(Direction.RIGHT);

        goal = getGoals(res); 
        assertTrue(goal.equals(":exit"));

        res = dmc.tick(Direction.LEFT);

        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }

    @Test
    @DisplayName("Test a nested AND complex goal completion.")
    public void testNestedANDGoal() {
        /*       player   boulder   switch
         *      treasure   exit
         *  (right left, down, right)
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_nestedANDGoal", "c_complexGoalsTest_nested");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:treasure AND (:boulders AND :exit))"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);

        // Boulders complete.
        goal = getGoals(res); 
        assertEquals("(:treasure AND :exit)", goal);

        res = dmc.tick(Direction.DOWN);

        // Treasure complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(":exit"));

        res = dmc.tick(Direction.RIGHT);

        // Exit complete, complex goal should be complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }

    @Test
    @DisplayName("Test a nested AND/OR complex goal completion, checking order doesn't matter.")
    public void testNestedANDThenORGoalA() {
        /*       player   boulder   switch
         *      treasure   exit
         *  (right left, down, right)
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_nestedANDThenORGoal", "c_complexGoalsTest_nested");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:exit AND (:boulders OR :treasure))"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);

        // Boulders complete.
        goal = getGoals(res); 
        assertEquals(":exit", goal);

        res = dmc.tick(Direction.DOWN);

        // Treasure complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(":exit"));

        res = dmc.tick(Direction.RIGHT);

        // Exit complete, complex goal should be complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }

    @Test
    @DisplayName("Test a nested AND/OR complex goal completion, checking OR working.")
    public void testNestedANDThenORGoalB() {
        /*       player   boulder   switch
         *      treasure   exit
         *  (right left, down, right)
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_nestedANDThenORGoal", "c_complexGoalsTest_nested");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:exit AND (:boulders OR :treasure))"));

        res = dmc.tick(Direction.DOWN);

        // Treasure complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(":exit"));

        res = dmc.tick(Direction.RIGHT);

        // Exit complete, complex goal should be complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }

    @Test
    @DisplayName("Test a nested OR/AND complex goal completion.")
    public void testNestedORThenANDGoal() {
        /*       player   boulder   switch
         *      treasure   exit
         *  (right left, down, right)
         */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_nestedORThenANDGoal", "c_complexGoalsTest_nested");
        String goal = getGoals(res); 
        assertTrue(goal.equals("(:exit OR (:boulders AND :treasure))"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);

        // Boulders complete.
        goal = getGoals(res); 
        assertTrue(goal.equals("(:exit OR :treasure)"));

        res = dmc.tick(Direction.DOWN);

        // Treasure complete.
        goal = getGoals(res); 
        assertTrue(goal.equals(""));
    }




}

