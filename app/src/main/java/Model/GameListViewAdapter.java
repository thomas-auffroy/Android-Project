package Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidapplication.R;

import java.util.List;

import Controler.LoginUser;


/**
 * Created by fbm on 05/03/2018.
 */

public class GameListViewAdapter extends ArrayAdapter<String> {

    private final List<String> values;
    private Activity parentActivity;

    public GameListViewAdapter(Context context, List<String> values, Activity parentActivity) {
        super(context, R.layout.template_list_games, values);
        this.values = values;
        this.parentActivity = parentActivity;
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

                TextView title = view.findViewById(R.id.gameName);

                Intent intent = new Intent(parentActivity, LoginUser.class);
                parentActivity.startActivity(intent);
                parentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)


            }
        });


        //
        return rowView;
    }


}
