package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Controler.Exercices.MathActivity;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.Score;
import Model.db.User;

public class ScoreActivity extends AppCompatActivity {

    private User user;
    private List<Score> scores;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_score);


        user = (User) getIntent().getSerializableExtra("USER");
        mDb = DatabaseClient.getInstance(getApplicationContext());

        if(user.getEmail().equals("anonymous"))
        {
            scores = Score.scoresAnonyme;
            afficheScoresCategory();
        }

        else
        {
            class GetScores extends AsyncTask<Void, Void, List<Score>> {
                @Override
                protected List<Score> doInBackground(Void... voids) {
                    scores = mDb.getAppDatabase().scoreDao().getHighestScore(user.getEmail());
                    return scores;
                }

                @Override
                protected void onPostExecute(List<Score> score) {
                    afficheScoresCategory();
                }
            }
            GetScores getScores = new GetScores();
            getScores.execute();
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
        System.out.println("oui");
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    private void afficheScoresCategory()
    {
        LinearLayout linearLayoutPrincipal = findViewById(R.id.layoutScorePrincipal);

        for(int i = 0; i < scores.size(); i++)
        {
            int gameId = scores.get(i).getId();
            String category = scores.get(i).getCategory();
            String medaille = scores.get(i).getMedaille().toString();
            float scoreRate = scores.get(i).getScore();
            String scoreRateToString = String.valueOf((int)scores.get(i).getScore())+"%";

            class GetGame extends AsyncTask<Void, Void, Game> {
                @Override
                protected Game doInBackground(Void... voids) {
                    Game game = mDb.getAppDatabase().gameDao().getGameById(gameId);
                    return game;
                }

                @Override
                protected void onPostExecute(Game game) {
                    LinearLayout linearLayoutCategory = (LinearLayout) getLayoutInflater().inflate(R.layout.template_score_categorie, null);

                    TextView titleCategorie = (TextView) linearLayoutCategory.findViewById(R.id.titleScoreCategory);
                    titleCategorie.setText(category);

                    TextView scoreGameMedail = (TextView) linearLayoutCategory.findViewById(R.id.scoreGameMedail);
                    scoreGameMedail.setText(medaille);

                    TextView scoreGameName = (TextView) linearLayoutCategory.findViewById(R.id.scoreGameName);
                    scoreGameName.setText(game.getName());

                    TextView scoreGameCompletionRate = (TextView) linearLayoutCategory.findViewById(R.id.scoreGameCompletionRate);
                    scoreGameCompletionRate.setText(scoreRateToString);

                    linearLayoutPrincipal.addView(linearLayoutCategory);
                }
            }
            GetGame getGame = new GetGame();
            getGame.execute();
        }
    }
}
