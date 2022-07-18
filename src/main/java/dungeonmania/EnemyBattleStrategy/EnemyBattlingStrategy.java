package dungeonmania.EnemyBattleStrategy;

public abstract class EnemyBattlingStrategy {
    private double damage;
    private double health;

    public abstract double attackModifier();
    public abstract double calculateDeltaEnemyHealth(double playerDmg);

    /* Getters & Setters */

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
