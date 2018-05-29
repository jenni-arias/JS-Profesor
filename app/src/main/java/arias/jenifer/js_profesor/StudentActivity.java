package arias.jenifer.js_profesor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    private ListView lst_students;
  //  private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        lst_students = (ListView)findViewById(R.id.lst_students);
       // tv_name = (TextView) findViewById(R.id.tv_name);

        //Datos ejemplo
        final Student[] datos = new Student[] {
                new Student("Estudiante 1"),
                new Student("Estudiante 2"),
                new Student("Estudiante 3"),
                new Student("Estudiante 4"),
                new Student("Estudiante 5"),
                new Student("Estudiante 6"),
                new Student("Estudiante 7"),
                new Student("Estudiante 8"),
                new Student("Estudiante 9"),
                new Student("Estudiante 10")
        };

        //Adaptador
        StudentAdapter adaptador = new StudentAdapter(this, datos);
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
