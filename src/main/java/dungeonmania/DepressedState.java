package dungeonmania;

import java.util.List;
import java.util.ArrayList;

public class DepressedState implements State {
    private FloorSwitch floorSwitch;

    public DepressedState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    @Override
    public void trigger(List<Entity> listOfEntities, Player player) {



        floorSwitch.setState(floorSwitch.getPressedState());
    }

    @Override
    public void untrigger(List<Entity> listOfEntities, Player player) {
        
    }
}
