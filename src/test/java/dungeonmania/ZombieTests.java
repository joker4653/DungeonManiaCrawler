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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieTests {

    // Helper function that returns a list of zombie spawn locations
    public List<Position> getSpawnLocations(DungeonResponse res, Position spawnerPos) {
        Position left = new Position(spawnerPos.getX() - 1, spawnerPos.getY());
        Position right = new Position(spawnerPos.getX() + 1, spawnerPos.getY());
        Position up = new Position(spawnerPos.getX(), spawnerPos.getY() - 1);
        Position below = new Position(spawnerPos.getX(), spawnerPos.getY() + 1);

        // if zombies can spawn on their spawner, add this to the list
        List<Position> possibleZombiePos = Arrays.asList(left, right, up, below);

        return possibleZombiePos;
    }

    // Zombie toast spawn tests:
    
    @Test
    @DisplayName("Test zombies can only spawn on cardinally adjacent open squares")
    public void testZombiesSpawnSuccess() {
        /* Test zombies spawn on a cardinally adjacent (current location, up, down, left, right) “open square” (i.e. no wall or boulder).
        ??? Also ensure no zombies spawn on top of the spawner (assert that the no. of zombies on the spawner = 0) */
        // if zombies can spawn on their spawner, add this to the list

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToast_spawnSuccess", "c_zombieToast_spawnSuccess");

        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();
        List<Position> possibleSpawnPos = getSpawnLocations(res, spawnerPos);

        for (int i = 0; i < 30; i++) {
            res = dmc.tick(Direction.DOWN);
            Position currZombiePos = getEntities(res, "zombie_toast").get(i).getPosition();
            assertTrue(possibleSpawnPos.contains(currZombiePos));
        }
    }

    @Test
    @DisplayName("Test zombies can't ? CHECK FORUJMDMG<NDN<SF spawn on walls, boulders and locked doors")
    public void testZombiesCantSpawn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_spawnBlocked", "c_zombieTest_spawnBlocked");
        
        for (int i = 0; i < 30; i++) {
            res = dmc.tick(Direction.DOWN);
        }

        assertEquals(getEntities(res, "zombie_toast").size(), 0);
    }

    @Test
    @DisplayName("Test zombies spawn when zombie_spawn_rate = 0")
    public void testZombiesCantSpawnWhenRateIs0() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_spawnSuccess", "c_spiderTest_spawnEveryTick");
        
        for (int i = 0; i < 30; i++) {
            res = dmc.tick(Direction.DOWN);
        }

        assertEquals(getEntities(res, "zombie_toast").size(), 0);
    }

    //TODO
    @Test
    @DisplayName("Test zombies spawn when zombie_spawn_rate = 1")
    public void testZombiesSpawnEveryTick() {
        
    }

    //TODO
    @Test
    @DisplayName("Test zombies spawn when zombie_spawn_rate = 10")
    public void testZombiesSpawnEvery10Ticks() {
        
    }

    //TODO
    @Test
    @DisplayName("Test zombies can't be spawned without a zombie spawner")
    public void testNoNewZombiesWithoutSpawner() {
        
    }

    //TODO
    @Test
    @DisplayName("Test multiple zombies can spawn from many different spawners")
    public void testMultipleZombieSpawners() {
        // Test zombies can exist already in dungeon map
    }

    // Zombie movement tests:

    //TODO
    @Test
    @DisplayName("Test zombies cannot move through walls, boulders and locked doors")
    public void testZombieMoveRestrictions() {

    }

    /* // TODO: WAIT UNTIL DOORS & KEYS HAVE BEEN IMPLEMENTED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Test
    @DisplayName("Test zombies can move through open doors")
    public void testZombieMoveThruOpenDoor() {

    }
    */

    @Test
    @DisplayName("Test zombies can only move up, down, right, left or stay where they are")
    public void testZombieCardinalMovements() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieToast_spawnSuccess", "c_zombieToast_spawnEveryTick");

        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();
        List<Position> possibleSpawnPos = getSpawnLocations(res, spawnerPos);

        res = dmc.tick(Direction.DOWN);
        Position z1Pos1 = getEntities(res, "zombie").get(0).getPosition();
        assertTrue(possibleSpawnPos.contains(z1Pos1));

        res = dmc.tick(Direction.DOWN);
        Position z1Pos2 = getEntities(res, "zombie").get(0).getPosition();
        Position z2Pos1 = getEntities(res, "zombie").get(1).getPosition();
        assertTrue(possibleSpawnPos.contains(z2Pos1));
        assertTrue(Position.isAdjacent(z1Pos1, z1Pos2) || z1Pos1 == z1Pos2);

        res = dmc.tick(Direction.DOWN);
        Position z1Pos3 = getEntities(res, "zombie").get(0).getPosition();
        Position z2Pos2 = getEntities(res, "zombie").get(1).getPosition();
        Position z3Pos1 = getEntities(res, "zombie").get(2).getPosition();
        assertTrue(possibleSpawnPos.contains(z3Pos1));
        assertTrue(Position.isAdjacent(z1Pos2, z1Pos3) || z1Pos2 == z1Pos3);
        assertTrue(Position.isAdjacent(z2Pos1, z2Pos2) || z2Pos1 == z2Pos2);
    }


}
