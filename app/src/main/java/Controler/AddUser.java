package Controler;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapplication.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.db.DatabaseClient;
import Model.db.User;


public class AddUser extends AppCompatActivity {
    // DATA
    private DatabaseClient mDb;
    private List<String> emailsInDb;

    //VIEW
    private EditText prenom;
    private EditText nom;
    private EditText email;
    private EditText motDePasse;
    private EditText dateNaissance;
    private EditText imageSrc;
    private Button saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage vue nouvel utilisateur
        setContentView(R.layout.test_adduser);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
        emailsInDb = null;

        // Récupérer les vues
        prenom = findViewById(R.id.dataName);
        nom = findViewById(R.id.dataSurname);
        email = findViewById(R.id.dataEmail);
        motDePasse = findViewById(R.id.dataPassword);
        dateNaissance = findViewById(R.id.dataBirthDate);
        saveUser = findViewById(R.id.buttonSaveUser);

        // Associer un événement au bouton save
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewUser();
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

    private void saveNewUser() {

        // Récupérer les informations contenues dans les vues
        final String sPrenom = prenom.getText().toString();
        final String sNom = nom.getText().toString();
        final String sEmail = email.getText().toString();
        final String sMotDePasse = motDePasse.getText().toString();
        final String sDateNaissanceUnchecked = dateNaissance.getText().toString();

        Date sDateNaissanceChecked = null; //Var de type java.sql.Date pour intégration dans DB
        Date now = new Date(System.currentTimeMillis()); //Var de type java.sql.Date pour test dateNaissance < now

        //Expression régulière pour conformité @ mail
        String emailRegx = "^(.+)@(\\S+)$";
        Pattern emailRegxPattern = Pattern.compile(emailRegx);
        Matcher sEmailCheck = emailRegxPattern.matcher(sEmail);

        // Vérifier les informations fournies par l'utilisateur
        if (sPrenom.isEmpty()) {
            prenom.setError("Entre ton prénom");
            prenom.requestFocus();
            return;
        }

        if (sNom.isEmpty()) {
            nom.setError("Entre ton nom");
            nom.requestFocus();
            return;
        }

        if (sMotDePasse.isEmpty()) {
            motDePasse.setError("Entre un mot de passe");
            motDePasse.requestFocus();
            return;
        }

        if (sEmail.isEmpty()) {
            email.setError("Entre une adresse email");
            email.requestFocus();
            return;
        }

        if (!sEmailCheck.matches()) {
            email.setError("Entre un format d'adresse email correct (\"xxx@xxx.com\")");
            email.requestFocus();
            return;
        }

        if (emailsInDb != null && emailsInDb.contains(sEmail))
        {
            email.setError("L'email existe déjà dans la base");
            email.requestFocus();
            return;
        }

        //Test date de naissance
        try {
            sDateNaissanceChecked = Date.valueOf(sDateNaissanceUnchecked);
        } catch (IllegalArgumentException e) {
            dateNaissance.setError("Le format de la date de naissance n'est pas correcte (\"YYYY-MM-JJ\")");
            dateNaissance.requestFocus();
            return;
        }

        if (sDateNaissanceChecked.after(now)) {
            dateNaissance.setError("La date de naissance doit être antérieure à la date du jour");
            dateNaissance.requestFocus();
            return;
        }


        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */

        Date finalSDateNaissanceChecked = sDateNaissanceChecked;

        class SaveUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a user
                User user = new User();
                user.setEmail(sEmail);
                user.setPrenom(sPrenom);
                user.setNom(sNom);
                user.setDateNaissance(finalSDateNaissanceChecked);
                user.setMotDePasse(sMotDePasse);
                user.setSrcImage("");

                // adding to database
                mDb.getAppDatabase().userDao().insert(user);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveUser saveUser = new SaveUser();
        saveUser.execute();
    }
}
