package dungeonmania;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class DepressedState implements State, Serializable {
    private FloorSwitch floorSwitch;

    public DepressedState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    @Override
    public void trigger(List<Entity> listOfEntities) {
        List<Boulder> listOfBoulders = new ArrayList<>();

        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("boulder")) {
                listOfBoulders.add((Boulder)currEntity);
            }
        }

        for (Boulder boulder : listOfBoulders) {
            if (boulder.getCurrentLocation().equals(floorSwitch.getCurrentLocation())) {
                floorSwitch.setState(floorSwitch.getPressedState());
            }
        }
    }

    @Override
    public void untrigger(List<Entity> listOfEntities) {
        
    }
}
