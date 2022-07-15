package dungeonmania.PlayerBattleStrategy;

public abstract class PlayerBattlingStrategy {
    private double damage;

    public abstract double attackModifier();

    /* Getters & Setters */

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
