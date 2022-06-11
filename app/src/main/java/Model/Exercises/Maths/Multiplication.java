package Model.Exercises.Maths;

import java.io.Serializable;

public class Multiplication implements Serializable {
    private int operande1;
    private int operande2;

    public Multiplication(int operande1, int operande2) {
        this.operande1 = operande1;
        this.operande2 = operande2;
    }

    public int getOperande1() {
        return operande1;
    }

    public int getOperande2() {
        return operande2;
    }

    public int getResult() {
        return operande1*operande2;
    }
}
