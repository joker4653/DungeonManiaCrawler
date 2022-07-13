package dungeonmania;

import java.util.List;
import java.util.ArrayList;

public class PressedState implements State {
    private FloorSwitch floorSwitch;

    public PressedState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    @Override
    public void trigger(List<Entity> listOfEntities, Player player) {
    
    }

    @Override
    public void untrigger(List<Entity> listOfEntities, Player player) {
        


        floorSwitch.setState(floorSwitch.getDepressedState());
    }
}
