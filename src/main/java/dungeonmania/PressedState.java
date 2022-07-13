package dungeonmania;

public class PressedState implements State {
    private FloorSwitch floorSwitch;

    public PressedState(FloorSwitch floorSwitch) {
        this.floorSwitch = floorSwitch;
    }

    @Override
    public void trigger() {
        
    }

    @Override
    public void untrigger() {
        

        
        floorSwitch.setState(floorSwitch.getDepressedState());
    }
}
