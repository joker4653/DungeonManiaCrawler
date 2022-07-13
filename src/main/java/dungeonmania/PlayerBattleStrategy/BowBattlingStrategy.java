package dungeonmania.PlayerBattleStrategy;

import java.util.HashMap;

public class BowBattlingStrategy extends PlayerBattlingStrategy {

    public BowBattlingStrategy(HashMap<String, String> configMap) {
        super.setDamage(2));
    }

    public double attackModifier() {
        return super.getDamage();
    }
}


