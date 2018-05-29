package arias.jenifer.js_profesor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private ListView lst_statistics;
    private Toolbar toolbar;
    private StatisticsAdapter adaptador;
    private ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //Configuración actionbar - toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name = getIntent().getStringExtra("Estudiante");
        getSupportActionBar().setTitle(name);


        /*
        datos.add("Nivel 1. Fecha: 18/05/2018. Hora: 15:50h." + "\n"
                + "Ejercicios bien: 0. Ejercicios mal: 0." + "\n"
                + "Completado: NO");
        datos.add("Nivel 1. Fecha: 18/05/2018. Hora: 15:50h." + "\n"
                + "Ejercicios bien: 1. Ejercicios mal: 0." + "\n"
                + "Completado: NO"); */

        DatabaseReference db =
                FirebaseDatabase.getInstance().getReference()
                        .child("Jenifer Arias")
                        .child("1");

        db.child("Completado").setValue("NO");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Completado")) {
                    Log.i("JENN", dataSnapshot.child("Completado").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Adaptador
        adaptador = new arias.jenifer.js_profesor.StatisticsAdapter(this, datos);
        lst_statistics = (ListView) findViewById(R.id.lst_statistics);
        lst_statistics.setAdapter(adaptador);

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
