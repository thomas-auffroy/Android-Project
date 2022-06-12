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

import Model.Enum.Medails;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.Question;
import Model.db.QuestionDao;
import Model.db.Reponse;
import Model.db.Score;
import Model.db.User;

public class ResultQcmActivity extends AppCompatActivity {

    private DatabaseClient mDb;
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
        mDb = DatabaseClient.getInstance(getApplicationContext());

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

        majScore();
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
        intent.putExtra("CATEGORY", game.getCategorie());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void playAgain(View view){
        GameQcmActivity.resetStaticVariables();

        Intent intent = new Intent(this, GameQcmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("USER", user);
        intent.putExtra("GAME", game);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void majScore()
    {
        Score score = new Score();

        Medails medaille;
        Integer gameID = game.getId();
        String userEmail = user.getEmail();

        int nbBonnesRep = goodAnswers;
        int sizeQuestion = totalQuestions;

        float successPercentage;
        successPercentage = ((float) nbBonnesRep / sizeQuestion) * 100;

        if(successPercentage <= 50)
        {
            medaille = Medails.PARTICIPATION;
        }
        else if(successPercentage <= 70)
        {
            medaille = Medails.BRONZE;
        }
        else if(successPercentage <= 80)
        {
            medaille = Medails.ARGENT;
        }
        else if(successPercentage <= 99)
        {
            medaille = Medails.OR;
        }
        else
        {
            medaille = Medails.PLATINE;
        }

        score.setScore(successPercentage);
        score.setMedaille(medaille);
        score.setUser(userEmail);
        score.setGame(gameID);

        if(!user.getEmail().equals("anonymous"))
        {
            class SaveScore extends AsyncTask<Void, Void, Score> {

                @Override
                protected Score doInBackground(Void... voids) {
                    mDb.getAppDatabase().scoreDao().insert(score);
                    return score;
                }

                @Override
                protected void onPostExecute(Score score) {

                    //Test score
                    /*
                    class PrintScore extends AsyncTask<Void, Void, List<Score>> {

                        @Override
                        protected List<Score> doInBackground(Void... voids) {
                            return mDb.getAppDatabase().scoreDao().getAllScoreFromUser(user.getEmail());
                        }

                        @Override
                        protected void onPostExecute(List<Score> scores) {
                            for (int i = 0; i < scores.size(); i++) {
                                System.out.println("Id : " + scores.get(i).getId());
                                System.out.println("Jeu : " + scores.get(i).getGame());
                                System.out.println("Medaille : " + scores.get(i).getMedaille());
                            }
                        }
                    }
                    PrintScore printScore = new PrintScore();
                    printScore.execute();
                }
                */
                }
            }
            SaveScore saveScore = new SaveScore();
            saveScore.execute();
        }
        else
        {
            if(Score.scoresAnonyme == null)
            {
                Score.scoresAnonyme = new ArrayList<Score>();
            }

            Score.scoresAnonyme.add(score);
        }
    }
}
