package dungeonmania.EnemyBattleStrategy;

import java.util.HashMap;

public class MercenaryEnemyStrategy extends EnemyBattlingStrategy {

    public MercenaryEnemyStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("mercenary_attack")));
        super.setHealth(Double.parseDouble(configMap.get("mercenary_health")));
    }

    @Override
    public double calculateEnemyHealth(double playerDmg) {
        super.setHealth(super.getHealth() - (playerDmg) / 5);
        return super.getHealth();
    }

    public double attackModifier() {
        return super.getDamage();
    }
}
