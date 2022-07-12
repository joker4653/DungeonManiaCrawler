package dungeonmania.EnemyBattleStrategy;

import java.util.HashMap;

public class ZombieBattlingStrategy extends EnemyBattlingStrategy {
    
    public ZombieBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("zombie_attack")));
        super.setHealth(Double.parseDouble(configMap.get("zombie_health")));    
    }


    @Override
    public double calculateEnemyHealth(double playerDmg) {
        super.setHealth(super.getHealth() - (playerDmg) / 5);
        return super.getHealth();
    }


    @Override
    public double attackModifier() {
        return super.getDamage();
    }
    
}
