package Controler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;

import Model.db.DatabaseClient;
import Model.db.Score;
import Model.db.User;

public class ScoreActivity  extends AppCompatActivity {

    private User user;
    private List<Score> scores;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_score);

        user = (User) getIntent().getSerializableExtra("USER");
        mDb = DatabaseClient.getInstance(getApplicationContext());

        if(user.getEmail().equals("anonymous"))
        {
            scores = Score.scoresAnonyme;
            afficheScores();
        }

        else
        {
            class GetScoreUser extends AsyncTask<Void, Void, List<Score>> {

                @Override
                protected List<Score> doInBackground(Void... voids) {
                    scores = mDb.getAppDatabase().scoreDao().getAllScoreFromUser(user.getEmail());
                    return scores;
                }

                @Override
                protected void onPostExecute(List<Score> user) {
                    afficheScores();
                }
            }

            GetScoreUser getScoreUser = new GetScoreUser();
            getScoreUser.execute();
        }
    }

    private void afficheScores()
    {
        LinearLayout layoutPrincipal = findViewById(R.id.layoutScorePrincipal);

        if(scores != null)
        {
            for(int i = 0; i < scores.size(); i++)
            {

            }
        }

    }
}
