package Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidapplication.R;

import java.util.List;

import Controler.Exercices.MathActivity;
import Controler.ProfilActivity;
import Model.db.User;


/**
 * Created by fbm on 05/03/2018.
 */

public class GameListViewAdapter extends ArrayAdapter<String> {

    private final List<String> values;
    private Activity parentActivity;
    private User user;

    public GameListViewAdapter(Context context, List<String> values, Activity parentActivity, User user) {
        super(context, R.layout.template_list_games, values);
        this.values = values;
        this.parentActivity = parentActivity;
        this.user = user;
    }

    /**
     * Remplit une ligne de la listView avec les informations de la multiplication associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String category = values.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_list_games, parent, false);


        TextView title = rowView.findViewById(R.id.gameName);
        title.setText(category);


        LinearLayout box = rowView.findViewById(R.id.layoutGame);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                TextView title = view.findViewById(R.id.titleCategory);
                TextView titleGameName = view.findViewById(R.id.gameName);
                String category = title.getText().toString();
                String gameName = titleGameName.getText().toString();

                Intent intent;

                switch(category)
                {
                    case "Mathématiques":
                        intent = new Intent(view.getContext(), MathActivity.class);
                        intent.putExtra(MathActivity.GAMENAME, gameName);
                        intent.putExtra(MathActivity.USER, user);
                        break;
                    case "Francais" :
                        intent = new Intent(view.getContext(), ProfilActivity.class);
                        intent.putExtra(MathActivity.GAMENAME, gameName);
                        intent.putExtra(MathActivity.USER, user);
                        break;
                    default :
                        intent = new Intent(view.getContext(), MathActivity.class);
                        break;
                }

                view.getContext().startActivity(intent);
            }
        });


        //
        return rowView;
    }


}
