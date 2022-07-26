package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;

public class StandardBattlingStrategy extends EnemyBattlingStrategy {
    public StandardBattlingStrategy(HashMap<String, String> configMap, String type) {
        super.setDamage(configMap.get(type + "_attack") != null ? Double.parseDouble(configMap.get(type + "_attack")) : 0);
        super.setHealth(configMap.get(type + "_health") != null ? Double.parseDouble(configMap.get(type + "_health")) : 0);
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
