package Controler.Exercices;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import Model.Exercises.Maths.MultiplicationListViewAdapter;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.User;

public class MultiplicationActivity extends AppCompatActivity {
    public static final String USER = null;
    public static final String TABLE = null;
    public static final String NAME = null;

    private ListView listView;

    private TableMultiplication tableMultiplication;
    private User user;
    private Game game;
    private String name;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_multiplication);

        tableMultiplication = (TableMultiplication) getIntent().getSerializableExtra(TABLE);
        user = (User) getIntent().getSerializableExtra(USER);
        name = (String) getIntent().getSerializableExtra(NAME);

        class GetCategory extends AsyncTask<Void, Void, Game> {

            @Override
            protected Game doInBackground(Void... voids) {
                game = mDb.getAppDatabase().gameDao().getGame(name);
                return game;
            }

            @Override
            protected void onPostExecute(Game game) {

                super.onPostExecute(game);
            }
        }

        listView = findViewById(R.id.generation_listview);

        MultiplicationListViewAdapter adapter = new MultiplicationListViewAdapter(this, tableMultiplication.getMultiplications());
        listView.setAdapter(adapter);
    }
}
