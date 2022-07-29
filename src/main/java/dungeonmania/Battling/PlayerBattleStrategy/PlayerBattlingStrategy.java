package dungeonmania.Battling.PlayerBattleStrategy;

import java.io.Serializable;

public abstract class PlayerBattlingStrategy implements Serializable {
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
