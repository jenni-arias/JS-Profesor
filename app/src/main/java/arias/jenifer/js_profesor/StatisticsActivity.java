package arias.jenifer.js_profesor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private ListView lst_statistics;
    private Toolbar toolbar;

    private DatabaseReference dbFire;
    private StatisticsAdapter adaptador;
    private ArrayList<String> datos = new ArrayList<>();

    static final String FILENAME_CODE = "Data.txt";

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
                    dbFire.child(level);

                    String complete = dataSnapshot.child("Completado").getValue().toString();

                    String data = "Nivel " + level + ". Fecha: 18/05/2018. Hora: 15:50h." + "\n"
                            + "Ejercicios bien: 0. Ejercicios mal: 0." + "\n"
                            + "Completado: "+ complete;

                    writeCode(data);
                    datos.add(readCode());
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

    private void writeCode(String data){
        try {
            FileOutputStream fout = openFileOutput(FILENAME_CODE, Context.MODE_PRIVATE);
            String line = String.valueOf(data);
            fout.write(line.getBytes());
            fout.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Fichero no encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "No se puede escribir el fichero", Toast.LENGTH_SHORT).show();
        }

    }

    private String readCode(){
        String data = new String();
        try {
            FileInputStream fin = openFileInput(FILENAME_CODE);
            byte[] buffer = new byte[80000];
            int nread = fin.read(buffer);
            if (nread > 0) {
                data = new String(buffer, 0, nread);
                fin.close();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Fichero no encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "No se puede leer el fichero", Toast.LENGTH_SHORT).show();
        }

        return data;
    }

}
