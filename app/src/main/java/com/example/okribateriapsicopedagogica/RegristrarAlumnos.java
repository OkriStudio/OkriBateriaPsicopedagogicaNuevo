package com.example.okribateriapsicopedagogica;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.okribateriapsicopedagogica.Alumnos;
import com.example.okribateriapsicopedagogica.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegristrarAlumnos extends Fragment {

    private static final String TAG = "RegristrarAlumnos";

    private EditText editTextName;
    private EditText editTextLastname;
    private EditText editTextRut;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Spinner spinnerCourse;
    private Button btnRegister;

    private DatabaseReference databaseReference;

    public RegristrarAlumnos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("alumnos"); // Inicializa la referencia a la base de datos
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regristrar_alumnos, container, false);

        // Inicializa los elementos de la vista
        editTextName = view.findViewById(R.id.editTextName);
        editTextLastname = view.findViewById(R.id.editTextLastname);
        editTextRut = view.findViewById(R.id.editTextRut);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        spinnerCourse = view.findViewById(R.id.spinnerCourse);
        btnRegister = view.findViewById(R.id.btn_register);

        // Crear un arreglo de cursos
        String[] cursos = {"1A", "1B", "1C", "2A", "2B", "2C", "3A", "3B", "3C",
                "4A", "4B", "4C", "5A", "5B", "5C", "6A", "6B", "6C",
                "7A", "7B", "7C", "8A", "8B", "8C"};

        // Configurar el Spinner con un ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, cursos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAlumno();
            }
        });

        return view;
    }

    private void registerAlumno() {
        String nombre = editTextName.getText().toString().trim();
        String apellido = editTextLastname.getText().toString().trim();
        String rut = editTextRut.getText().toString().trim();
        String correo = editTextEmail.getText().toString().trim();
        String contraseña = editTextPassword.getText().toString().trim();
        String curso = spinnerCourse.getSelectedItem().toString(); // Obtener el curso seleccionado

        // Validar los campos
        if (nombre.isEmpty() || apellido.isEmpty() || rut.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo objeto alumno
        String idAlumno = databaseReference.push().getKey(); // Generar una nueva clave para el alumno
        Alumnos nuevoAlumno = new Alumnos(idAlumno, nombre, apellido, correo, curso, rut, contraseña, null); // Cambiado a null para nivel_bateria

        // Guardar el alumno en la base de datos
        databaseReference.child(idAlumno).setValue(nuevoAlumno)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Alumno registrado con éxito.", Toast.LENGTH_SHORT).show();
                        // Limpiar los campos después de registrar
                        clearFields();
                    } else {
                        Log.e(TAG, "Error al registrar el alumno: ", task.getException());
                        Toast.makeText(getContext(), "Error al registrar el alumno. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        editTextName.setText("");
        editTextLastname.setText("");
        editTextRut.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        spinnerCourse.setSelection(0); // Seleccionar la primera opción del spinner
    }
}
