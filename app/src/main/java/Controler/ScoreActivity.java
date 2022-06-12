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
    private List<String> categories;
    private List<Integer> games;
    private DatabaseClient mDb;
    private LinearLayout linearLayoutPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_score);

        linearLayoutPrincipal = findViewById(R.id.layoutScorePrincipal);

        user = (User) getIntent().getSerializableExtra("USER");
        mDb = DatabaseClient.getInstance(getApplicationContext());

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
                    class GetCategories extends AsyncTask<Void, Void, List<String>> {

                        @Override
                        protected List<String> doInBackground(Void... voids) {
                            categories = mDb.getAppDatabase().scoreDao().getCategoryFromScore(user.getEmail());
                            System.out.println("Nb category : "+categories.size());
                            return categories;
                        }

                        @Override
                        protected void onPostExecute(List<String> categories) {
                            class GetGames extends AsyncTask<Void, Void, List<Integer>> {

                                @Override
                                protected List<Integer> doInBackground(Void... voids) {
                                    games = mDb.getAppDatabase().scoreDao().getGameFromScore(user.getEmail());
                                    System.out.println("Taille jeux : "+games.size());
                                    return games;
                                }

                                @Override
                                protected void onPostExecute(List<Integer> scores) {
                                    afficheScores();
                                }
                            }
                            GetGames getGames = new GetGames();
                            getGames.execute();
                        }
                    }
                    GetCategories getCategories = new GetCategories();
                    getCategories.execute();
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
            ArrayList<Score> scoresFromCategory;
            ArrayList<Integer> nbPlayPerGame;

            /*
            //On parcours toutes les catégories issues des scores
            for(int i = 0; i < categories.size(); i++)
            {
                scoresFromCategory = new ArrayList<Score>();
                nbPlayPerGame = new ArrayList<Integer>();

                //On parcours chaque score pour déterminer le plus élevé
                for(int j = 0; j < games.size(); j++)
                {
                    Score finalScore = null;
                    int nbPlay = 0;

                    for(int k = 0; k < scores.size(); k++)
                    {
                        if(scores.get(k).getCategory().equals(categories.get(i)) && scores.get(k).getGame() == games.get(j))
                        {
                            if(finalScore == null || scores.get(k).getMedaille().compareTo(finalScore.getMedaille()) > 0)
                            {
                                finalScore = scores.get(k);
                            }
                            nbPlay++;
                        }
                    }
                    if(finalScore != null)
                    {
                        scoresFromCategory.add(finalScore);
                    }

                    nbPlayPerGame.add(nbPlay);
                }
                afficheScoresCategory(scoresFromCategory, nbPlayPerGame, categories.get(i));
            }*/
            ArrayList<String> usedCategories = new ArrayList<String>();

            for(int i = 0; i < categories.size(); i++)
            {
                scoresFromCategory = new ArrayList<Score>();
                nbPlayPerGame = new ArrayList<Integer>();

                for(int j = 0; j < scores.size(); j++)
                {
                    Score finalScore;

                    if(scores.get(j).getCategory().equals(categories.get(i)))
                    {
                        scoresFromCategory.add(scores.get(j));
                    }
                }
                afficheScoresCategory(scoresFromCategory, categories.get(i));
            }
        }
        else
        {

        }
    }

    private void afficheScoresCategory(ArrayList<Score> scoresFromCategory, String category)
    {
        LinearLayout linearLayoutCategory = (LinearLayout) getLayoutInflater().inflate(R.layout.template_score_categorie, null);
        LinearLayout linearLayoutCategoryReceiver = linearLayoutCategory.findViewById(R.id.layoutScoreGameCategory);

        TextView titleCategorie = (TextView) linearLayoutCategory.findViewById(R.id.titleScoreCategory);
        titleCategorie.setText(category);

        for(int i = 0; i < scoresFromCategory.size(); i++)
        {
            int gameId = scoresFromCategory.get(i).getId();
            String medaille = scoresFromCategory.get(i).getMedaille().toString();
            //int nbPlay = nbPlayPerGame.get(i);
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
                    //System.out.println(nbPlay);
                    System.out.println(scoreRate);

                    LinearLayout linearLayoutGame = (LinearLayout) getLayoutInflater().inflate(R.layout.template_score_game, null);

                    TextView scoreGameMedail = (TextView) linearLayoutGame.findViewById(R.id.scoreGameMedail);
                    scoreGameMedail.setText(medaille);

                    TextView scoreGameName = (TextView) linearLayoutGame.findViewById(R.id.scoreGameName);
                    scoreGameName.setText(game.getName());

                    TextView scoreGameCompletionRate = (TextView) linearLayoutGame.findViewById(R.id.scoreGameCompletionRate);
                    scoreGameCompletionRate.setText(String.valueOf(scoreRate));

                    TextView scoreNbPlay = (TextView) linearLayoutGame.findViewById(R.id.scoreNbPlay);
                    //scoreNbPlay.setText(String.valueOf(String.valueOf(nbPlay)));

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
