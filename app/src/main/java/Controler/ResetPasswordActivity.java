package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapplication.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.db.DatabaseClient;
import Model.db.User;

public class ResetPasswordActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    private EditText newPassword;
    private EditText confirmPassword;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        newPassword = findViewById(R.id.dataNewPassword);
        confirmPassword = findViewById(R.id.confirmPasswordText);
        email = findViewById(R.id.dataEmailResetPassword);
    }


    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                //|View.SYSTEM_UI_FLAG_FULLSCREEN // Si on veut cacher la barre android du haut
        );
        super.onStart();
    }

    public void backward(View view) {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void reset(View view){

        //Expression régulière pour conformité @ mail
        String emailRegx = "^(.+)@(\\S+)$";
        Pattern emailRegxPattern = Pattern.compile(emailRegx);
        Matcher emailCheck = emailRegxPattern.matcher(email.getText());

        if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())){
            Toast.makeText(this, "Mot de passe différent", Toast.LENGTH_LONG).show();
            return;
        } else if (newPassword.getText().toString().isEmpty()){
            newPassword.setError("Entrez un mot de passe non vide");
            newPassword.requestFocus();
            return;
        } else {
            if (!emailCheck.matches() || email.getText().toString().isEmpty()){
                email.setError("Entrez un format d'adresse email valide (\"xxx@xxx.com\")");
                email.requestFocus();
                return;
            }
            class GetUser extends AsyncTask<Void, Void, User> {

                @Override
                protected User doInBackground(Void... voids) {
                    User user = mDb.getAppDatabase().userDao().getUser(email.getText().toString());
                    return user;
                }

                @Override
                protected void onPostExecute(User user) {

                    if (user == null){
                        Toast.makeText(ResetPasswordActivity.this, "L'email n'est pas dans notre base de donnée", Toast.LENGTH_LONG).show();
                        return;
                    }

                    class UpdatePassword extends AsyncTask<Void, Void, User> {

                        @Override
                        protected User doInBackground(Void... voids) {
                            user.setMotDePasse(newPassword.getText().toString());
                            mDb.getAppDatabase().userDao().update(user);

                            return user;
                        }

                        @Override
                        protected void onPostExecute(User user) {

                            Intent intent = new Intent(ResetPasswordActivity.this, LoginUser.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom); // Permet une animation de la vue (override le comportement de base)
                        }
                    }

                    UpdatePassword updatePassword = new UpdatePassword();
                    updatePassword.execute();
                }
            }

            // Executer tache asynchrone User
            GetUser getUser = new GetUser();
            getUser.execute();
        }
    }


}
