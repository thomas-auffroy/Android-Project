package Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.androidapplication.R;

import java.util.ArrayList;

import Model.db.Score;
import Model.db.User;

public class HomePageNoLoginActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_homepage_no_login);

        user = (User) getIntent().getSerializableExtra("USER");

        if(Score.scoresAnonyme == null)
        {
            Score.scoresAnonyme = new ArrayList<Score>();
        }
    }

    @Override
    protected void onStart() {
        // Permet de cacher la barre de navigation du bas
        getWindow().getDecorView().setSystemUiVisibility(
                 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        super.onStart();
    }

    public void login(View view){
        Intent intent = new Intent(this, LoginUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
    }

    public void play(View view){
        Intent intent = new Intent(this, ListCategoriesActivity.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
    }

    /*
    public void score(View view) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("USER", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
    }
    */


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        ActivityCompat.finishAffinity(this);
    }
}
