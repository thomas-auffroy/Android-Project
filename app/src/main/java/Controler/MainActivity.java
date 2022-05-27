package Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import Model.db.DatabaseClient;

public class MainActivity  extends AppCompatActivity {
    private Button buttonAdd;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_homepage_no_login);

        Button loginBTN = findViewById(R.id.btnConnection);

        /*
        setContentView(R.layout.activity_connection);
        */
        /*
        setContentView(R.layout.test_main);

        //Initialise DB
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        buttonAdd = findViewById(R.id.addUser);

        // Ajouter un événement au bouton d'ajout
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUser.class);
                startActivity(intent);
            }
        });
        */
    }

    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                //|View.SYSTEM_UI_FLAG_FULLSCREEN // Si on veut cacher la barre android du haut
        );
        super.onStart();
    }


    public void login(View view){
        Intent intent = new Intent(this, LoginUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
    }

}
