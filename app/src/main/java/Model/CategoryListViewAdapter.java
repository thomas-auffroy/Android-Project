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

import Controler.ListGamesActivity;
import Model.db.User;


/**
 * Created by fbm on 05/03/2018.
 */

public class CategoryListViewAdapter extends ArrayAdapter<String> {

    private final List<String> values;
    private Activity parentActivity;
    private User user;

    public CategoryListViewAdapter(Context context, List<String> values, Activity parentActivity, User user) {
        super(context, R.layout.template_categorie, values);
        this.values = values;
        this.user = user;

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
        final View rowView = inflater.inflate(R.layout.template_categorie, parent, false);


        TextView title = rowView.findViewById(R.id.titleCategory);
        title.setText(category);

        LinearLayout box = rowView.findViewById(R.id.layoutCategory);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                TextView name = view.findViewById(R.id.titleCategory);
                Intent intent = new Intent(parentActivity, ListGamesActivity.class);
                intent.putExtra(ListGamesActivity.CATEGORY, name.getText());
                intent.putExtra(ListGamesActivity.USER, user);
                parentActivity.startActivity(intent);

                parentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Permet une animation de la vue (override le comportement de base)
            }
        });

        //
        return rowView;
    }


}
