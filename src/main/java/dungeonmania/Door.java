package dungeonmania;

public class Door extends StaticEntity {
    private boolean isLocked;

    public Door() {
        super.setCanZombieBeOnThisEntity(false);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        if (isLocked) {
            super.setCanZombieBeOnThisEntity(false);
        } else {
            super.setCanZombieBeOnThisEntity(true);
        }

        this.isLocked = isLocked;
    }

}
