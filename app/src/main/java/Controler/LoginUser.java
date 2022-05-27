package Controler;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

public class LoginUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connection);
    }

    public void backward(View view){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

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
