package dungeonmania.Entities;

import java.util.List;

public interface State {
    public void trigger(List<Entity> listOfEntities);
    public void untrigger(List<Entity> listOfEntities);
}
