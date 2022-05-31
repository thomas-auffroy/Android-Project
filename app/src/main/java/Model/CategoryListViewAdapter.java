package Model;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapplication.R;

import java.util.List;


/**
 * Created by fbm on 05/03/2018.
 */

public class CategoryListViewAdapter extends ArrayAdapter<String> {

    private final List<String> values;

    public CategoryListViewAdapter(Context context, List<String> values) {
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

        // Récupération de la multiplication
        final String category = values.get(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_categorie, parent, false);

        // Récupération des objets graphiques dans le template
        TextView title = rowView.findViewById(R.id.titleCategory);

        // Modification de l'objet graphique en fonction de la multiplication
        title.setText(category);

        //
        return rowView;
    }


}
