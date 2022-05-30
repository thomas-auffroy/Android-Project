package Model;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Model.db.DatabaseClient;
import Model.db.Game;

public class CategorieTemplate extends AppCompatActivity {

    private List<Game> jeux;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les jeux dans la base en fonction d'une catégorie
        class GetGames extends AsyncTask<Void, Void, List<Game>> {

            @Override
            protected List<Game> doInBackground(Void... voids) {

                // get
                jeux = mDb.getAppDatabase().gameDao().getAllFromCategory("Math");
                System.out.println(jeux);
                return jeux;
            }

            @Override
            protected void onPostExecute(List<Game> jeux) {
                super.onPostExecute(jeux);
            }
        }

        // Executer tache asynchrone
        GetGames getGames = new GetGames();
        getGames.execute();



    }
}
