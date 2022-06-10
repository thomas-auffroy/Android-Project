package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;
import java.util.ListIterator;

import Model.CategoryListViewAdapter;
import Model.db.DatabaseClient;
import Model.db.GameDao;
import Model.db.QuestionDao;
import Model.db.Reponse;
import Model.db.User;

public class HomePageNoLoginActivity extends AppCompatActivity {

    private Integer questions;
    private DatabaseClient mDb;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_homepage_no_login);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        user = (User) getIntent().getSerializableExtra("USER");

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

    public void play(View view){
        Intent intent = new Intent(this, ListCategoriesActivity.class);
        intent.putExtra(ListCategoriesActivity.USER, user);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)

    }
}
