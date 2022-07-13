package dungeonmania;

public class DepressedState implements State {
    private FloorSwitch floorSwitch;

    public DepressedState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    @Override
    public void trigger() {



        floorSwitch.setState(floorSwitch.getPressedState());
    }

    @Override
    public void untrigger() {
        
    }
}
