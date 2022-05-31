package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;

import Model.db.DatabaseClient;
import Model.db.User;

public class LoginUser extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private List<String> emailsInDb;
    private User user;

    //VIEW
    private EditText email;
    private EditText motDePasse;
    private Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connection);
        user = new User();

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
        emailsInDb = null;

        // Récupérer les vues
        email = findViewById(R.id.dataEmailConnection);
        motDePasse = findViewById(R.id.dataPasswordConnection);
        connect = findViewById(R.id.btnConnection);

        // Associer un événement au bouton save
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection();
            }
        });

        // Récupérer les emails dans la base
        class GetEmails extends AsyncTask<Void, Void, List<String>> {

            @Override
            protected List<String> doInBackground(Void... voids) {

                // adding to database
                emailsInDb = mDb.getAppDatabase().userDao().getAllEmails();
                return emailsInDb;
            }

            @Override
            protected void onPostExecute(List<String> emails) {
                super.onPostExecute(emails);
            }
        }

        // Executer tache asynchrone
        GetEmails getEmails = new GetEmails();
        getEmails.execute();
    }

    private void Connection() {
        final String sEmail = email.getText().toString();
        final String sMotDePasse = motDePasse.getText().toString();

        if (sEmail.isEmpty()) {
            email.setError("Entre une adresse email");
            email.requestFocus();
            return;
        }

        else if (emailsInDb != null && !emailsInDb.contains(sEmail))
        {
            email.setError("L'email n'existe pas dans la base");
            email.requestFocus();
            return;
        }

        else if(sMotDePasse.isEmpty())
        {
            motDePasse.setError("Entre un mot de passe");
            motDePasse.requestFocus();
            return;
        }

        else
        {
            // Récupérer les emails dans la base
            class GetUser extends AsyncTask<Void, Void, User> {

                @Override
                protected User doInBackground(Void... voids) {

                    // Getting user

                    user = mDb.getAppDatabase().userDao().getUser(sEmail);
                    return user;
                }

                @Override
                protected void onPostExecute(User user) {
                    super.onPostExecute(user);
                }
            }

            // Executer tache asynchrone
            GetUser getUser = new GetUser();
            getUser.execute();

            if(!sMotDePasse.equals(user.getMotDePasse()))
            {
                motDePasse.setError("Le mot de passe est incorrect");
                motDePasse.requestFocus();
                return;
            }
            else
            {
                backward(null);
            }
        }
    }

        public void backward(View view){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
    }

    public void signUp(View view){
        Intent intent = new Intent(this, SubscriptionUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
    }

    //Essayer d'intégrer le code dans le onCreate
    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                //|View.SYSTEM_UI_FLAG_FULLSCREEN // Si on veut cacher la barre android du haut
        );
        super.onStart(); // A quoi ça sert ça ? fonctionne sans
    }
}
