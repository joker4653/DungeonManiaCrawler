package dungeonmania;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.io.Serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dungeonmania.Entities.Entity;

import dungeonmania.Goal.BooleanGoal;
import dungeonmania.Goal.SimpleGoal;
import dungeonmania.Goal.ComplexGoal;

public class Statistics implements Serializable {

    private BooleanGoal goal;

    private boolean reachedAnExit = false;

    private int enemiesDestroyed = 0;
    private int enemiesGoal;

    private int spawnersDestroyed = 0;
    private int spawnersCreated;

    private int floorSwitchesDepressed = 0;
    private int floorSwitchesCreated;

    private int treasureCollected = 0;
    private int treasureGoal;

    public Statistics(JsonObject jsonGoals, List<Entity> listOfEntities, HashMap<String, String> configMap) {
        
        this.goal = generateGoals(jsonGoals);

        this.floorSwitchesCreated = listOfEntities.stream().filter(e -> e.getEntityType().equals("switch")).collect(Collectors.toList()).size();
        this.spawnersCreated = listOfEntities.stream().filter(e -> e.getEntityType().equals("zombie_toast_spawner")).collect(Collectors.toList()).size();

        this.enemiesGoal = Integer.parseInt(configMap.get("enemy_goal"));
        this.treasureGoal = Integer.parseInt(configMap.get("treasure_goal"));
    }


    public String getGoals() {
        return goal.prettyPrint(); // False cos not called within BooleanGoal to child goal.
    }

    public void addEnemyDestroyed() {
        this.enemiesDestroyed += 1;
        checkEnemiesGoal();
    }
    
    public void addSpawnerDestroyed() {
        this.spawnersDestroyed += 1;
        checkEnemiesGoal();
    }

    public void addTreasureCollected() {
        this.treasureCollected += 1;
        checkTreasureGoal();
    }

    public void addFloorSwitch() {
        this.floorSwitchesDepressed += 1;
        checkSwitchGoal();
    }

    public void removeFloorSwitch() {
        this.floorSwitchesDepressed -= 1;
        checkSwitchGoal();
    }

    public void reachedExit() {
        reachedAnExit = true;
        checkExitGoal();
    }

    public void notOnExit() {
        reachedAnExit = false;
        checkExitGoal();
    }

    private void addGoal(String goalStr) {
        goal.makeIncomplete(goalStr);
    }

    private void removeGoal(String goalStr) {
        goal.makeComplete(goalStr);
    }

    private void checkEnemiesGoal() {
        if (enemiesDestroyed >= enemiesGoal && spawnersDestroyed >= spawnersCreated) {
            removeGoal(":enemies");
        }
    }

    private void checkTreasureGoal() {
        if (treasureCollected >= treasureGoal) {
            removeGoal(":treasure");
        }
    }

    private void checkSwitchGoal() {
        if (floorSwitchesDepressed >= floorSwitchesCreated) {
            removeGoal(":boulders");
        } else {
            addGoal(":boulders");
        }
    }

    private void checkExitGoal() {
        if (reachedAnExit) {
            removeGoal(":exit");
            
            // If exit is not the final goal, we should not set it yet.
            if (!goal.isComplete()) {
                addGoal(":exit");
            }
        } else {
            addGoal(":exit");
        }
    }

    private BooleanGoal generateGoals(JsonObject jsonGoals) {
        String goalType = jsonGoals.get("goal").getAsString();

        BooleanGoal goal = null;

        if (goalType.equals("AND") || goalType.equals("OR")) {
            // Create complex goal.
            JsonArray subGoals = (JsonArray) jsonGoals.get("subgoals");

            JsonObject subGoalObj1 = (JsonObject) subGoals.get(0);
            JsonObject subGoalObj2 = (JsonObject) subGoals.get(1);

            BooleanGoal subGoal1 = generateGoals(subGoalObj1);
            BooleanGoal subGoal2 = generateGoals(subGoalObj2);

            if (subGoal1 != null && subGoal2 != null) {
                goal = new ComplexGoal(goalType, subGoal1, subGoal2);
            }
        } else {
            // Create simple goal.
            String name = ":" + goalType;
            goal = new SimpleGoal(name);
        }

        return goal;
    }
}

