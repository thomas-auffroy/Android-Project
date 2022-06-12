package Controler;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidapplication.R;
import java.util.List;
import Model.CategoryListViewAdapter;
import Model.db.DatabaseClient;
import Model.db.User;

public class ListCategoriesActivity extends AppCompatActivity {

    private User user;
    private List<String> categories;
    private DatabaseClient mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        mDb = DatabaseClient.getInstance(getApplicationContext());
        user = (User) getIntent().getSerializableExtra("USER");

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

                CategoryListViewAdapter adapter = new CategoryListViewAdapter(ListCategoriesActivity.this, categories, ListCategoriesActivity.this, user);
                listView.setAdapter(adapter);
                super.onPostExecute(jeux);
            }
        }

        // Executer tache asynchrone
        GetCategory getCategory = new GetCategory();
        getCategory.execute();


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
