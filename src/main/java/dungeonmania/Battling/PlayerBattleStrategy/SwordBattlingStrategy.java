package dungeonmania.Battling.PlayerBattleStrategy;

import java.util.HashMap;

public class SwordBattlingStrategy extends PlayerBattlingStrategy {

    public SwordBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("sword_attack")));
    }

    public double attackModifier() {
        return super.getDamage();
    }
}


