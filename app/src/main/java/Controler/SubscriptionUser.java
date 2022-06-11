package Controler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.db.DatabaseClient;
import Model.db.User;

public class SubscriptionUser extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private List<String> emailsInDb;
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    //VIEW
    private EditText prenom;
    private EditText nom;
    private EditText email;
    private EditText motDePasse;
    private EditText dateNaissance;
    private Button saveUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        // Récupération du DatabaseClient
        mDb = DatabaseClient.getInstance(getApplicationContext());
        emailsInDb = null;

        // Récupérer les vues
        prenom = findViewById(R.id.dataName);
        nom = findViewById(R.id.dataSurname);
        email = findViewById(R.id.dataEmail);
        motDePasse = findViewById(R.id.dataPassword);
        dateNaissance = findViewById(R.id.dataBirthDate);
        saveUser = findViewById(R.id.btnSubscription);

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

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


    public void chooseDate(View view) {
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                dateNaissance.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        datePickerDialog.show();
    }


    public void subscribe(View view) {

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

        if (emailsInDb != null && emailsInDb.contains(sEmail)) {
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
                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                super.onPostExecute(user);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                Intent intent = new Intent(SubscriptionUser.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(MainActivity.USER, user);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
                startActivity(intent);
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveUser saveUser = new SaveUser();
        saveUser.execute();
    }

    public void backward(View view) {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void tooglePassword(View view){


        // a améliorer

        int inputMode = motDePasse.getInputType();

        //motDePasse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        motDePasse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

        if (inputMode == InputType.TYPE_CLASS_TEXT) {
            motDePasse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (inputMode == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            motDePasse.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        }

    }

    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                //|View.SYSTEM_UI_FLAG_FULLSCREEN // Si on veut cacher la barre android du haut
        );
        super.onStart(); // A quoi ça sert ça ? fonctionne sans
    }

}
