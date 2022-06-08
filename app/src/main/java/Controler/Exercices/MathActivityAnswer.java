package Controler.Exercices;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import Model.Exercises.Maths.TableMultiplication;
import Model.db.User;

public class MathActivityAnswer  extends AppCompatActivity {
    public static final String RES = null;
    public static final String GAMENAME = null;
    public static final String USER = null;

    private User user;
    private String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = (User) getIntent().getSerializableExtra(USER);
        gameName = (String) getIntent().getSerializableExtra(GAMENAME);

        switch (gameName)
        {
            case "Multiplication":
                getMultiplicationResult();
                break;
            default:
                break;
        }
    }

    private void getMultiplicationResult()
    {
        TableMultiplication table = (TableMultiplication) getIntent().getSerializableExtra(RES);


    }
}
