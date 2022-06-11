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
    private Integer totalQuestions;

    private TextView result;
    private TextView nameCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qcm);

        game = (Game) getIntent().getSerializableExtra("GAME");
        user = (User) getIntent().getSerializableExtra("USER");
        goodAnswers = (Integer) getIntent().getSerializableExtra("GOOD_ANSWERS");
        totalQuestions = (Integer) getIntent().getSerializableExtra("TOTAL_QUESTION");

        nameCategory = findViewById(R.id.nameCategoryResultQcm);
        nameCategory.setText(game.getCategorie());


        result = findViewById(R.id.resultatQCMText);
        if (goodAnswers > 1) {
            result.setText("Vous avez obtenu " + goodAnswers + " bonnes réponses sur " + totalQuestions);
        } else {
            result.setText("Vous avez obtenu " + goodAnswers + " bonne réponse sur " + totalQuestions + ". Courage, vous allez y arriver");
        }


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
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void playAgain(View view){
        Intent intent = new Intent(this, GameQcmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("USER", user);
        intent.putExtra("GAME", game);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }
}
