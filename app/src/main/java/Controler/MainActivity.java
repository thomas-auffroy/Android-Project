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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
