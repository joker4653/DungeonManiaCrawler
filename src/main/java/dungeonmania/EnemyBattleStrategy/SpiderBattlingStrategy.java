package dungeonmania.EnemyBattleStrategy;

import java.util.HashMap;

public class SpiderBattlingStrategy extends EnemyBattlingStrategy {
    
    public SpiderBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("spider_attack")));
        super.setHealth(Double.parseDouble(configMap.get("spider_health")));
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
