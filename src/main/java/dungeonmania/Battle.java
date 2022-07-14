package dungeonmania;

import java.util.ArrayList;
import java.util.HashMap;

import dungeonmania.PlayerBattleStrategy.NoWeaponBattlingStrategy;
import dungeonmania.PlayerBattleStrategy.SwordBattlingStrategy;
import dungeonmania.PlayerBattleStrategy.BowBattlingStrategy;


public class Battle {
    private MovingEntity enemy;
    private Player player;
    private double initPlayerHealth;
    private double initEnemyHealth;
    ArrayList<Round> rounds = new ArrayList<Round>();

    public Battle(Player player, Entity entity) {
        this.enemy = (MovingEntity) entity;
        this.player = player;
        this.initPlayerHealth = player.getPlayerHealth();
        this.initEnemyHealth = enemy.getEnemyHealth();
    }

    /* Getters & Setters */

    public String getEnemyType() {
        return enemy.getEntityType();
    }

    public double getInitPlayerHealth() {
        return initPlayerHealth;
    }

    public double getInitEnemyHealth() {
        return initEnemyHealth;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    /*
     * @returns true if player alive after battle, false otherwise.
     */
    public boolean doBattle(HashMap<String, String> configMap) {
        boolean result = doRound(configMap);        

        return result;
    }

    /*
     * @returns true if player alive after round, false otherwise.
     */
    public boolean doRound(HashMap<String, String> configMap) {
        double player_attack = getPlayerAttack(configMap);
        double player_defence = getPlayerDefence(configMap);
        double enemy_attack = enemy.enemyAttackModifier();

        double delta_player_health = (enemy_attack - player_defence) / 10;
        double delta_enemy_health = enemy.calculateEnemyHealth(player_attack);
        double player_hp = player.getPlayerHealth() - delta_player_health;
        double enemy_hp = enemy.getEnemyHealth() - delta_enemy_health;
        player.setPlayerHealth(player_hp);
        enemy.setEnemyHealth(enemy_hp);

        ArrayList<Entity> weaponryUsed= getWeaponry();
        // TODO make weaponryUsed, do durability changes.

        Round round = new Round(delta_player_health, delta_enemy_health, weaponryUsed);
        rounds.add(round);

        // Check if battle is over.
        if (player_hp <= 0) {
            return false;
        } else if (enemy_hp <= 0) {
            return true;
        } else {
            return doRound(configMap);
        }
    }

    private ArrayList<Entity> getWeaponry() {
        ArrayList<Entity> weaponryUsed = new ArrayList<Entity>();

        Entity item = player.getItem("sword");
        if (item != null) {
            weaponryUsed.add(item);
            Sword sword = (Sword) item;
            sword.reduceDurability();
            if (sword.isDestroyed()) {
                player.removeItem(sword);
            }
        }

/* TODO UNCOMMENT WHEN BOW IS IMPLEMENTED!
        Entity bow_item = player.getItem("bow");
        if (bow_item != null) {
            weaponryUsed.add(bow_item);
            Bow bow = (Bow) bow;
            bow.reduceDurability();
            if (bow.isDestroyed()) {
                player.removeItem(bow);
            }
        }
        */

        return weaponryUsed;
    }

    private double getPlayerAttack(HashMap<String, String> configMap) {
        boolean swordExists = player.itemExists("sword");
        boolean bowExists = player.itemExists("bow");
        int allies = player.getAllies();

        double atk = new NoWeaponBattlingStrategy(configMap).attackModifier();

        if (swordExists) {
            atk += new SwordBattlingStrategy(configMap).attackModifier();
        }
        if (bowExists) {
            atk *= new BowBattlingStrategy(configMap).attackModifier();
        }

        double ally_attack = Double.parseDouble(configMap.get("ally_attack"));
        for (int i = 0; i < allies; i++) {
            atk += ally_attack;
        }

        return atk;
    }

    private double getPlayerDefence(HashMap<String, String> configMap) {
        double def = 0;

        boolean shieldExists = player.itemExists("shield");
        double shield_defence = Double.parseDouble(configMap.get("shield_defence"));
        if (shieldExists) {
            def += shield_defence;
        }

        int allies = player.getAllies();
        double ally_defense = Double.parseDouble(configMap.get("ally_attack"));
        for (int i = 0; i < allies; i++) {
            def += ally_defense;
        }

        return def;
    }

}
