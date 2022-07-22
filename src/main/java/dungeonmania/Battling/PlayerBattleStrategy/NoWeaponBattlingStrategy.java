package dungeonmania.Battling.PlayerBattleStrategy;

import java.util.HashMap;

public class NoWeaponBattlingStrategy extends PlayerBattlingStrategy {

    public NoWeaponBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(Double.parseDouble(configMap.get("player_attack")));
    }

    public double attackModifier() {
        return super.getDamage();
    }
}

