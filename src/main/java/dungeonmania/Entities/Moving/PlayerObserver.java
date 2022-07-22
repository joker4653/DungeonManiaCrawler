package dungeonmania.Entities.Moving;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.Battling.EnemyBattleStrategy.MercenaryAllyStrategy;
import dungeonmania.Entities.Entity;

public class PlayerObserver implements Observer, Serializable {
    private Player player;


    public PlayerObserver(Player player) {
        this.player = player;
        this.player.attach(this);
    }
    @Override
    public void update(String potion, List<Entity> ListOfEntities) {
        List<Entity> Zombs = ListOfEntities.stream().filter(e -> (e.getEntityType().equals("zombie_toast"))).collect(Collectors.toList());
        List<Entity> Mercs = ListOfEntities.stream().filter(e -> (e.getEntityType().equals("mercenary"))).collect(Collectors.toList());

        for (Entity e : Zombs) {
            ZombieToast zomb = (ZombieToast) e;
            // zomb.update(potion);
        }

        for (Entity e : Mercs) {
            Mercenary merc = (Mercenary) e;
            // merc.update(potion);
        }
        
    }
    
}
