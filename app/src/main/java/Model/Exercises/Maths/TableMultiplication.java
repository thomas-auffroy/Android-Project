package Model.Exercises.Maths;

import java.util.ArrayList;

public class TableMultiplication {
    private ArrayList<Multiplication> tableMultiplication;

    public TableMultiplication(int table)
    {
        this.tableMultiplication = new ArrayList<Multiplication>();

        for(int i = 1; i <= 10; i++)
        {
            this.tableMultiplication.add(new Multiplication(table, i));
        }
    }

    public ArrayList<Multiplication> getTableMultiplication()
    {
        return this.tableMultiplication;
    }
}
