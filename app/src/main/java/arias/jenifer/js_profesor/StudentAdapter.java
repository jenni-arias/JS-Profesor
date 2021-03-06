package arias.jenifer.js_profesor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {

    private Context context;
    private ArrayList<Student> datos;

    public StudentAdapter(Context context, ArrayList<Student> datos) {
        super(context, R.layout.student_item, datos);
        this.context = context;
        this.datos = datos;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.student_item, parent, false);
        }

        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(datos.get(position).getName());

        return view;
    }
}
