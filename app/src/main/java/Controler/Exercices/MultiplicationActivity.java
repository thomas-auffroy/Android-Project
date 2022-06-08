package Controler.Exercices;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import Model.Exercises.Maths.MultiplicationListViewAdapter;
import Model.Exercises.Maths.TableMultiplication;
import Model.db.User;

public class MultiplicationActivity extends AppCompatActivity {
    public static final String USER = null;
    public static final String TABLE = null;

    private ListView listView;

    private TableMultiplication tableMultiplication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_multiplication);

        tableMultiplication = (TableMultiplication) getIntent().getSerializableExtra(TABLE);
        user = (User) getIntent().getSerializableExtra(USER);

        listView = findViewById(R.id.generation_listview);

        MultiplicationListViewAdapter adapter = new MultiplicationListViewAdapter(this, tableMultiplication.getMultiplications());
        listView.setAdapter(adapter);
    }
}
