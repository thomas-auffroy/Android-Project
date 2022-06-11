package Controler.Exercices;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.ArrayList;

import Controler.ListCategoriesActivity;
import Controler.MainActivity;
import Controler.SubscriptionUser;
import Model.Enum.Medails;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.Score;
import Model.db.User;

public class ExerciseSuccess  extends AppCompatActivity {

    private Integer nb_Erreur = 0;
    private User user;
    private Game game;
    private ArrayList<Integer> reponsesJoueur;
    private ArrayList<Integer> bonnesReponses;
    private ArrayList<String> questions;
    private Score score;
    private DatabaseClient mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        reponsesJoueur = new ArrayList<Integer>();
        bonnesReponses = new ArrayList<Integer>();
        questions = new ArrayList<String>();

        user = (User) getIntent().getSerializableExtra("USER");
        game = (Game) getIntent().getSerializableExtra("GAME");
        nb_Erreur = getIntent().getIntExtra("NB_ERROR", 0);
        reponsesJoueur = getIntent().getIntegerArrayListExtra("REPONSESJOUEUR");
        bonnesReponses = getIntent().getIntegerArrayListExtra("BONNESREPONSES");
        questions = getIntent().getStringArrayListExtra("QUESTIONS");

        setContentView(R.layout.activity_maths_results);
        afficherReponses();
        majScore();

        System.out.println("Nombre erreur : "+nb_Erreur);
    }


    public void afficherReponses()
    {
        for(int i = 0; i < questions.size(); i++)
        {
            LinearLayout linearLayoutPrincipal = findViewById(R.id.mathsAnswersPrincipal);
            LinearLayout linearLayoutAnswers = findViewById(R.id.mathsAnswers);
            LinearLayout linearLayoutResult = (LinearLayout) getLayoutInflater().inflate(R.layout.template_resultat, null);

            TextView ligne = (TextView) linearLayoutResult.findViewById(R.id.template_calcul_success);
            ligne.setText(questions.get(i));
            
            TextView rep = (TextView) linearLayoutResult.findViewById(R.id.template_resultat_success);

            if(reponsesJoueur.get(i) == 0)
            {
                rep.setText("Absence de réponse");
                rep.setBackgroundColor(Color.RED);
            }
            else if(reponsesJoueur.get(i) != bonnesReponses.get(i))
            {
                rep.setText(reponsesJoueur.get(i).toString());
                rep.setBackgroundColor(Color.RED);
            }
            else
            {
                rep.setText(reponsesJoueur.get(i).toString());
                rep.setBackgroundColor(Color.GREEN);
            }

            TextView nbErreurs = (TextView) linearLayoutPrincipal.findViewById(R.id.nbErrors);
            nbErreurs.setText(nb_Erreur.toString());

            linearLayoutAnswers.addView(linearLayoutResult);
        }
    }

    public void majScore()
    {
        score = new Score();

        Medails medaille;
        Integer gameID = game.getId();
        String userEmail = user.getEmail();

        int successPercentage = (nb_Erreur * 100) / questions.size();

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

        if(user.getEmail() != "anonymous")
        {
            class SaveScore extends AsyncTask<Void, Void, Score> {

                @Override
                protected Score doInBackground(Void... voids) {
                    // adding to database
                    mDb.getAppDatabase().scoreDao().insert(score);

                    return score;
                }

                @Override
                protected void onPostExecute(Score score) {
                }
            }
        }
        else
        {
            Score.scoresAnonyme.add(score);
        }

    }

    public void autreTable(View view)
    {
        Intent intent = new Intent(this, MultiplicationActivity.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void autreExercice(View view)
    {
        Intent intent = new Intent(this, ListCategoriesActivity.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void corriger(View view)
    {
        super.finish();
    }


}
