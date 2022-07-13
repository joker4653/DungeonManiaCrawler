package dungeonmania;

import java.util.List;
import java.util.ArrayList;

public interface State {
    public void trigger(List<Entity> listOfEntities, Player player);
    public void untrigger(List<Entity> listOfEntities, Player player);
}
