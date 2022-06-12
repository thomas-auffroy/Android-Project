package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.db.DatabaseClient;
import Model.db.User;

public class LoginUser extends AppCompatActivity {

    // DATA
    private DatabaseClient mDb;
    private String passwordDb;
    private User user;

    //VIEW
    private EditText email;
    private EditText motDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connection);

        // Initialisation attributs
        user = new User();
        passwordDb = null;
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        email = findViewById(R.id.dataEmailConnection);
        motDePasse = findViewById(R.id.dataPassword);
    }

    public void connect(View view) {
        final String sEmail = email.getText().toString();
        final String sMotDePasse = motDePasse.getText().toString();

        // Test entrées utilisateur
        //Expression régulière pour conformité @ mail
        String emailRegx = "^(.+)@(\\S+)$";
        Pattern emailRegxPattern = Pattern.compile(emailRegx);
        Matcher emailCheck = emailRegxPattern.matcher(email.getText());

        if (!emailCheck.matches() || sEmail.isEmpty()){
            email.setError("Entrez un format d'adresse email valide (\"xxx@xxx.com\")");
            email.requestFocus();
            return;
        }
        else if(sMotDePasse.isEmpty())
        {
            motDePasse.setError("Entre un mot de passe");
            motDePasse.requestFocus();
            return;
        }

        else {
            //Tâche asyncrhone -> Obtenir le MdP du User
            class GetPassword extends AsyncTask<Void, Void, String> {

                @Override
                protected String doInBackground(Void... voids) {

                    // Getting user

                    passwordDb = mDb.getAppDatabase().userDao().getMotDePasse(sEmail);
                    return passwordDb;
                }

                @Override
                protected void onPostExecute(String password) {
                    // Test existence email
                    if (passwordDb == null) {
                        email.setError("L'email n'existe pas dans la base");
                        email.requestFocus();
                        return;
                    }

                    // Test concordance MdP
                    else if (!sMotDePasse.equals(passwordDb)) {
                        motDePasse.setError("Le mot de passe est incorrect");
                        motDePasse.requestFocus();
                        return;
                    }

                    // MdP et Email concordent -> On récupère le User
                    else
                    {
                        // //Tâche asyncrhone -> Récupérer le User
                        class GetUser extends AsyncTask<Void, Void, User> {

                            @Override
                            protected User doInBackground(Void... voids) {
                                user = mDb.getAppDatabase().userDao().getUser(sEmail);
                                return user;
                            }

                            @Override
                            protected void onPostExecute(User user) {
                                Intent intent = new Intent(LoginUser.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("USER", user);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
                                startActivity(intent);
                            }
                        }

                        // Executer tache asynchrone User
                        GetUser getUser = new GetUser();
                        getUser.execute();
                    }
                }
            }
            // Executer tache asynchrone Password
            GetPassword getPassword = new GetPassword();
            getPassword.execute();
        }
    }

    public void backward(View view){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void forgotPassword(View view){
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top); // Permet une animation de la vue (override le comportement de base)
    }

    public void signUp(View view){
        Intent intent = new Intent(this, SubscriptionUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
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
