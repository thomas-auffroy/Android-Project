package Controler;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import Model.db.DatabaseClient;

public class addUser  extends AppCompatActivity {
    // DATA
    private DatabaseClient mDb;

    //VIEW
    private EditText prenom;
    private EditText nom;
    private Button saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage vue nouvel utilisateur
        setContentView(R.layout.activity_newUser);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        prenom = findViewById(R.id.dataPrenom);
        nom = findViewById(R.id.dataNom);
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
        final String sPrenom = prenom.getText().toString();
        final String sNom = nom.getText().toString();

        // Vérifier les informations fournies par l'utilisateur
        if (sPrenom.isEmpty()) {
            prenom.setError("Entre ton prénom :)");
            prenom.requestFocus();
            return;
        }

        if (sNom.isEmpty()) {
            nom.setError("Entre ton nom :)");
            nom.requestFocus();
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
