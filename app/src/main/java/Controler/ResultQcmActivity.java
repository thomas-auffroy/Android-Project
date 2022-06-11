package Controler;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.Question;
import Model.db.QuestionDao;
import Model.db.Reponse;
import Model.db.User;

public class ResultQcmActivity extends AppCompatActivity {

    private User user;
    private Game game;
    private Integer goodAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qcm);

        game = (Game) getIntent().getSerializableExtra("GAME");
        user = (User) getIntent().getSerializableExtra("USER");




        System.out.println(game);


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

    public void backward(View view) {
        Intent intent = new Intent(this, ListGamesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("USER", user);
        intent.putExtra(ListGamesActivity.CATEGORY, game.getCategorie());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
        startActivity(intent);
    }
}
