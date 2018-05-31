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

    static String FILENAME_CODE = "";

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

        dbFire = FirebaseDatabase.getInstance().getReference().child(name);
        FILENAME_CODE = name + ".txt";

        dbFire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null) {
                    String level = dataSnapshot.getKey();
                    dbFire.child(level);

                    String dato = readFile();
                    if(!dato.isEmpty()) {
                        dato = dato + "&"; }

                    String fecha = dataSnapshot.child("Fecha").getValue().toString();
                    String hora = dataSnapshot.child("Hora").getValue().toString();
                    String ej_bien = dataSnapshot.child("Ejercicios bien").getValue().toString();
                    String ej_mal = dataSnapshot.child("Ejercicios mal").getValue().toString();
                    String complete = dataSnapshot.child("Completado").getValue().toString();

                    String data = "Nivel " + level + ". Fecha: " + fecha + ". Hora: " + hora + "h." + "\n"
                            + "Ejercicios bien: " + ej_bien + ". Ejercicios mal: "+ ej_mal + "." + "\n"
                            + "Completado: "+ complete;

                    dato = dato.concat(data);
                    writeFile(dato);
                    String[] split = dato.split("&");

                    for(int i = 0; i < split.length; i++) {
                        if(!datos.contains(split[i])) {
                            datos.add(split[i]);
                        }
                    }
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

    private void writeFile(String dato){
        try {
            FileOutputStream fout = openFileOutput(FILENAME_CODE, Context.MODE_PRIVATE);
            String line = String.valueOf(dato);
            fout.write(line.getBytes());
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readFile(){
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}
