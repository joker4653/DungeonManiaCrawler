package dungeonmania.Entities.Moving;

import java.util.List;

import dungeonmania.Entities.Entity;

public interface Observer {
    
    public void update(String Potion, List<Entity> ListOfEntites);
}
