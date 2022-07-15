package dungeonmania;

public class Battle {
    private String enemyType;
    private double initPlayerHealth;
    private double initEnemyHealth;

    public Battle(Player player, Entity currEntity) {
        this.enemyType = currEntity.getEntityType();
        this.initPlayerHealth = player.getPlayerHealth();
        this.initEnemyHealth = ((MovingEntity)currEntity).getEnemyHealth();
    }


    /* Getters & Setters */

    public String getEnemyType() {
        return enemyType;
    }

    public double getInitPlayerHealth() {
        return initPlayerHealth;
    }

    public double getInitEnemyHealth() {
        return initEnemyHealth;
    }

}
