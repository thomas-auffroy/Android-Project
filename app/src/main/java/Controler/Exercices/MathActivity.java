package Controler.Exercices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Controler.ListCategoriesActivity;
import Controler.LoginUser;
import Controler.MainActivity;
import Model.CategoryListViewAdapter;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.AppDatabase;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.Score;
import Model.db.User;

public class MathActivity  extends AppCompatActivity {

    private User user;
    private Game game;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = (Game) getIntent().getSerializableExtra("GAME"); // Bizarre
        user = (User) getIntent().getSerializableExtra("USER"); // Bizarre

        mDb = DatabaseClient.getInstance(getApplicationContext());

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
        intent.putExtra("USER", user);
        intent.putExtra("GAME", game);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                //|View.SYSTEM_UI_FLAG_FULLSCREEN // Si on veut cacher la barre android du haut
        );
        super.onStart();
    }


}
