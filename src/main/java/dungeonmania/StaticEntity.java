package dungeonmania;

public abstract class StaticEntity extends Entity {
    private boolean canBlockPlayerMovement;

    public StaticEntity() {
        super.setMovingEntity(false);
    }

    public boolean isCanBlockPlayerMovement() {
        return canBlockPlayerMovement;
    }

    public void setCanBlockPlayerMovement(boolean canBlockPlayerMovement) {
        this.canBlockPlayerMovement = canBlockPlayerMovement;
    }
}
