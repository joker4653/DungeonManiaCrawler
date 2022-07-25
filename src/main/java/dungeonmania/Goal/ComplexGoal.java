package dungeonmania.Goal;

public class ComplexGoal extends BooleanGoal {

    private String operation;
    private BooleanGoal node1;
    private BooleanGoal node2;

    public ComplexGoal(String operation, BooleanGoal node1, BooleanGoal node2) {
        this.operation = operation;
        this.node1 = node1;
        this.node2 = node2;
    }

    public boolean isComplete() {
        boolean eval1 = node1.isComplete();
        boolean eval2 = node2.isComplete();

        switch (operation) {
            case "AND":
                return (eval1 && eval2);
            case "OR":
                return (eval1 || eval2);
            default:
                return true;
        }
    }

    public void makeComplete(String goal) {
        node1.makeComplete(goal);
        node2.makeComplete(goal);
    }

    public void makeIncomplete(String goal) {
        node1.makeIncomplete(goal);
        node2.makeIncomplete(goal);
    }

    public String prettyPrint() {
        String str1 = node1.prettyPrint();
        String str2 = node2.prettyPrint();

        switch (operation) {
            case "AND":
                return printAND(str1, str2);
            case "OR":
                return printOR(str1, str2);
            default:
                return "";
        }
    }

    private String printAND(String str1, String str2) {
        if (str1.equals("") && str2.equals("")) {
            return "";
        } else if (str1.equals("")) {
            return str2;
        } else if (str2.equals("")) {
            return str1;
        } else {
            return "(" + str1 + " " + operation + " " + str2 + ")";
        }
    }

    private String printOR(String str1, String str2) {
        if (!str1.equals("") && !str2.equals("")) {
            return "(" + str1 + " " + operation + " " + str2 + ")";
        } else {
            return "";
        }
    }


}

