package dungeonmania;

public abstract class StaticEntity extends Entity {


    // Note: all Entities (except for Boulder) must have "super.setCanSpiderBeOnThisEntity(true);" in their constructors
    @Override
    public void setCanSpiderBeOnThisEntity(boolean canSpiderBeOnThisEntity) {
        super.setCanSpiderBeOnThisEntity(canSpiderBeOnThisEntity);
    }

    public void setCanZombieBeOnThisEntity(boolean canZombieBeOnThisEntity) {
        super.setCanZombieBeOnThisEntity(canZombieBeOnThisEntity);
    }

}
