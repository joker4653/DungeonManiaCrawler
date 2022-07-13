package dungeonmania;

public class FloorSwitch extends StaticEntity {
    private State depressedState;
    private State pressedState;
    private State state;

    public FloorSwitch() {
        depressedState = new DepressedState(this);
        pressedState = new PressedState(this);
        state = depressedState;
    }

    public void trigger() {
        state.trigger();
    }

    public void untrigger() {
        state.untrigger();
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
