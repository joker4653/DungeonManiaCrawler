package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;

public class StandardBattlingStrategy extends EnemyBattlingStrategy {
    public StandardBattlingStrategy(HashMap<String, String> configMap, String type) {
        super.setDamage(Double.parseDouble(configMap.get(type + "_attack")));
        super.setHealth(Double.parseDouble(configMap.get(type + "_health")));
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
