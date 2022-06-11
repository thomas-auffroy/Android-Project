package Controler.Exercices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.ArrayList;
import java.util.Random;

import Model.Exercises.Maths.TableMultiplication;
import Model.db.Game;
import Model.db.User;

public class MultiplicationActivity extends AppCompatActivity {

    private TableMultiplication tableMultiplication;
    private User user;
    private Game game;
    private ArrayList<EditText> reponsesTextView;
    private ArrayList<Integer> reponsesJoueur;
    private ArrayList<Integer> bonnesReponses;
    private ArrayList<String> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_multiplication_reponse);

        user = (User) getIntent().getSerializableExtra("USER");
        game = (Game) getIntent().getSerializableExtra("GAME");

        Random random = new Random();
        tableMultiplication = new TableMultiplication(random.nextInt((10-1)+1)+1, 10);

        reponsesTextView = new ArrayList<EditText>();
        reponsesJoueur = new ArrayList<Integer>();
        bonnesReponses = new ArrayList<Integer>();
        questions = new ArrayList<String>();

        setTableMultiplication();
    }

    public void setTableMultiplication()
    {
        for(int i = 0; i < this.tableMultiplication.getMultiplications().size(); i++)
        {
            String question = tableMultiplication.getMultiplication(i).getOperande1()+" x "+tableMultiplication.getMultiplication(i).getOperande2()+" = ";
            questions.add(question);
            bonnesReponses.add(tableMultiplication.getMultiplication(i).getResult());

            LinearLayout linearLayoutPrincipal = findViewById(R.id.multLinearPrincip);

            LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.template_calcul, null);


            TextView ligne = (TextView) linearLayout.findViewById(R.id.template_calcul);
            ligne.setText(question);


            EditText rep = (EditText) linearLayout.findViewById(R.id.template_resultat);
            reponsesTextView.add(rep);

            linearLayoutPrincipal.addView(linearLayout);
        }
    }

    public void getResult(View view)
    {
        int nbError = 0;

        for(int i = 0; i < reponsesTextView.size(); i++)
        {
            String value = reponsesTextView.get(i).getText().toString();
            System.out.println("Réponse : "+i+" = "+value);

            if(!value.isEmpty())
            {
                int finalValue = Integer.parseInt(value);

                if(finalValue != this.tableMultiplication.getMultiplications().get(i).getResult())
                {
                    nbError++;
                }

                reponsesJoueur.add(finalValue);
            }
            else
            {
                nbError++;
                reponsesJoueur.add(0);
            }
        }

        Intent intent =  new Intent(this,ExerciseSuccess.class);
        intent.putExtra("NB_ERROR", nbError);
        intent.putExtra("USER", user);
        intent.putExtra("GAME", game);
        intent.putExtra("REPONSESJOUEUR", reponsesJoueur);
        intent.putExtra("BONNESREPONSES", bonnesReponses);
        intent.putExtra("QUESTIONS", questions);

        startActivity(intent);
    }
}
