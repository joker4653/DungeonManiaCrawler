package dungeonmania.Battling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import dungeonmania.Battling.PlayerBattleStrategy.BowBattlingStrategy;
import dungeonmania.Battling.PlayerBattleStrategy.NoWeaponBattlingStrategy;
import dungeonmania.Battling.PlayerBattleStrategy.SwordBattlingStrategy;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.Entities.Collectables.Sword;
import dungeonmania.Entities.Moving.MovingEntity;
import dungeonmania.Entities.Moving.Player;


public class Battle implements Serializable {
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
    public boolean doBattle(HashMap<String, String> configMap, Inventory inventory) {
        ArrayList<Entity> weaponryUsed = getWeaponry(inventory);
        boolean result = doRound(configMap, weaponryUsed, inventory); 

        return result;
    }

    /*
     * @returns true if player alive after round, false otherwise.
     */
    public boolean doRound(HashMap<String, String> configMap, ArrayList<Entity> weaponryUsed, Inventory inventory) {
        double player_attack = getPlayerAttack(configMap, weaponryUsed, inventory);
        double player_defence = getPlayerDefence(configMap, inventory);
        double enemy_attack = enemy.enemyAttackModifier();

        double delta_player_health = - (enemy_attack - player_defence) / 10;    
        double delta_enemy_health = - enemy.calculateDeltaEnemyHealth(player_attack);  
        double player_hp = player.getPlayerHealth() + delta_player_health;
        double enemy_hp = enemy.getEnemyHealth() + delta_enemy_health;
        player.setPlayerHealth(player_hp);
        enemy.setEnemyHealth(enemy_hp);


        Round round = new Round(delta_player_health, delta_enemy_health, weaponryUsed);
        rounds.add(round);

        // Check if battle is over.
        if (player_hp <= 0) {
            return false;
        } else if (enemy_hp <= 0) {
            return true;
        } else {
            return doRound(configMap, weaponryUsed, inventory);
        }
    }

    private ArrayList<Entity> getWeaponry(Inventory inventory) {
        ArrayList<Entity> weaponryUsed = new ArrayList<Entity>();

        Entity item = inventory.getItem("sword");
        if (item != null) {
            weaponryUsed.add(item);
            Sword sword = (Sword) item;
            sword.reduceDurability();
            if (sword.isDestroyed()) {
                inventory.removeItem(sword);
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

    private double getPlayerAttack(HashMap<String, String> configMap, ArrayList<Entity> weaponryUsed, Inventory inventory) {
        boolean swordExists = itemExists(weaponryUsed, "sword");
        boolean bowExists = itemExists(weaponryUsed, "bow");
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

    private double getPlayerDefence(HashMap<String, String> configMap, Inventory inventory) {
        double def = 0;

        boolean shieldExists = inventory.itemExists("shield");
        double shield_defence = Double.parseDouble(configMap.get("shield_defence"));
        if (shieldExists) {
            def += shield_defence;
        }

        int allies = player.getAllies();
        double ally_defense = Double.parseDouble(configMap.get("ally_defence"));
        for (int i = 0; i < allies; i++) {
            def += ally_defense;
        }

        return def;
    }

    private boolean itemExists(ArrayList<Entity> entities, String type) {
        for (Entity entity : entities) {
            if (entity.getEntityType().equalsIgnoreCase(type)) {
                return true;
            }
        }

        return false;
    }

}
