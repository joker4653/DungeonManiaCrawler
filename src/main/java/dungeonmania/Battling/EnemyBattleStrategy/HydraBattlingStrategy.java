package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;
import java.util.Random;

public class HydraBattlingStrategy extends EnemyBattlingStrategy {
    double hydraRate;
    double hydraAmt;

    public HydraBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("hydra_attack")));
        super.setHealth(Double.parseDouble(configMap.get("hydra_health")));
        this.hydraRate = Double.parseDouble(configMap.get("hydra_health_increase_rate"));
        this.hydraAmt = Double.parseDouble(configMap.get("hydra_health_increase_amount"));
    }

    @Override
    public double calculateDeltaEnemyHealth(double playerDmg) {
        if (new Random().nextDouble() <= hydraRate) {
            // health increases rather than decreases
            return -hydraAmt;
        }

        return playerDmg / 5;
    }

    @Override
    public double attackModifier() {
        return super.getDamage();
    }
}
