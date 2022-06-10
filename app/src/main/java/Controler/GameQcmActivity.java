package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Model.db.DatabaseClient;
import Model.db.Question;
import Model.db.QuestionDao;
import Model.db.Reponse;
import Model.db.User;

public class GameQcmActivity extends AppCompatActivity {

    public static final String USER =  null;
    public static final String GAME =  null;
    private User user;
    private DatabaseClient mDb;

    private TextView question;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_game_qcm);

        question = findViewById(R.id.questionQCM);
        answer1 = findViewById(R.id.answerQCM1);
        answer2 = findViewById(R.id.answerQCM2);
        answer3 = findViewById(R.id.answerQCM3);
        answer4 = findViewById(R.id.answerQCM4);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        System.out.println("lolzedez" + getIntent().getSerializableExtra(USER));
        System.out.println(getIntent().getSerializableExtra(GAME));
       // user = (User) getIntent().getSerializableExtra(USER);


/*
        // Récupérer les jeux dans la base en fonction d'une catégorie
        class GetQuestion extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                String question = mDb.getAppDatabase().questionDao().getQuestionFromIds(1,0);
                return question;
            }

            @Override
            protected void onPostExecute(String foo) {

                System.out.println(foo);

            }
        }


 */
        class GetQuestions extends AsyncTask<Void, Void, List<Question>> {

            @Override
            protected List<Question> doInBackground(Void... voids) {
                List<Question> question = mDb.getAppDatabase().questionDao().getAll();
                return question;
            }

            @Override
            protected void onPostExecute(List<Question> foo) {

                System.out.println(foo.get(0).getQuestion());
                System.out.println(foo.get(0).getGameId());
                System.out.println(foo.get(0).getQuestionId());

            }
        }

        // Executer tache asynchrone
        GetQuestions getQuestion = new GetQuestions();
        getQuestion.execute();

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

    public void backward(View view){
        Intent intent = new Intent(this, ListGamesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(view == null) // Backward utilisé en fin de connection
        {
            intent.putExtra(ListGamesActivity.USER, user);
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
        startActivity(intent);
    }
}
