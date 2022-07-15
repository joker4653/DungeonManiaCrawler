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
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTests {
    
    // Helper function taken from ExampleTests.java. All credit goes to the COMP2511 team who wrote ExampleTests.java.
    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), enemyAttack / 10);
            assertEquals(round.getDeltaEnemyHealth(), playerAttack / 5);
            enemyHealth -= round.getDeltaEnemyHealth();
            playerHealth -= round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    private static DungeonResponse genericEnemySequence(DungeonResponse initialResponse, DungeonManiaController controller, String entityType) {
        int enemyCount = countEntityOfType(initialResponse, entityType);
       
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, enemyCount);
        return controller.tick(Direction.RIGHT);
    }

    /*@Test
    @DisplayName("Testing: spider loses the battle against player")
    public void testSpiderLosesBasic() {
        //  exit   wall      wall    wall
        //         player    [  ]    boulder
        //  wall   wall     spider   wall
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicSpider", "c_battleTests_basicSpiderSpiderDies");
        DungeonResponse postBattleResponse = genericEnemySequence(initialResponse, controller, "spider");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, true, "c_battleTests_basicSpiderSpiderDies");
    }

    @Test
    @DisplayName("Testing: zombie loses the battle against player")
    public void testZombieLosesBasic() {
        //  exit   boulder      
        //  door   zombie_toast_spawner        wall    
        //  player  [  ]          
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicZombie", "c_battleTests_basicZombieZombieDies");
 
        DungeonResponse postBattleResponse = genericEnemySequence(initialResponse, controller, "zombie_toast");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie_toast", battle, true, "c_battleTests_basicZombieZombieDies");
    } 

    @Test
    @DisplayName("Test zombie walk through the open door and wins the battle")
    public void testZombieWalkThroughOpenDoor() {
        //
        //  player   key      door    
        //           wall     zombie_toast_spawner     wall
        //                    wall                     exit
        //
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_throughOpenDoor", "c_battleTests_basicZombieZombieWins");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
      
        DungeonResponse postBattleResponse = genericEnemySequence(res, dmc, "zombie_toast");

        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie_toast", battle, false, "c_battleTests_basicZombieZombieWins");
    }*/ 

    /* System level test.
    The test below involves the following:
    1. player gets 3 coins
    2. player bribes the mercenary
    3. player battles and wins against one zombie
    4. player pushes a boulder
    5. player exits and wins
    */
    @Test
    @DisplayName("Test multiple entities")
    public void systemTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_systemTest_multipleEntities", "c_systemTest_playerWins");

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        res = testMercenary(dmc, res);

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        res = testPlayerPushBoudler(dmc, res);

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // player exits
        assertEquals(new Position(6, 8), getPlayer(res).get().getPosition());
    }    

    private DungeonResponse testMercenary(DungeonManiaController dmc, DungeonResponse res) {
        EntityResponse merc = getEntities(res, "mercenary").get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(merc.getId());
        });

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        Position playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.DOWN);
        assertTrue(getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos));
    
        return res;
    }

    private DungeonResponse testPlayerPushBoudler(DungeonManiaController dmc, DungeonResponse res) {
        Position bPos = new Position(3, 6);
        EntityResponse boulder = getEntities(res, "boulder").stream()
                                                                  .filter(b -> b.getPosition().equals(bPos))
                                                                  .findFirst()
                                                                  .get();
        
        res = dmc.tick(Direction.RIGHT);
        
        Position newPos = getEntities(res, "boulder").stream()
                                                           .filter(b -> b.getId().equals(boulder.getId()))
                                                           .findFirst()
                                                           .get()
                                                           .getPosition();

        assertEquals(new Position(4, 6), newPos);
        return res;
    }

}