package dungeonmania.Entities.Moving;

import java.util.HashMap;
import java.util.UUID;

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
    }
    
}
