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

    private static DungeonResponse genericSpiderSequence(DungeonManiaController controller, String configFile) {
        //
        //  exit   wall      wall    wall
        //         player    [  ]    boulder
        //  wall   wall     spider   wall
        //
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicSpider", configFile);
        int spiderCount = countEntityOfType(initialResponse, "spider");
       
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, spiderCount);
        return controller.tick(Direction.RIGHT);
    }

    @Test
    @DisplayName("Testing: spider loses the battle against player")
    public void testSpiderLosesBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericSpiderSequence(controller, "c_battleTests_basicSpiderSpiderDies");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, false, "c_battleTests_basicSpiderSpiderDies");
    }


    private static DungeonResponse genericZombieSequence(DungeonManiaController controller, String configFile) {
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
    @DisplayName("Testing: zombie loses the battle against player")
    public void testZombieLosesBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicZombieZombieDies");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie_toast", battle, false, "c_battleTests_basicZombieZombieDies");
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
        DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicPlayerBattle");
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
        DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicPlayerBattle");

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
        DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicPlayerBattle");
        
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
        DungeonResponse postBattleResponse = genericZombieSequence(controller, "c_battleTests_basicPlayerBattle");

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
