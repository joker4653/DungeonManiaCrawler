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
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.Battling.EnemyBattleStrategy.EnemyBattlingStrategy;
import dungeonmania.Battling.EnemyBattleStrategy.HydraBattlingStrategy;
import dungeonmania.Entities.Moving.Hydra;
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
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -enemyAttack / 10);
            assertEquals(round.getDeltaEnemyHealth(), -playerAttack / 5);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    private static DungeonResponse genericEnemySequence(DungeonResponse res, DungeonManiaController controller, String entityType) {
       
        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, entityType));
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
        DungeonResponse res = controller.newGame("d_battleTest_basicZombie", "c_battleTests_basicZombieZombieDies");

        res = controller.tick(Direction.UP);

        DungeonResponse postBattleResponse = genericEnemySequence(res, controller, "zombie_toast");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie", battle, true, "c_battleTests_basicZombieZombieDies");
    } 

/*  TODO UNCOMMENT THIS WHEN DOOR/KEY HAVE BEEN IMPLEMENTED
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
        assertBattleCalculations("zombie", battle, false, "c_battleTests_basicZombieZombieWins");
    }
*/

    private static DungeonResponse genericPlayerSequence(DungeonManiaController dmc, String dungeon, String configFile) {
        //
        //  exit   boulder      
        //  door   zombie_toast_spawner        wall    
        //  player  sword          
        //
        DungeonResponse res = dmc.newGame(dungeon, configFile);

        res = dmc.tick(Direction.RIGHT);

        return res;
    }

    @Test
    @DisplayName("Test general player battle scenario - player dies.")
    public void testPlayerBattleDies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericPlayerSequence(dmc, "d_battleTest_basicZombie", "c_battleTests_basicZombieZombieWins");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("zombie", battle, false, "c_battleTests_basicZombieZombieWins");
    }



    /*
     * Check battle calculations for sword/bow/shield/ally bonuses.
     */
    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath, int atkBonus, int defBonus) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -enemyAttack / 10);  
            assertEquals(round.getDeltaEnemyHealth(), -playerAttack / 5);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }



    @Test
    @DisplayName("Test player attack with sword bonus.") 
    public void testPlayerAttackSword() {
        // Test player attack w/ sword.
        //  exit    boulder     wall
        //  door    spawner     wall
        //  player  sword       wall
        //          wall
        //
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericPlayerSequence(controller, "d_battleTest_playerSword", "c_battleTests_basicPlayerBattle");

        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", "c_battleTests_basicPlayerBattle"));
        double swordBonus = Double.parseDouble(getValueFromConfigFile("sword_attack", "c_battleTests_basicPlayerBattle"));
        playerAttack += swordBonus;

        BattleResponse battle = postBattleResponse.getBattles().get(0);

        List<RoundResponse> rounds = battle.getRounds();

        RoundResponse round1 = rounds.get(0);

        assertEquals(-playerAttack / 5, round1.getDeltaEnemyHealth());
    }

    /* // TODO Finish these tests once appropriate entities have been made
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

    */

    @Test
    @DisplayName("Test player attack & defence with ally bonuses - mercenary.")
    public void TestPlayerBattleAllyMerc() {
        TestPlayerBattleAlly("d_battleTest_allyBackup", "c_battleTest_allyBackup", "mercenary");
    }

    public void TestPlayerBattleAlly(String dungeonFilePath, String configFilePath, String type) {
        // Test player attack w/ ally
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(dungeonFilePath, configFilePath);

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        EntityResponse merc = getEntities(res, type).get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(merc.getId());
        });

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT); // tick 6

        assertEquals(1, countEntityOfType(res, "zombie_toast"));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        DungeonResponse postBattleResponse = res;

        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double allyAtkBonus = Double.parseDouble(getValueFromConfigFile("ally_attack", configFilePath));
        playerAttack += allyAtkBonus;

        double enemyAttack = Double.parseDouble(getValueFromConfigFile("zombie_attack", configFilePath));
        double allyDefBonus = Double.parseDouble(getValueFromConfigFile("ally_defence", configFilePath));
        enemyAttack -= allyDefBonus;

        BattleResponse battle = postBattleResponse.getBattles().get(0);

        List<RoundResponse> rounds = battle.getRounds();

        RoundResponse round1 = rounds.get(0);

        assertEquals(round1.getDeltaCharacterHealth(), -enemyAttack / 10);
        assertEquals(round1.getDeltaEnemyHealth(), -playerAttack / 5);
        

    }

    /* System level test.
    Tests the following:
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

        res = testPlayerPushBoulder(dmc, res);

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // player exits
        assertEquals(new Position(6, 8), getPlayer(res).get().getPosition());
        String goals = getGoals(res); 
        assertFalse(goals.contains(":exit")); // completed goal has been removed
    }    

    private DungeonResponse testMercenary(DungeonManiaController dmc, DungeonResponse res) {
        EntityResponse merc = getEntities(res, "mercenary").get(0);

        assertDoesNotThrow(() -> {
            dmc.interact(merc.getId());
        });

        res = dmc.tick(Direction.DOWN);
        
        Position playerPrevPos = getPlayer(res).get().getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getEntities(res, "mercenary").get(0).getPosition().equals(playerPrevPos));
        res = dmc.tick(Direction.DOWN);
        return res;
    }

    private DungeonResponse testPlayerPushBoulder(DungeonManiaController dmc, DungeonResponse res) {
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

    // Hydra battle tests

    @Test
    @DisplayName("Test hydra's health never increases when hydra_health_increase_rate = 0. Hydra loses.")
    public void testHydraHpNeverIncreases() {
        //  exit   wall      wall                   wall
        //         player    hydra                  boulder
        //  wall   wall      zombie_toast_spawner   wall
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicHydra", "c_battleTest_hydraNeverIncrease");
        DungeonResponse postBattleResponse = genericEnemySequence(initialResponse, controller, "hydra");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("hydra", battle, true, "c_battleTest_hydraNeverIncrease");
    }

    @Test
    @DisplayName("Test hydra's health always increases when hydra_health_increase_rate = 1. Hydra wins.")
    public void testHydraHpAlwaysIncreases() {
        //  exit   wall      wall                   wall
        //         player    hydra                  boulder
        //  wall   wall      zombie_toast_spawner   wall
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicHydra", "c_battleTest_hydraAlwaysIncrease");
        DungeonResponse postBattleResponse = genericEnemySequence(initialResponse, controller, "hydra");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertHydraIncreasesHealthAlways("hydra", battle, false, "c_battleTest_hydraAlwaysIncrease");
    }

    private void assertHydraIncreasesHealthAlways(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -enemyAttack / 10);
            assertEquals(round.getDeltaEnemyHealth(), Double.parseDouble(getValueFromConfigFile("hydra_health_increase_amount", configFilePath)));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    // Test hydra’s health when hydra_health_increase_rate = 0.72142, hydra_health = 100, hydra_attack = 1, hydra_health_increase_amount = 100, player_health = 5 and player_attack = 1, resulting in the hydra winning.
    @Test
    @DisplayName("Test hydra's health increases when hydra_health_increase_rate = 0.72142. Hydra wins.")
    public void testHydraWins() {
        //  exit   wall      wall                   wall
        //         player    hydra                  boulder
        //  wall   wall      zombie_toast_spawner   wall
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicHydra", "c_battleTest_hydraWins");
        DungeonResponse postBattleResponse = genericEnemySequence(initialResponse, controller, "hydra");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        String hydraID = getEntities(initialResponse, "hydra").get(0).getId();
        Hydra hydra = (Hydra)controller.getListOfEntities().stream()
                                                           .filter(e -> e.getEntityID().equals(hydraID))
                                                           .findFirst()
                                                           .get();
        EnemyBattlingStrategy battleStrategy = hydra.getEnemyStrategy();
        long seed = ((HydraBattlingStrategy)(battleStrategy)).getSeed();
        Random random = new Random(seed);

        assertHydraIncreasesHealth(random, "hydra", battle, false, "c_battleTest_hydraWins");
    }

    private void assertHydraIncreasesHealth(Random random, String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
        double hydra_health_increase_rate = Double.parseDouble(getValueFromConfigFile("hydra_health_increase_rate", configFilePath));

        double enemyPrevHealth = enemyHealth;
       
        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -enemyAttack / 10);
            if (random.nextDouble() <= hydra_health_increase_rate)
                assertEquals(round.getDeltaEnemyHealth(), Double.parseDouble(getValueFromConfigFile("hydra_health_increase_amount", configFilePath)));
            else
                assertEquals(round.getDeltaEnemyHealth(), -playerAttack / 5);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }
        assertTrue(enemyHealth > enemyPrevHealth);
        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    // Test hydra’s health never changes when hydra_health_increase_amount = 0, and hydra_health_increase_rate is anything. Here, the hydra loses against the player.
    @Test
    @DisplayName("Test hydra's health never changes when hydra_health_increase_amount = 0. The hydra loses against the player.")
    public void testHydraLost() {
        //  exit   wall      wall                   wall
        //         player    hydra                  boulder
        //  wall   wall      zombie_toast_spawner   wall
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicHydra", "c_battleTest_hydraLoses");
        DungeonResponse postBattleResponse = genericEnemySequence(initialResponse, controller, "hydra");
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertHydraIncreasesHealth0("hydra", battle, true, "c_battleTest_hydraLoses");
    }
    private void assertHydraIncreasesHealth0(String enemyType, BattleResponse battle, boolean enemyDies, String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
       
        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -enemyAttack / 10);
            assertTrue(round.getDeltaEnemyHealth() <= 0);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }
     
        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

    // Assassin battle tests

    // Helper function taken and modified from ExampleTests.java. All credit goes to the COMP2511 team who wrote ExampleTests.java.
    private static DungeonResponse genericAssassinSequence(DungeonManiaController controller, String configFile) {
        //
        //  exit   wall  wall       wall
        // player  [  ]  assassin   wall
        //  wall   wall  wall       wall
        //
        DungeonResponse initialResponse = controller.newGame("d_battleTest_basicAssassin", configFile);
        int assassinCount = countEntityOfType(initialResponse, "assassin");
        
        assertEquals(1, countEntityOfType(initialResponse, "player"));
        assertEquals(1, assassinCount);
        return controller.tick(Direction.RIGHT);
    }

    @Test
    @DisplayName("Test basic battle calculations - assassin - player loses")
    public void testHealthBelowZeroAssassin() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericAssassinSequence(controller, "c_battleTests_basicAssassinPlayerDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("assassin", battle, false, "c_battleTests_basicAssassinPlayerDies");
    }

    @Test
    @DisplayName("Test basic battle calculations - assassin - player wins")
    public void testRoundCalculationsAssassin() {
       DungeonManiaController controller = new DungeonManiaController();
       DungeonResponse postBattleResponse = genericAssassinSequence(controller, "c_battleTests_basicAssassinAssassinDies");
       BattleResponse battle = postBattleResponse.getBattles().get(0);
       assertBattleCalculations("assassin", battle, true, "c_battleTests_basicAssassinAssassinDies");
    }

    @Test
    @DisplayName("Test player attack & defence with ally bonuses - assassin.")
    public void TestPlayerBattleAllyAssassin() {
        TestPlayerBattleAlly("d_battleTest_allyAssassinBackup", "c_battleTest_allyAssassinBackup", "assassin");
    }

}
