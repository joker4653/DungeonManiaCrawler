package dungeonmania.Goal;

import java.io.Serializable;


public abstract class BooleanGoal implements Serializable {

    public abstract boolean isComplete();
    public abstract String prettyPrint();

    public abstract void makeComplete(String goal);
    public abstract void makeIncomplete(String goal);

}

