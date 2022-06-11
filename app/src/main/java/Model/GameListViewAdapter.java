package Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.R;

import java.util.List;

import Controler.Exercices.MathActivity;
import Controler.GameQcmActivity;
import Controler.ListCategoriesActivity;
import Controler.ProfilActivity;
import Model.db.DatabaseClient;
import Model.db.Game;
import Model.db.User;


/**
 * Created by fbm on 05/03/2018.
 */

public class GameListViewAdapter extends ArrayAdapter<Game> {

    private final List<Game> values;
    private Game game;
    private Activity parentActivity;
    private User user;
    private DatabaseClient mDb;

    public GameListViewAdapter(Context context, List<Game> values, Activity parentActivity, User user) {
        super(context, R.layout.template_list_games, values);
        this.values = values;
        this.parentActivity = parentActivity;
        this.user = user;
        this.mDb = DatabaseClient.getInstance(context.getApplicationContext());
    }

    /**
     * Remplit une ligne de la listView avec les informations de la multiplication associée
     *v
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Game gameToPrint = values.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_list_games, parent, false);


        TextView title = rowView.findViewById(R.id.gameName);
        title.setText(gameToPrint.getName());


        LinearLayout box = rowView.findViewById(R.id.layoutGame);
        box.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){

                TextView titleGameName = view.findViewById(R.id.gameName);
                String gameName = titleGameName.getText().toString();

                class GetGame extends AsyncTask<Void, Void, Game> {
                    @Override
                    protected Game doInBackground(Void... voids) {
                        game = mDb.getAppDatabase().gameDao().getGame(gameName);
                        return game;
                    }

                    @Override
                    protected void onPostExecute(Game jeu) {

                        Intent intent;

                        switch(game.getTypeJeu())
                        {
                            case "maths":
                                intent = new Intent(parentActivity, MathActivity.class);
                                //intent.putExtra(MathActivity.USER, user);
                                intent.putExtra("USER", user);
                                intent.putExtra("GAME", game);
                                break;
                            case "qcm":
                                intent = new Intent(parentActivity, GameQcmActivity.class);
                                intent.putExtra("USER", user);
                                intent.putExtra("GAME", game);
                                break;
                            default :
                                intent = new Intent(view.getContext(), MathActivity.class);
                                break;
                        }
                        parentActivity.startActivity(intent);
                        parentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)

                    }
                }
                GetGame getGame = new GetGame();
                getGame.execute();
            }
        });


        //
        return rowView;
    }


}
