package com.example.okribateriapsicopedagogica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Baterias extends Fragment {

    private Spinner spinnerNivelBateria;
    private TableLayout tableAlumnos;
    private DatabaseReference database;

    public Baterias() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_baterias, container, false);

        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance().getReference("alumnos");

        // Inicializar vistas
        spinnerNivelBateria = view.findViewById(R.id.spinnerNivelBateria);
        tableAlumnos = view.findViewById(R.id.tableAlumnos);

        // Agregar encabezados a la tabla
        agregarEncabezados();

        // Configurar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.niveles_bateria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNivelBateria.setAdapter(adapter);

        // Listener para el Spinner
        spinnerNivelBateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Recuperar nivel de batería seleccionado
                String nivelSeleccionado = parent.getItemAtPosition(position).toString();
                cargarAlumnosPorNivel(Integer.parseInt(nivelSeleccionado));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        return view;
    }

    // Método para agregar encabezados a la tabla
    private void agregarEncabezados() {
        TableRow encabezadoRow = new TableRow(getContext());
        encabezadoRow.setBackgroundColor(0xFFBDC3C7); // Color del fondo
        encabezadoRow.setPadding(8, 8, 8, 8); // Padding para el encabezado

        String[] encabezados = {"Nombre", "RUT", "Curso", "Puntuación", "Rendimiento"};
        for (String encabezado : encabezados) {
            TextView textView = new TextView(getContext());
            textView.setText(encabezado);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            textView.setTextSize(14); // Tamaño de texto
            textView.setTextColor(0xFFFFFFFF); // Color del texto
            textView.setPadding(8, 8, 8, 8); // Padding
            encabezadoRow.addView(textView);
        }

        tableAlumnos.addView(encabezadoRow); // Agregar encabezado a la tabla
    }

    private void cargarAlumnosPorNivel(int nivelBateria) {
        tableAlumnos.removeViews(1, tableAlumnos.getChildCount() - 1); // Limpiar la tabla antes de agregar nuevos datos

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                for (DataSnapshot alumnoSnapshot : dataSnapshot.getChildren()) {
                    Alumnos alumno = alumnoSnapshot.getValue(Alumnos.class);
                    if (alumno != null && alumno.getNivel_bateria() != null && alumno.getNivel_bateria() == nivelBateria) {
                        // Agregar fila a la tabla
                        TableRow row = new TableRow(getContext());
                        row.setPadding(8, 8, 8, 8);

                        TextView nombreView = new TextView(getContext());
                        nombreView.setText(alumno.getNombre() + " " + alumno.getApellido());
                        nombreView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        TextView rutView = new TextView(getContext());
                        rutView.setText(alumno.getRut());
                        rutView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        TextView cursoView = new TextView(getContext());
                        cursoView.setText(alumno.getCurso());
                        cursoView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        TextView puntuacionView = new TextView(getContext());
                        puntuacionView.setText(alumno.getNivel_bateria().toString());
                        puntuacionView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        TextView rendimientoView = new TextView(getContext());
                        rendimientoView.setText(obtenerRendimiento(alumno.getNivel_bateria()));
                        rendimientoView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        row.addView(nombreView);
                        row.addView(rutView);
                        row.addView(cursoView);
                        row.addView(puntuacionView);
                        row.addView(rendimientoView);
                        tableAlumnos.addView(row);

                        found = true;
                    }
                }
                if (!found) {
                    Toast.makeText(getContext(), "No se encontraron alumnos con nivel de batería " + nivelBateria, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error al cargar los datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String obtenerRendimiento(int puntuacion) {
        if (puntuacion >= 8) {
            return "Alto";
        } else if (puntuacion >= 4) {
            return "Medio";
        } else {
            return "Bajo";
        }
    }
}
