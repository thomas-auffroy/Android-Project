package Controler.Exercices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.Random;

import Controler.MainActivity;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.User;

public class MathActivity  extends AppCompatActivity {

    public static final String GAMENAME = "";
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
                setMultiplicationExercice();
                break;
            default:
                break;
        }
    }

    private void setMultiplicationExercice()
    {

    }

    private void getMultiplicationExercice()
    {
        Random random = new Random();

        TableMultiplication table = new TableMultiplication(random.nextInt(1+10)+1, 10);

        Intent intent = new Intent(this,MultiplicationActivity.class);
        intent.putExtra(MultiplicationActivity.USER, user);
        intent.putExtra(MultiplicationActivity.TABLE, table);

        startActivity(intent);
    }
}
