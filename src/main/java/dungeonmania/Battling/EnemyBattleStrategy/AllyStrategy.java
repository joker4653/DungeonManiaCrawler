package dungeonmania.Battling.EnemyBattleStrategy;

import java.util.HashMap;

public class AllyStrategy extends EnemyBattlingStrategy {
    private double allyAttack;
    private double allyDefence;

    public AllyStrategy(HashMap<String, String> configMap, String type) {
        super.setDamage(Double.parseDouble(configMap.get(type + "_attack")));
        super.setHealth(Double.parseDouble(configMap.get(type + "_health")));

        this.allyAttack = configMap.get("ally_attack") != null ? Double.parseDouble(configMap.get("ally_attack")) : 0;
        this.allyDefence =  configMap.get("ally_defence") != null ? Double.parseDouble(configMap.get("ally_defence")) : 0;
    }

    public double allyAttackModifier() {
        return allyAttack;
    }

    public double allyDefenceModifier() {
        return allyDefence;
    }

    @Override
    public double attackModifier() {
        return 0;
    }

    @Override
    public double calculateDeltaEnemyHealth(double playerDmg) {
        return getHealth(); // ally's health doesnt decrease        
    }
}
