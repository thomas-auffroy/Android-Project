package Controler.Exercices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;
import java.util.Random;

import Controler.ListCategoriesActivity;
import Controler.MainActivity;
import Model.CategoryListViewAdapter;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.AppDatabase;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.User;

public class MathActivity  extends AppCompatActivity {

    public static final String USER = null;
    public static final String GAME = null;

    private User user;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = (Game) getIntent().getSerializableExtra(GAME);
        user = (User) getIntent().getSerializableExtra(USER);

        switch (game.getName())
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
        Intent intent = new Intent(this,MultiplicationActivity.class);
        intent.putExtra(MultiplicationActivity.USER, user);
        intent.putExtra(MultiplicationActivity.GAME, game);
    }


}
