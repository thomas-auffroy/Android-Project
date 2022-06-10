package Controler.Exercices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import Controler.ListCategoriesActivity;
import Model.db.Game;
import Model.db.User;

public class ExerciseSuccess  extends AppCompatActivity {

    private int nb_Erreur = 0;
    private User user;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = (User) getIntent().getSerializableExtra("USER");
        game = (Game) getIntent().getSerializableExtra("GAME");
        nb_Erreur = getIntent().getIntExtra("NB_ERROR", 0);

        System.out.println("Nombre erreur : "+nb_Erreur);
        if(nb_Erreur == 0)
        {
            setContentView(R.layout.activity_mult_felicitation);
        }
        else
        {
            setContentView(R.layout.activity_mult_erreur);

            TextView afficheErreur = findViewById(R.id.mult_erreur_nbErreur);
            afficheErreur.setText("Nombre d'erreurs : "+nb_Erreur);
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
