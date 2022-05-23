package Controler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapplication.R;

import Model.db.DatabaseClient;
import Model.db.User;


public class addUser  extends AppCompatActivity
{
    // DATA
    private DatabaseClient mDb;

    //VIEW
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText password;
    private EditText birthDate;
    private Button saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage vue nouvel utilisateur
        setContentView(R.layout.activity_newUser);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues

        name = findViewById(R.id.dataName);
        surname = findViewById(R.id.dataSurname);
        email = findViewById(R.id.dataEmail);
        password = findViewById(R.id.dataPassword);
        birthDate = findViewById(R.id.dataBirthDate);
        saveUser = findViewById(R.id.buttonSaveUser);

        // Associer un événement au bouton save
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewUser();
            }
        });
    }

    private void saveNewUser() {

        // Récupérer les informations contenues dans les vues
        final String sPrenom = name.getText().toString();
        final String sNom = surname.getText().toString();

        // Vérifier les informations fournies par l'utilisateur
        if (sPrenom.isEmpty()) {
            name.setError("Entre ton prénom :)");
            name.requestFocus();
            return;
        }

        if (sNom.isEmpty()) {
            surname.setError("Entre ton nom :)");
            surname.requestFocus();
            return;
        }

        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */
        class SaveTask extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                // creating a task
                User user = new User();
                user.setPrenom(sPrenom);
                user.setNom(sNom);

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
        SaveTask st = new SaveTask();
        st.execute();
}
