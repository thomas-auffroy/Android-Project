package Controler;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;

import Model.CategoryListViewAdapter;
import Model.db.DatabaseClient;
import Model.db.Game;

public class PlayActivity extends AppCompatActivity {

    private List<String> categories;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mDb = DatabaseClient.getInstance(getApplicationContext());


        // Récupérer les jeux dans la base en fonction d'une catégorie
        class GetCategory extends AsyncTask<Void, Void, List<String>> {

            @Override
            protected List<String> doInBackground(Void... voids) {
                categories = mDb.getAppDatabase().gameDao().getAllCategory();
                return categories;
            }

            @Override
            protected void onPostExecute(List<String> jeux) {
                ListView listView = findViewById(R.id.listViewCategories);

                CategoryListViewAdapter adapter = new CategoryListViewAdapter(PlayActivity.this, categories);
                listView.setAdapter(adapter);
                super.onPostExecute(jeux);
            }
        }

        // Executer tache asynchrone
        GetCategory getCategory = new GetCategory();
        getCategory.execute();


    }
}
