package Model;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;

import Model.db.DatabaseClient;
import Model.db.Game;

public class CategorieTemplate extends AppCompatActivity {

    private List<String> categories;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les jeux dans la base en fonction d'une catégorie
        class GetCategory extends AsyncTask<Void, Void, List<String>> {

            @Override
            protected List<String> doInBackground(Void... voids) {

                // get
                categories = mDb.getAppDatabase().gameDao().getAllCategory();
                System.out.println(categories);
                return categories;
            }

            @Override
            protected void onPostExecute(List<String> jeux) {
                super.onPostExecute(jeux);
            }
        }

        // Executer tache asynchrone
        GetCategory getCategory = new GetCategory();
        getCategory.execute();

        ListView listView = findViewById(R.id.listViewCategories);

        CategoryListViewAdapter adapter = new CategoryListViewAdapter(this, categories);
        listView.setAdapter(adapter);




    }


}
