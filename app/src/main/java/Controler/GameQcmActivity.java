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
    private static List<Question> availableQuestions = null;

    private DatabaseClient mDb;

    private TextView nameCategory;

    private TextView numeroQuestionTextView;
    private TextView questionView;
    private Button answer1View;
    private Button answer2View;
    private Button answer3View;
    private Button answer4View;
    private String rightAnswer;
    private Button btnNext;

    private boolean haveAnswered = false;

    private static Integer numeroQuestion = 1;
    private final Integer maxQuestions = 5;
    private static Integer goodAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_game_qcm);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        user = (User) getIntent().getSerializableExtra("USER");
        game = (Game) getIntent().getSerializableExtra("GAME");

        questionView = findViewById(R.id.questionQCM);

        if (availableQuestions == null) {
            getAllQuestionsFromGameId(game.getId());
        } else {
            Question aQuestion = chooseRandomly();
            questionView.setText(aQuestion.getQuestion());
            displayAnswer(aQuestion);
        }

        nameCategory = findViewById(R.id.nameCategoryGameQcm);
        nameCategory.setText(game.getCategorie());

        numeroQuestionTextView = findViewById(R.id.numeroQuestion);
        numeroQuestionTextView.setText("Question N°" + numeroQuestion + " sur " + maxQuestions);

        answer1View = findViewById(R.id.answerQCM1);
        answer2View = findViewById(R.id.answerQCM2);
        answer3View = findViewById(R.id.answerQCM3);
        answer4View = findViewById(R.id.answerQCM4);

        btnNext = findViewById(R.id.btnNextQcm);

        rightAnswer = "";
    }


    public void checkAnswer(View view) {
        btnNext.setAlpha(1);
        Button btnClicked = findViewById(view.getId());

        if (haveAnswered == false) {
            if (btnClicked.getText() == rightAnswer) {
                btnClicked.setTextColor(Color.GREEN);
                goodAnswers += 1;
            } else {
                btnClicked.setTextColor(Color.RED);
            }
        }
        haveAnswered = true;
    }

    public void next(View view) {

        if (!haveAnswered){
            return;
        }

        // Reload activty
        if (availableQuestions.size() > 0) {
            numeroQuestion += 1;
            finish();
            startActivity(getIntent());
        } else {
            Intent intent = new Intent(this, ResultQcmActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("USER", user);
            intent.putExtra("GAME", game);

            intent.putExtra("GOOD_ANSWERS", goodAnswers);
            intent.putExtra("TOTAL_QUESTION", maxQuestions);

            resetStaticVariables();

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
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
        super.onStart();
    }

    @Override
    public void onBackPressed(){
        // Permet de reset les variables lors d'un retour arrière à l'aide du menu de navigation android
        resetStaticVariables();
        super.onBackPressed();
    }

    public static void resetStaticVariables(){
        numeroQuestion = 1;
        goodAnswers = 0;
        availableQuestions = null;
    }

    public void backward(View view) {
        resetStaticVariables();

        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }

    private void getAllQuestionsFromGameId(Integer gameId) {

        class GetQuestions extends AsyncTask<Void, Void, List<Question>> {
            @Override
            protected List<Question> doInBackground(Void... voids) {
                List<Question> questions = mDb.getAppDatabase().questionDao().getAllQuestionsFromGameId(gameId, maxQuestions);
                return questions;
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                availableQuestions = questions;
                Question aQuestion = chooseRandomly();
                questionView.setText(aQuestion.getQuestion());
                displayAnswer(aQuestion);
            }
        }
        GetQuestions getQuestion = new GetQuestions();
        getQuestion.execute();
    }

    private void displayAnswer(Question question) {

        class GetReponses extends AsyncTask<Void, Void, List<Reponse>> {

            @Override
            protected List<Reponse> doInBackground(Void... voids) {
                List<Reponse> reponses = mDb.getAppDatabase().reponseDao().getAllReponsesFromQuestionId(question.getQuestionId());
                return reponses;
            }

            @Override
            protected void onPostExecute(List<Reponse> reponses) {

                ListIterator<Reponse> it = reponses.listIterator();

                while (it.hasNext() && rightAnswer == "") {
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

                int random = (int) Math.floor(Math.random() * (4));
                answer1View.setText(reponses.get(buffer.get(random)).getReponse());
                buffer.remove(random);

                random = (int) Math.floor(Math.random() * (3));
                answer2View.setText(reponses.get(buffer.get(random)).getReponse());
                buffer.remove(random);

                random = (int) Math.floor(Math.random() * (2));
                answer3View.setText(reponses.get(buffer.get(random)).getReponse());
                buffer.remove(random);

                random = (int) Math.floor(Math.random() * (1));
                answer4View.setText(reponses.get(buffer.get(random)).getReponse());
                buffer.remove(random);
            }
        }

        GetReponses getReponses = new GetReponses();
        getReponses.execute();
    }

    private Question chooseRandomly(){
        int random = (int) Math.floor(Math.random()*(availableQuestions.size()));

        Question question = availableQuestions.get(random);
        availableQuestions.remove(random);

        return question;
    }
}
