package Model;

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

import Controler.LoginUser;
import Controler.PlayActivity;


/**
 * Created by fbm on 05/03/2018.
 */

public class GameListViewAdapter extends ArrayAdapter<String> {

    private final List<String> values;

    public GameListViewAdapter(Context context, List<String> values) {
        super(context, R.layout.template_categorie, values);
        this.values = values;
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

        LinearLayout box = rowView.findViewById(R.id.layoutCategory);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                /*
                TextView title = view.findViewById(R.id.titleCategory);
                System.out.println(title.getText());

                Intent intent = new Intent(view.getContext(), LoginUser.class);
                view.getContext().startActivity(intent);
                */

            }
        });

        //
        return rowView;
    }


}
