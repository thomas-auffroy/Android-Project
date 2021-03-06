package Controler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.sql.Date;

import java.util.Calendar;

import Model.db.DatabaseClient;
import Model.db.User;

public class ModifyProfilActivity extends AppCompatActivity {
    // DATA
    private DatabaseClient mDb;
    private User user;

    //VIEW
    private EditText dateNaissance;
    private EditText password;
    private EditText adresseMail;
    private TextView fullName;

    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        setContentView(R.layout.activity_profil_modify);
        user = (User) getIntent().getSerializableExtra("USER");


        dateNaissance = findViewById(R.id.dataBirthDate);
        dateNaissance.setText(user.getDateNaissance().toString());

        fullName = findViewById(R.id.dataFullName);
        fullName.setText(user.getPrenom() + " " + user.getNom());

        adresseMail = findViewById(R.id.dataEmail);
        adresseMail.setText(user.getEmail());

        password = findViewById(R.id.dataPassword);
        password.setText(user.getMotDePasse());

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    }

    public void backward(View view){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    public void logout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
        startActivity(intent);
    }

    public void tooglePassword(View view){
        // a am??liorer
        int inputMode = password.getInputType();

        //motDePasse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

        if (inputMode == InputType.TYPE_CLASS_TEXT) {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (inputMode == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        }
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

    public void acceptContent(View view){

        Date dateNaissanceFormat = Date.valueOf(dateNaissance.getText().toString()); // Permet de convertir une string en Date

        class ModifyContent extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {

                user.setDateNaissance(dateNaissanceFormat);
                user.setMotDePasse(password.getText().toString());

                // updating to database
                mDb.getAppDatabase().userDao().update(user);

                return user;
            }


            @Override
            protected void onPostExecute(User user) {
                Intent intent = new Intent(ModifyProfilActivity.this, ProfilActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("USER", user);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom); // Permet une animation de la vue (override le comportement de base)
            }
        }

        ModifyContent foo = new ModifyContent();
        foo.execute();
    }


    //Essayer d'int??grer le code dans le onCreate
    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                //|View.SYSTEM_UI_FLAG_FULLSCREEN // Si on veut cacher la barre android du haut
        );
        super.onStart(); // A quoi ??a sert ??a ? fonctionne sans
    }

}
