package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;

public class MercenaryEnemyStrategy extends EnemyBattlingStrategy {

    public MercenaryEnemyStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("mercenary_attack")));
        super.setHealth(Double.parseDouble(configMap.get("mercenary_health")));
    }

    @Override
    public double calculateDeltaEnemyHealth(double playerDmg) {
        return playerDmg / 5;
    }

    public double attackModifier() {
        return super.getDamage();
    }
}
