package dungeonmania;

public abstract class StaticEntity extends Entity {

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
}
