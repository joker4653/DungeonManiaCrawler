package dungeonmania.Entities.Moving;

import java.util.List;

import dungeonmania.Entities.Entity;

public class PlayerObserver implements Observer {
   List<Entity> listofEntities;
   Player player;
    
    public PlayerObserver(Player player, List<Entity> listofEntities) {
        this.player = player;
        this.listofEntities = listofEntities;

        this.player.attach(this);
    }


    @Override
    public void update(String CurrentPotion) {
        listofEntities.stream().filter(e -> (e.isMovingEntity() && !e.getEntityID().equals(player.getEntityID()))).forEach(e -> {e.setCurrPlayerPotion(CurrentPotion);});
        
    }
    
}
