package dungeonmania;

import java.util.List;
import java.util.ArrayList;

public class PressedState implements State {
    private FloorSwitch floorSwitch;

    public PressedState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    @Override
    public void trigger(List<Entity> listOfEntities) {
    
    }

    @Override
    public void untrigger(List<Entity> listOfEntities) {
        List<Boulder> listOfBoulders = new ArrayList<>();

        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("boulder")) {
                listOfBoulders.add((Boulder)currEntity);
            }
        }

        for (Boulder boulder : listOfBoulders) {
            if (!(boulder.getCurrentLocation().equals(floorSwitch.getCurrentLocation()))) {
                floorSwitch.setState(floorSwitch.getDepressedState());
            }
        }
    }
}
