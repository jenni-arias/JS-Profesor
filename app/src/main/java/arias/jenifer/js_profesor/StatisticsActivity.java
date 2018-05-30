package arias.jenifer.js_profesor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private ListView lst_statistics;
    private Toolbar toolbar;
    private DatabaseReference dbFire;
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

        dbFire = FirebaseDatabase.getInstance().getReference().child(name);

        dbFire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null) {
                    Log.i("JENN1", dataSnapshot.getKey());
                    String level = dataSnapshot.getKey();
                    datos.add("Nivel "+ level);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Adaptador
        adaptador = new StatisticsAdapter(this, datos);
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
