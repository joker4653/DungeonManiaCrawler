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

        List<Position> possibleZombiePos = Arrays.asList(left, right, up, below);

        return possibleZombiePos;
    }

    // Zombie toast spawn tests:
    
    @Test
    @DisplayName("Test zombies can only spawn on cardinally adjacent open squares")
    public void testZombiesSpawnSuccess() {
        /* Test zombies spawn on a cardinally adjacent (up, down, left, right) “open square” (i.e. no wall or boulder).
        Also ensure no zombies spawn on top of the spawner (assert that the no. of zombies on the spawner = 0) */

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_spawnSuccess", "c_zombieTest_spawnSuccess");

        Position spawnerPos = getEntities(res, "zombie_toast_spawner").get(0).getPosition();
        List<Position> possibleSpawnPos = getSpawnLocations(res, spawnerPos);

        for (int i = 0; i < 30; i++) {
            res = dmc.tick(Direction.DOWN);
            Position currZombiePos = getEntities(res, "zombie_toast").get(i).getPosition();
            assertTrue(possibleSpawnPos.contains(currZombiePos));
            assertTrue(currZombiePos != spawnerPos); // zombies can spawn on top of their spawners
        }
    }

    @Test
    @DisplayName("Test zombies can't ??? CHECK FORUMMMMMMMM<NDN<SF spawn on walls, boulders and locked doors")
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

    @Test
    @DisplayName("Test correct no. of zombies spawn when zombie_spawn_rate = 1")
    public void testZombiesSpawnEveryTick() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_spawnEveryTick", "c_zombieTest_spawnEveryTick");
        
        for (int i = 0; i < 10; i++) {
            res = dmc.tick(Direction.DOWN);
        }

        assertEquals(getEntities(res, "zombie_toast").size(), 10);
    }

    @Test
    @DisplayName("Test correct no. of zombies spawn when zombie_spawn_rate = 10")
    public void testZombiesSpawnEvery10Ticks() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_spawnEveryTick", "c_spiderTest_spawnEvery10Ticks");
        
        for (int i = 0; i < 50; i++) {
            res = dmc.tick(Direction.DOWN);
        }

        assertEquals(getEntities(res, "zombie_toast").size(), 5);
    }

    @Test
    @DisplayName("Test more zombies can't be spawned without a zombie spawner")
    public void testNoNewZombiesWithoutSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_cantSpawnWithNoSpawner", "c_zombieTest_spawnEveryTick");

        for (int i = 0; i < 50; i++) {
            res = dmc.tick(Direction.UP);
        }

        assertEquals(getEntities(res, "zombie_toast").size(), 1);
    }

    @Test
    @DisplayName("Test multiple zombies can spawn from many different spawners")
    public void testMultipleZombieSpawners() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_multiSpawners", "c_zombieTest_spawnEveryTick");
        int currZombCount = 2;

        assertEquals(getEntities(res, "zombie_toast").size(), currZombCount);

        for (int i = 0; i < 10; i++) {
            res = dmc.tick(Direction.UP);
            // since there are 3 zombie toast spawners and the spawn_rate is 1, 3 new zombies should be created per tick.
            currZombCount += 3;
        }

        assertEquals(getEntities(res, "zombie_toast").size(), currZombCount);
    }

    // Zombie movement tests:

    @Test
    @DisplayName("Test zombies cannot move through walls, boulders and locked doors")
    public void testZombieMoveRestrictions() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_moveRestrictions", "c_zombieTest_spawnEveryTick");

        res = dmc.tick(Direction.DOWN);
        Position z1Pos1 = getEntities(res, "zombie_toast").get(0).getPosition();
        assertTrue(z1Pos1.equals(new Position(2, 2)));

        res = dmc.tick(Direction.DOWN);
        Position z1Pos2 = getEntities(res, "zombie_toast").get(0).getPosition();
        assertTrue(z1Pos2.equals(new Position(3, 2)));

        Position z2Pos1 = getEntities(res, "zombie_toast").get(1).getPosition();
        assertTrue(z2Pos1.equals(new Position(2, 2)));

        res = dmc.tick(Direction.DOWN);
        Position z2Pos2 = getEntities(res, "zombie_toast").get(1).getPosition();
        assertTrue(z2Pos2.equals(new Position(3, 2)));

        Position z3Pos1 = getEntities(res, "zombie_toast").get(2).getPosition();
        assertTrue(z3Pos1.equals(new Position(2, 2)));

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
