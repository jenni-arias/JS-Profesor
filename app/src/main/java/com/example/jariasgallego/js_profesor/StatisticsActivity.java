package com.example.jariasgallego.js_profesor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tv_statistics;
    private Toolbar toolbar_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tv_statistics = (TextView) findViewById(R.id.tv_statistics);

        String name = getIntent().getStringExtra("Estudiante");

        //Configuración actionbar - toolbar
        toolbar_student = (Toolbar) findViewById(R.id.toolbar_student);
        setSupportActionBar(toolbar_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        tv_statistics.setText("Nivel 1. Fecha: 18/05/2018. Hora: 15:50h." + "\n"
                                + "Ejercicios bien: 0. Ejercicios mal: 0." + "\n"
                                + "Completado: NO");

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
