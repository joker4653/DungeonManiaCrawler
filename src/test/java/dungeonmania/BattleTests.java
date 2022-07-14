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

    @Test
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
    }

    private static DungeonResponse genericPlayerBattle(DungeonManiaController controller, String configFile) {
        //
        //  exit   boulder      
        //  door   zombie_toast_spawner        wall    
        //  player  [  ]          
        //
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicZombie", configFile);
        int zombieCount = countEntityOfType(initialResponse, "zombie_toast");
       
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, zombieCount);
        return controller.tick(Direction.RIGHT);
    }

    @Test
    @DisplayName("Test general player battle scenario - player dies.")
    public void testPlayerBattleDies() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericPlayerSequence(controller, "d_battleTest_basicSpider", "c_battleTests_basicPlayerBattle");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie_toast", battle, true, "c_battleTests_basicPlayerBattle");
    }



    /*
     * Check battle calculations for sword/bow/shield/ally bonuses.
     */
    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath, int atkBonus, int defBonus) {
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

    private static DungeonResponse genericPlayerSequence(DungeonManiaController controller, String dungeon, String configFile) {
        //
        //  exit   boulder      
        //  door   zombie_toast_spawner        wall    
        //  player  sword          
        //
        DungeonResponse initialResponse = controller.newGame(dungeon, configFile);
        int zombieCount = countEntityOfType(initialResponse, "zombie_toast");
       
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, zombieCount);
        return controller.tick(Direction.RIGHT);
    }


    @Test
    @DisplayName("Test player attack with sword bonus.") 
    public void testPlayerAttackSword() {
        // Test player attack w/ sword.
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericPlayerSequence(controller, "d_battleTest_playerSword", "c_battleTests_basicPlayerBattle");

        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", "c_battleTests_basicPlayerBattle"));
        double swordBonus = Double.parseDouble(getValueFromConfigFile("sword_attack", "c_battleTests_basicPlayerBattle"));
        playerAttack += swordBonus;

        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<RoundResponse> rounds = battle.getRounds();

        RoundResponse round1 = rounds.get(0);

        assertEquals(round1.getDeltaEnemyHealth(), playerAttack / 5);
    }

    /* // TODO Finish these tests once appropriate entities have been made!!!!!!
    @Test
    @DisplayName("Test player attack with bow bonus.") {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericEnemySequence(controller, "c_battleTests_basicPlayerBattle");

        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        playerAttack *= 2;

        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<RoundResponse> rounds = battle.getRounds();

        Round round1 = rounds.get(0);

        assertEquals(round.getDeltaEnemyHealth(), playerAttack / 5);
    }

    @Test
    @DisplayName("Test player attack with sword and bow bonus.") {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericEnemySequence(controller, "c_battleTests_basicPlayerBattle");
        
        new Sword(x, y, Integer.parseInt(configMap.get("sword_durability")), Integer.parseInt(configMap.get("sword_attack"))

        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double swordBonus = Double.parseDouble(getValueFromConfigFile("sword_attack", "c_battleTests_basicPlayerBattle"));
        playerAttack += swordBonus;
        playerAttack *= 2;

        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<RoundResponse> rounds = battle.getRounds();

        Round round1 = rounds.get(0);

        assertEquals(round.getDeltaEnemyHealth(), playerAttack / 5);
    }

    @Test
    @DisplayName("Test player defense with shield bonus.") {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericEnemySequence(controller, "c_battleTests_basicPlayerBattle");

        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        playerAttack += swordBonus;
        playerAttack *= 2;

        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<RoundResponse> rounds = battle.getRounds();

        Round round1 = rounds.get(0);

        assertEquals(round.getDeltaEnemyHealth(), playerAttack / 5);
         
        // Test player attack w/ sword.
    }

    // TODO Write this test when ally/mercenary stuff has been cleared up!
    @Test
    @DisplayName("Test player attack & defence with ally bonuses.") {
        // Test player attack w/ ally
    }
    */
}
