package dungeonmania.Entities.Moving;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Entities.Entity;

public class PlayerObserver implements Observer, Serializable {
    private Player player;


    public PlayerObserver(Player player) {
        this.player = player;
        this.player.attach(this);
    }
    @Override
    public void update(String potion, List<Entity> ListOfEntities) {
        List<Entity> MovingEntities = ListOfEntities.stream().filter(e -> (e.getEntityType().equals("zombie_toast") ||
                                                                            e.getEntityType().equals("mercenary") ||
                                                                            e.getEntityType().equals("hydra") ||
                                                                            e.getEntityType().equals("assassin") ||
                                                                            e.getEntityType().equals("spider")        
                                                                            )).collect(Collectors.toList());

                                                                            

        for (Entity e : MovingEntities) {
            MovingEntity ent = (MovingEntity) e;
            ent.setCurrentPlayerPotion(potion);
        }
        
    }
    
}
