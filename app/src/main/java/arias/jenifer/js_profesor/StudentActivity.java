package arias.jenifer.js_profesor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private ListView lst_students;
    private DatabaseReference dbFire;
    final ArrayList<Student> datos = new ArrayList<>();
    StudentAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        lst_students = (ListView)findViewById(R.id.lst_students);

        dbFire = FirebaseDatabase.getInstance().getReference();

        dbFire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null) {
                    Log.i("JENN", dataSnapshot.getKey());
                    Student student = new Student(dataSnapshot.getKey());
                    datos.add(student);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                datos.remove(dataSnapshot.getKey());
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Adaptador
        adaptador = new StudentAdapter(this, datos);
        lst_students.setAdapter(adaptador);

        lst_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String student_name =((Student)parent.getItemAtPosition(position)).getName();
                Toast.makeText(StudentActivity.this, student_name + " seleccionado.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StudentActivity.this, StatisticsActivity.class);
                intent.putExtra("Estudiante", student_name);
                startActivity(intent);
            }
        });
    }
}
