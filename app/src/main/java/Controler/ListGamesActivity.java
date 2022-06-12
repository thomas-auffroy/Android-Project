package Controler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapplication.R;
import java.util.List;
import Model.GameListViewAdapter;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.User;

public class ListGamesActivity extends AppCompatActivity {

    private User user;
    private List<Game> games;
    private DatabaseClient mDb;

    public static String category;

    private TextView nameCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);
        mDb = DatabaseClient.getInstance(getApplicationContext());
        category = (String) getIntent().getSerializableExtra("CATEGORY");
        user = (User) getIntent().getSerializableExtra("USER");

        System.out.println(category);

        nameCategory = findViewById(R.id.nameCategoryListGames);
        nameCategory.setText(category);

        // Récupérer les jeux dans la base en fonction d'une catégorie
        class GetGames extends AsyncTask<Void, Void, List<Game>> {

            @Override
            protected List<Game> doInBackground(Void... voids) {
                games = mDb.getAppDatabase().gameDao().getAllFromCategory(category);
                return games;
            }

            @Override
            protected void onPostExecute(List<Game> jeux) {
                ListView listView = findViewById(R.id.listViewGames);

                GameListViewAdapter adapter = new GameListViewAdapter(ListGamesActivity.this, games, ListGamesActivity.this, user);
                listView.setAdapter(adapter);
                super.onPostExecute(jeux);
            }
        }

        // Executer tache asynchrone
        GetGames getGames = new GetGames();
        getGames.execute();

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
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Permet une animation de la vue (override le comportement de base)
    }
}
