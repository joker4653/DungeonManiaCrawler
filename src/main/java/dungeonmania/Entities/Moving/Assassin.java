package dungeonmania.Entities.Moving;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    private Random random;
    private double failRate;

    public Assassin(int x, int y, HashMap<String, String> configMap) {
        super(x, y, configMap);
        super.setAlly(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(true);
        super.setEntityType("assassin");
        super.setEnemyHealth(configMap.get("assassin_health") != null ? Double.parseDouble(configMap.get("assassin_health")) : 0);
        super.setNeighbour(false);
        super.setConfigMap(configMap);
        super.setCanStepOn("assassin");
        super.setBribe(configMap.get("assassin_bribe_amount") != null ? Integer.parseInt(configMap.get("assassin_bribe_amount")) : 0);
        this.random = new Random();
        this.failRate = configMap.get("assassin_bribe_fail_rate") != null ? Double.parseDouble(configMap.get("assassin_bribe_fail_rate")) : 0;
        super.setEnemyHealth(configMap.get("assassin_attack") != null ? Double.parseDouble(configMap.get("assassin_attack")) : 0);
    }
    
    @Override
    public void becomeAlly(Mercenary merc, Player player) {
        if (random.nextDouble() <= (1 - failRate)) {
            super.becomeAlly(this, player);
        }
    }

    /* Getters & Setters */

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
