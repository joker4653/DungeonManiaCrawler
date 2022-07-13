package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    private State depressedState;
    private State pressedState;
    private State state;

    public FloorSwitch(int x, int y) {
        super.setCanSpiderBeOnThisEntity(false);
        super.setCanZombieBeOnThisEntity(false);
        super.setCurrentLocation(new Position(x, y));
        super.setEntityID(UUID.randomUUID().toString());
        super.setEntityType("boulder");
        super.setInteractable(false);
        super.setCanMercBeOnThisEntity(false);
        this.setCanBlockPlayerMovement(false);

        depressedState = new DepressedState(this);
        pressedState = new PressedState(this);
        state = depressedState;
    }

    public void trigger(List<Entity> listOfEntities, Player player) {
        state.trigger(listOfEntities, player);
    }

    public void untrigger(List<Entity> listOfEntities, Player player) {
        state.untrigger(listOfEntities, player);
    }

    public State getState() {
        return state;
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
