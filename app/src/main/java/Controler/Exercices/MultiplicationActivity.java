package Controler.Exercices;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.Random;

import Model.Exercises.Maths.MultiplicationListViewAdapter;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.User;

public class MultiplicationActivity extends AppCompatActivity {
    public static final String USER = null;
    public static final String GAME = null;

    private ListView listView;

    private TableMultiplication tableMultiplication;
    private User user;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_multiplication);

        user = (User) getIntent().getSerializableExtra(USER);
        game = (Game) getIntent().getSerializableExtra(GAME);

        Random random = new Random();
        tableMultiplication = new TableMultiplication(random.nextInt(1+10)+1, 10);

        listView = findViewById(R.id.generation_listview);

        MultiplicationListViewAdapter adapter = new MultiplicationListViewAdapter(this, tableMultiplication.getMultiplications());
        listView.setAdapter(adapter);
    }
}
