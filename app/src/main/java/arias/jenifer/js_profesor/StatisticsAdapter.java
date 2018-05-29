package arias.jenifer.js_profesor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsAdapter extends BaseAdapter {

    private Context context;
    private TextView tv_statistics;
    private ArrayList<String> datos;

    public StatisticsAdapter(Context context, ArrayList<String> datos) {
        this.context = context;
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.statistics_item, parent, false);
        }

        tv_statistics = (TextView) view.findViewById(R.id.tv_statistics);
        tv_statistics.setText(datos.get(position));

        return view;
    }
}
