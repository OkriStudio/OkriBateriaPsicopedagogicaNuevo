package com.example.okribateriapsicopedagogica;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log; // Importar para usar logs
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Perfil extends Fragment {

    private static final String TAG = "PerfilFragment"; // Etiqueta para los logs
    private TextView txtNombre, txtCorreo, txtRut, txtCurso, txtRol;
    private DatabaseReference databaseReference;

    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializa la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar las vistas
        txtNombre = view.findViewById(R.id.txtNombre);
        txtCorreo = view.findViewById(R.id.txtCorreo);
        txtRut = view.findViewById(R.id.txtRut);
        txtCurso = view.findViewById(R.id.txtCurso);
        txtRol = view.findViewById(R.id.txtRol);

        // Obtener el ID del usuario desde SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("MiAppPrefs", getContext().MODE_PRIVATE);
        String idAlumno = prefs.getString("id_alumno", null);
        String idProfe = prefs.getString("id_profe", null);

        Log.d(TAG, "idAlumno: " + idAlumno + ", idProfe: " + idProfe); // Log para ver qué ID se está obteniendo

        if (idAlumno != null) {
            // Cargar datos del alumno
            Log.d(TAG, "Cargando datos de alumno con ID: " + idAlumno);
            loadAlumnoData(idAlumno);
            txtRol.setText("Rol: Alumno");
        } else if (idProfe != null) {
            // Cargar datos del profesor
            Log.d(TAG, "Cargando datos de profesor con ID: " + idProfe);
            loadProfeData(idProfe);
            txtRol.setText("Rol: Profesor");
        } else {
            Log.d(TAG, "No se encontraron IDs de alumno o profesor");
        }

        return view;
    }
    private void loadAlumnoData(String idAlumno) {
        databaseReference.child("alumnos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Datos de alumnos recibidos: " + dataSnapshot.toString());
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Alumnos alumno = snapshot.getValue(Alumnos.class);
                    if (alumno != null && alumno.getId_alumno().equals(idAlumno)) {
                        Log.d(TAG, "Alumno encontrado: " + alumno.getNombre());
                        txtNombre.setText(alumno.getNombre() + " " + alumno.getApellido()); // Agregar apellido
                        txtCorreo.setText(alumno.getCorreo());
                        txtRut.setText("RUT: " + alumno.getRut());
                        txtCurso.setText("Curso: " + alumno.getCurso());
                        found = true;
                        break; // Salir del bucle una vez encontrado
                    }
                }
                if (!found) {
                    Log.d(TAG, "No existe un alumno con el ID: " + idAlumno);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al cargar datos del alumno: " + databaseError.getMessage());
            }
        });
    }
    private void loadProfeData(String idProfe) {
        databaseReference.child("profesores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Datos de profesores recibidos: " + dataSnapshot.toString());
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Profe profe = snapshot.getValue(Profe.class);
                    if (profe != null && profe.getId_profe().equals(idProfe)) {
                        Log.d(TAG, "Profesor encontrado: " + profe.getNombre());
                        txtNombre.setText(profe.getNombre() + " " + profe.getApellido()); // Agregar apellido
                        txtCorreo.setText(profe.getCorreo());
                        txtRut.setText("RUT: " + profe.getRut());
                        txtCurso.setText("Curso: No aplica");
                        found = true;
                        break; // Salir del bucle una vez encontrado
                    }
                }
                if (!found) {
                    Log.d(TAG, "No existe un profesor con el ID: " + idProfe);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error al cargar datos del profesor: " + databaseError.getMessage());
            }
        });
    }
}

