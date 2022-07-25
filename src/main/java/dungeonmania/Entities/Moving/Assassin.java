package dungeonmania.Entities.Moving;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import dungeonmania.Battling.EnemyBattleStrategy.AllyStrategy;
import dungeonmania.Battling.EnemyBattleStrategy.StandardBattlingStrategy;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

    public Assassin(int x, int y, HashMap<String, String> configMap) {
        super(x, y, configMap);
        super.setAlly(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(true);
        super.setEntityType("assassin");
        super.setEnemyHealth(Double.parseDouble(configMap.get("assassin_health")));
        super.setNeighbour(false);
        super.setConfigMap(configMap);
        super.setCanStepOn("assassin");
        super.setBribe(Integer.parseInt(configMap.get("assassin_bribe_amount")));
        super.enemyChangeStrategy(new StandardBattlingStrategy(configMap, "assassin"));
    }
    
    @Override
    public void becomeAlly(Mercenary merc, Player player, HashMap<String, String> configMap) {
        if (new Random().nextDouble() <= (1 - Double.parseDouble(getConfigMap().get("assassin_bribe_fail_rate")))) {
            super.becomeAlly(this, player, configMap);
            super.enemyChangeStrategy(new AllyStrategy(configMap, "assassin"));
        }
    }
}
