package dungeonmania.Entities.Static;

import java.util.List;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.State;

import java.io.Serializable;
import java.util.ArrayList;

public class PressedState implements State, Serializable {
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
        boolean isBoulder = false;

        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("boulder")) {
                listOfBoulders.add((Boulder)currEntity);
            }
        }

        for (Boulder boulder : listOfBoulders) {
            if (boulder.getCurrentLocation().equals(floorSwitch.getCurrentLocation())) {
                isBoulder = true;
            }
        }

        if (isBoulder == false) {
            floorSwitch.setState(floorSwitch.getDepressedState());
        }
    }
}
