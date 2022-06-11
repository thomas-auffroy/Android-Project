package Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import Model.db.DatabaseClient;
import Model.db.Score;
import Model.db.User;

public class MainActivity  extends AppCompatActivity {
    public static final String USER = null;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra(USER);

        if(user == null)
        {
            user = new User();
            user.setEmail("anonymous");
        }

        if(Score.scoresAnonyme != null)
        {
            Score score = Score.scoresAnonyme.get(0);

            System.out.println("Medaille : "+score.getMedaille());
        }

        if(user.getEmail() == "anonymous")
        {
            Intent intent = new Intent(MainActivity.this, HomePageNoLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("USER", user);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
            startActivity(intent);
        }

        if(user.getEmail() != "anonymous")
        {
            Intent intent = new Intent(MainActivity.this, HomePageLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(HomePageLoginActivity.USER, user);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
            startActivity(intent);
        }

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

    /*
    public void login(View view){
        Intent intent = new Intent(this, LoginUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
    }

    public void play(View view){
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)

    }
    */


}
