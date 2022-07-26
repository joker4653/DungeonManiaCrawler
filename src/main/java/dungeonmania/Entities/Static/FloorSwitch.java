package dungeonmania.Entities.Static;

import java.util.List;
import java.util.UUID;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.State;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    private State depressedState;
    private State pressedState;
    private State state;

    public FloorSwitch(int x, int y) {
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("switch");
        super.setInteractable(false);
        this.setCanBlockPlayerMovement(false);

        depressedState = new DepressedState(this);
        pressedState = new PressedState(this);
        state = depressedState;
    }

    public void trigger(List<Entity> listOfEntities) {
        state.trigger(listOfEntities);
    }

    public void untrigger(List<Entity> listOfEntities) {
        state.untrigger(listOfEntities);
    }

    public State getState() {
        return state;
    }

    public boolean isTriggered() {
        if (state.equals(pressedState)) {
            return true;
        } else {
            return false;
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getDepressedState() {
        return depressedState;
    }
    public State getPressedState() {
        return pressedState;
    }
}
