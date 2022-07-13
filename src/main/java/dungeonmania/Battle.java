package dungeonmania;

import java.util.ArrayList;

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

    /*
     * @returns true if player alive after battle, false otherwise.
     */
    public boolean doBattle() {
        doRound();        
        // Do BATTLE!!!!

        return true;
    }

    public void doRound() {
        double player_attack = getPlayerAttack(player);
        double enemy_attack = enemy.enemyAttackModifier();

        double enemy_hp = enemy.
    }


    private double getPlayerAttack(Player player) {
        boolean swordExists = player.itemExists("sword");
        boolean bowExists = player.itemExists("bow");
        boolean shieldExists = player.itemExists("shield");
        int allies = getAllies();
    }

    private int getAllies()

}
