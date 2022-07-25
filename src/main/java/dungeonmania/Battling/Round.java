package dungeonmania.Battling;

import java.io.Serializable;
import java.util.List;

import dungeonmania.Entities.Entity;

public class Round implements Serializable {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<Entity> weaponryUsed;

    public Round(double deltaPlayerHealth, double deltaEnemyHealth, List<Entity> weaponryUsed)
    {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponryUsed = weaponryUsed;
    }

    public double getDeltaCharacterHealth(){
        return deltaPlayerHealth;
    }
    
    public double getDeltaEnemyHealth(){
        return deltaEnemyHealth;
    }

    public List<Entity> getWeaponryUsed() {
        return weaponryUsed;
    }
}
