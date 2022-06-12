package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

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
    private ArrayList<String> categories;
    private DatabaseClient mDb;
    private int gameId;
    private LinearLayout linearLayoutPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_score);

        linearLayoutPrincipal = findViewById(R.id.layoutScorePrincipal);

        user = (User) getIntent().getSerializableExtra("USER");
        mDb = DatabaseClient.getInstance(getApplicationContext());

        categories = new ArrayList<String>();

        if(user.getEmail().equals("anonymous"))
        {
            scores = Score.scoresAnonyme;
            afficheScores();
        }

        else
        {
            class GetScoreUser extends AsyncTask<Void, Void, List<Score>> {

                @Override
                protected List<Score> doInBackground(Void... voids) {
                    scores = mDb.getAppDatabase().scoreDao().getAllScoreFromUser(user.getEmail());
                    return scores;
                }

                @Override
                protected void onPostExecute(List<Score> scores) {
                    afficheScores();
                }
            }
            GetScoreUser getScoreUser = new GetScoreUser();
            getScoreUser.execute();
        }
    }

    private void afficheScores()
    {
        if(scores != null)
        {
            for(int i = 0; i < scores.size(); i++)
            {
                if(!categories.contains(scores.get(i).getCategory()))
                {
                    categories.add(scores.get(i).getCategory());
                }
            }

            //On parcours toutes les catégories issues des scores
            for(int i = 0; i < categories.size(); i++)
            {
                ArrayList<Score> scoresFromCategory = new ArrayList<Score>();
                ArrayList<Integer> nbPlayPerGame = new ArrayList<Integer>();

                //On parcours chaque score pour déterminer le plus élevé
                for(int j = 0; j < scores.size(); j++)
                {
                    int nbPlay = 1; //Nb de fois où le jeu a été joué

                    String category = categories.get(i);
                    System.out.println("Catégorie boucle for: "+category);

                    Score score = scores.get(j);

                    //On récupère un premier score de même catégorie
                    if(score.getCategory().equals(category))
                    {
                        Score finalScore = scores.get(j); //Le premier score trouvé

                        //On parcours à nouveau les scores pour vérifier qu'il n'y a pas de doublon
                        for(int k = 0; k < scores.size(); k++)
                        {
                            //Si 2 scores pour le même jeu
                            if(scores.get(k).getGame() == finalScore.getGame())
                            {
                                nbPlay++;

                                //Si le score trouvé comporte une meilleur médaille que le score initial -> On remplace
                                if(scores.get(k).getMedaille().compareTo(finalScore.getMedaille()) > 0)
                                {
                                    finalScore = scores.get(k);
                                }
                            }
                        }
                        scoresFromCategory.add(finalScore);
                        nbPlayPerGame.add(nbPlay);

                        afficheScoresCategory(scoresFromCategory, nbPlayPerGame, category);
                    }
                }
            }
        }
        else
        {

        }
    }

    private void afficheScoresCategory(ArrayList<Score> scoresFromCategory, ArrayList<Integer> nbPlayPerGame, String category)
    {
        LinearLayout linearLayoutCategory = (LinearLayout) getLayoutInflater().inflate(R.layout.template_score_categorie, null);
        LinearLayout linearLayoutCategoryReceiver = linearLayoutCategory.findViewById(R.id.layoutScoreGameCategory);

        TextView titleCategorie = (TextView) linearLayoutCategory.findViewById(R.id.titleScoreCategory);
        titleCategorie.setText(category);
        System.out.println("Catégorie affiche: "+category);

        for(int i = 0; i < scoresFromCategory.size(); i++)
        {
            int gameId = scoresFromCategory.get(i).getId();
            String medaille = scoresFromCategory.get(i).getMedaille().toString();
            int nbPlay = nbPlayPerGame.get(i);
            float scoreRate = scoresFromCategory.get(i).getScore();

            class GetGame extends AsyncTask<Void, Void, Game> {
                @Override
                protected Game doInBackground(Void... voids) {
                    Game game = mDb.getAppDatabase().gameDao().getGameById(gameId);
                    return game;
                }

                @Override
                protected void onPostExecute(Game game) {
                    System.out.println(game.getName());
                    System.out.println(game.getId());
                    System.out.println(gameId);
                    System.out.println(medaille);
                    System.out.println(nbPlay);
                    System.out.println(scoreRate);

                    LinearLayout linearLayoutGame = (LinearLayout) getLayoutInflater().inflate(R.layout.template_score_game, null);

                    TextView scoreGameMedail = (TextView) linearLayoutGame.findViewById(R.id.scoreGameMedail);
                    scoreGameMedail.setText(medaille);

                    TextView scoreGameName = (TextView) linearLayoutGame.findViewById(R.id.scoreGameName);
                    scoreGameName.setText(game.getName());

                    TextView scoreGameCompletionRate = (TextView) linearLayoutGame.findViewById(R.id.scoreGameCompletionRate);
                    scoreGameCompletionRate.setText(String.valueOf(scoreRate));

                    TextView scoreNbPlay = (TextView) linearLayoutGame.findViewById(R.id.scoreNbPlay);
                    scoreNbPlay.setText(String.valueOf(String.valueOf(nbPlay)));

                    linearLayoutCategoryReceiver.addView(linearLayoutGame);
                }
            }
            GetGame getGame = new GetGame();
            getGame.execute();

        }

        this.linearLayoutPrincipal.addView(linearLayoutCategory);
    }

    public void backward(View view) {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }
}
