package dungeonmania;

import java.util.List;
import java.io.Serializable;
import java.util.HashMap;

import java.util.stream.Collectors;

import dungeonmania.Entities.Entity;

public class Statistics implements Serializable {

    private HashMap<String, Boolean> goals;

    private boolean reachedAnExit = false;

    private int enemiesDestroyed = 0;
    private int enemiesGoal;

    private int spawnersDestroyed = 0;
    private int spawnersCreated;

    private int floorSwitchesDepressed = 0;
    private int floorSwitchesCreated;

    private int treasureCollected = 0;
    private int treasureGoal;

    public Statistics(HashMap<String, Boolean> goals, List<Entity> listOfEntities, HashMap<String, String> configMap) {
        this.goals = goals;
        this.spawnersCreated = listOfEntities.stream().filter(e -> e.getEntityType().equals("zombie_toast_spawner")).collect(Collectors.toList()).size();

        this.enemiesGoal = Integer.parseInt(configMap.get("enemy_goal"));
        this.treasureGoal = Integer.parseInt(configMap.get("treasure_goal"));
    }

    public HashMap<String, Boolean> getGoals() {
        return this.goals;
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

    private void addGoal(String goal) {
        if (goals.containsKey(goal)) {
            goals.replace(goal, false);
        }
    }

    private void removeGoal(String goal) {
        if (goals.containsKey(goal)) {
            goals.replace(goal, true);
        }
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
        }
    }
}

