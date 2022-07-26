package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;
import java.util.Random;

public class HydraBattlingStrategy extends EnemyBattlingStrategy {
    private double hydraRate;
    private double hydraAmt;
    private long seed;
    private Random random;

    public HydraBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(configMap.get("hydra_attack") != null ? Double.parseDouble(configMap.get("hydra_attack")) : 0);
        super.setHealth(configMap.get("hydra_health") != null ? Double.parseDouble(configMap.get("hydra_health")) : 0);
        this.hydraRate = configMap.get("hydra_health_increase_rate") != null ? Double.parseDouble(configMap.get("hydra_health_increase_rate")) : 0;
        this.hydraAmt = configMap.get("hydra_health_increase_amount") != null ? Double.parseDouble(configMap.get("hydra_health_increase_amount")) : 0;
        this.seed = System.currentTimeMillis();
        this.random = new Random(seed);
    }

    @Override
    public double calculateDeltaEnemyHealth(double playerDmg) {
        if (random.nextDouble() <= hydraRate) {
            // health increases rather than decreases
            return -hydraAmt;
        }

        return playerDmg / 5;
    }

    @Override
    public double attackModifier() {
        return super.getDamage();
    }

    public long getSeed() {
        return seed;
    }
}
