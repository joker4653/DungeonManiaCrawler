package dungeonmania.Goal;


public class SimpleGoal extends BooleanGoal {

    private String name;
    private boolean value = false;

    public SimpleGoal(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return value;
    }

    public void makeComplete(String goal) {
        if (name.equals(goal)) {
            this.value = true;
        }
    }

    public void makeIncomplete(String goal) {
        if (name.equals(goal)) {
            this.value = false;
        }
    }

    public String prettyPrint() {
        if (!this.isComplete()) {
            return name;
        } else {
            return "";
        }
    }
}

