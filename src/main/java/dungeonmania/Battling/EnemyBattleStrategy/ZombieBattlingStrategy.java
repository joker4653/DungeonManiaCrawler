package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;

public class ZombieBattlingStrategy extends EnemyBattlingStrategy {
    
    public ZombieBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("zombie_attack")));
        super.setHealth(Double.parseDouble(configMap.get("zombie_health")));    
    }


    @Override
    public double calculateDeltaEnemyHealth(double playerDmg) {
        return playerDmg / 5;
    }


    @Override
    public double attackModifier() {
        return super.getDamage();
    }
    
}
