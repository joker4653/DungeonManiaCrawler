package dungeonmania.Entities.Moving;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dungeonmania.Statistics;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends MovingEntity {
    private double hydraRate;
    private double hydraAmt;
    private long seed;
    private Random random;

    public Hydra(int x, int y, HashMap<String, String> configMap) {
        super();
        super.setEntityID(UUID.randomUUID().toString());
        super.setInteractable(false);
        super.setEntityType("hydra");
        super.setEnemyHealth(configMap.get("hydra_health") != null ? Double.parseDouble(configMap.get("hydra_health")) : 0);
        super.setAlly(false);
        super.setCanStepOn("hydra");
        super.setCurrentLocation(new Position(x, y));
        super.setMovementFactor(configMap.get("movement_factor") != null ? Integer.parseInt(configMap.get("movement_factor")) : 0);
        super.setEnemyDamage(configMap.get("hydra_attack") != null ? Double.parseDouble(configMap.get("hydra_attack")) : 0);
        this.hydraRate = configMap.get("hydra_health_increase_rate") != null ? Double.parseDouble(configMap.get("hydra_health_increase_rate")) : 0;
        this.hydraAmt = configMap.get("hydra_health_increase_amount") != null ? Double.parseDouble(configMap.get("hydra_health_increase_amount")) : 0;
        this.seed = System.currentTimeMillis();
        this.random = new Random(seed);
    }

    @Override
    public void move(List<Entity> listOfEntities, Direction dir, Player player, Inventory inventory,
                     Statistics statistics) {
        super.moveRandomly(listOfEntities, dir, player, inventory, statistics);
    }

    @Override
    public double getDeltaEnemyHealth(double playerAttack) {
        if (random.nextDouble() <= hydraRate) {
            // health increases rather than decreases
            return -hydraAmt;
        }

        return playerAttack / 5;
    }

    /* Getters & Setters */

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
