package dungeonmania.Battling;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;



public class Round implements Serializable {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<HashMap<String, String>> weaponryUsed;

    public Round(double deltaPlayerHealth, double deltaEnemyHealth, List<HashMap<String, String>> weaponryUsed)
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

    public List<HashMap<String, String>> getWeaponryUsed() {
        return weaponryUsed;
    }
}
