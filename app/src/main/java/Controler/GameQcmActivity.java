package Controler;

import android.content.Intent;
import android.graphics.Color;
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
import Model.db.Game;
import Model.db.Question;
import Model.db.QuestionDao;
import Model.db.Reponse;
import Model.db.User;

public class GameQcmActivity extends AppCompatActivity {

    private User user;
    private Game game;

    private DatabaseClient mDb;

    private TextView questionView;
    private Button answer1View;
    private Button answer2View;
    private Button answer3View;
    private Button answer4View;
    private String rightAnswer;

    private boolean haveAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_game_qcm);

        questionView = findViewById(R.id.questionQCM);
        answer1View = findViewById(R.id.answerQCM1);
        answer2View = findViewById(R.id.answerQCM2);
        answer3View = findViewById(R.id.answerQCM3);
        answer4View = findViewById(R.id.answerQCM4);

        rightAnswer = "";

        mDb = DatabaseClient.getInstance(getApplicationContext());

        user = (User) getIntent().getSerializableExtra("USER");
        game = (Game) getIntent().getSerializableExtra("GAME");

        class GetNombreQuestions extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                Integer nombre = mDb.getAppDatabase().questionDao().getNombreQuestions(game.getId());
                return nombre;
            }

            @Override
            protected void onPostExecute(Integer nombreQuestions) {
                class GetQuestion extends AsyncTask<Void, Void, Question> {

                    @Override
                    protected Question doInBackground(Void... voids) {
                        int random_int = (int) Math.floor(Math.random()*(nombreQuestions));

                        Question question = mDb.getAppDatabase().questionDao().getQuestionFromIds(game.getId(), random_int);
                        return question;
                    }

                    @Override
                    protected void onPostExecute(Question question) {
                        questionView.setText(question.getQuestion());

                        class GetReponses extends AsyncTask<Void, Void, List<Reponse>> {

                            @Override
                            protected List<Reponse> doInBackground(Void... voids) {
                                List<Reponse> reponses = mDb.getAppDatabase().reponseDao().getAllReponsesFromQuestionId(question.getQuestionId());
                                return reponses;
                            }

                            @Override
                            protected void onPostExecute(List<Reponse> reponses) {

                                ListIterator<Reponse> it = reponses.listIterator();

                                while (it.hasNext() && rightAnswer == ""){
                                    Reponse buffer = it.next();
                                    if (buffer.getEstVrai() == 1) {
                                        rightAnswer = buffer.getReponse();
                                    }
                                }

                                // Position aléatoirement les réponses
                                ArrayList<Integer> buffer = new ArrayList<>();
                                buffer.add(0);
                                buffer.add(1);
                                buffer.add(2);
                                buffer.add(3);

                                int random = (int) Math.floor(Math.random()*(3));
                                buffer.remove(random);
                                answer1View.setText(reponses.get(random).getReponse());


                                random = (int) Math.floor(Math.random()*(2));
                                buffer.remove(random);
                                answer2View.setText(reponses.get(random).getReponse());

                                random = (int) Math.floor(Math.random()*(1));
                                buffer.remove(random);
                                answer3View.setText(reponses.get(random).getReponse());

                                random = (int) Math.floor(Math.random()*(0));
                                buffer.remove(random);
                                answer4View.setText(reponses.get(random).getReponse());
                            }
                        }

                        GetReponses getReponses = new GetReponses();
                        getReponses.execute();
                    }
                }

                GetQuestion getQuestion = new GetQuestion();
                getQuestion.execute();
            }
        }
        GetNombreQuestions getNombreQuestions = new GetNombreQuestions();
        getNombreQuestions.execute();
    }


    public void checkAnswer(View view){
        Button btnClicked = findViewById(view.getId());

        if (haveAnswered == false){
            if (btnClicked.getText() == rightAnswer){
                btnClicked.setTextColor(Color.GREEN);
            } else {
                btnClicked.setTextColor(Color.RED);
            }
        }
        haveAnswered = true;
    }

    public void playAgain(View view){

        // Reload activty
        finish();
        startActivity(getIntent());
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
