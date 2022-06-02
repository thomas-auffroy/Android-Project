package Model.Exercises.Maths;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidapplication.R;

import java.util.List;

public class MultiplicationListViewAdapter extends ArrayAdapter<Multiplication> {

    private final List<Multiplication> values;

    public MultiplicationListViewAdapter(Context context, List<Multiplication> values) {
        super(context, R.layout.template_calcul, values);
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
        final Multiplication multiplication = values.get(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_calcul, parent, false);

        // Récupération des objets graphiques dans le template
        TextView calcul = rowView.findViewById(R.id.template_calcul);
        final EditText resultat = rowView.findViewById(R.id.template_resultat);

        // Modification de l'objet graphique en fonction de la multiplication
        calcul.setText(multiplication.getOperande1() + " x " + multiplication.getOperande2() + " = ");

        //
        return rowView;
    }


}
