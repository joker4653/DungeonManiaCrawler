package dungeonmania;

public abstract class StaticEntity extends Entity {
    private boolean canBlockPlayerMovement;

    public StaticEntity() {
        super.setMovingEntity(false);
    }

    // Note: all Entities (except for Boulder) must have "super.setCanSpiderBeOnThisEntity(true);" in their constructors
    @Override
    public void setCanSpiderBeOnThisEntity(boolean canSpiderBeOnThisEntity) {
        super.setCanSpiderBeOnThisEntity(canSpiderBeOnThisEntity);
    }

    public void setCanZombieBeOnThisEntity(boolean canZombieBeOnThisEntity) {
        super.setCanZombieBeOnThisEntity(canZombieBeOnThisEntity);
    }

    public void setCanMercBeOnThisEntity(boolean canMercBeOnThisEntity) {
        super.setCanMercBeOnThisEntity(canMercBeOnThisEntity);
    }

    public boolean isCanBlockPlayerMovement() {
        return canBlockPlayerMovement;
    }

    public void setCanBlockPlayerMovement(boolean canBlockPlayerMovement) {
        this.canBlockPlayerMovement = canBlockPlayerMovement;
    }
}
