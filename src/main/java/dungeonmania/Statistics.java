package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.stream.Collectors;

public class Statistics {

    private ArrayList<String> listOfGoals;

    private boolean reachedAnExit = false;

    private int enemiesDestroyed = 0;
    private int enemiesGoal;

    private int spawnersDestroyed = 0;
    private int spawnersCreated;

    private int floorSwitchesDepressed = 0;
    private int floorSwitchesCreated;

    private int treasureCollected = 0;
    private int treasureGoal;

    public Statistics(ArrayList<String> listOfGoals, List<Entity> listOfEntities, HashMap<String, String> configMap) {
        this.listOfGoals = listOfGoals;
        this.spawnersCreated = listOfEntities.stream().filter(e -> e.getEntityType().equals("zombie_toast_spawner")).collect(Collectors.toList()).size();

        this.enemiesGoal = Integer.parseInt(configMap.get("enemy_goal"));
        this.treasureGoal = Integer.parseInt(configMap.get("treasure_goal"));
    }

    public ArrayList<String> getListOfGoals() {
        return this.listOfGoals;
    }

    public void addEnemyDestroyed() {
        this.enemiesDestroyed += 1;
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
        if (!listOfGoals.contains(goal)) {
            listOfGoals.add(goal);
        }
    }

    private void removeGoal(String goal) {
        if (listOfGoals.contains(goal)) {
            listOfGoals.remove(goal);
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


    // Setters for (set) stats variables.
    // Goal checking.







}

