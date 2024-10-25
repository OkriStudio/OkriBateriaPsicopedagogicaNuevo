package com.example.okribateriapsicopedagogica;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class RevisionAlumnos extends Fragment {

    private EditText searchEditText;
    private Button searchButton;
    private TableLayout tableLayout;
    private DatabaseReference databaseReference;
    private List<Alumnos> alumnoList; // Lista de alumnos

    public RevisionAlumnos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("alumnos");
        alumnoList = new ArrayList<>(); // Inicializa la lista de alumnos
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revision_alumnos, container, false);
        searchEditText = view.findViewById(R.id.searchEditText);
        searchButton = view.findViewById(R.id.searchButton);
        tableLayout = view.findViewById(R.id.tableLayout);

        // Cargar los datos de alumnos al iniciar el fragment
        loadAlumnosData();

        // Configurar el botón de búsqueda
        searchButton.setOnClickListener(v -> searchAlumnos());

        return view;
    }

    private void loadAlumnosData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alumnoList.clear(); // Limpiar la lista anterior
                tableLayout.removeViews(1, tableLayout.getChildCount() - 1); // Limpiar filas anteriores

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Alumnos alumno = snapshot.getValue(Alumnos.class);
                    if (alumno != null) {
                        alumnoList.add(alumno);
                        addAlumnoToTable(alumno);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error al cargar los datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAlumnoToTable(Alumnos alumno) {
        TableRow tableRow = new TableRow(getContext());

        TextView nombreTextView = new TextView(getContext());
        nombreTextView.setText(alumno.getNombre());
        nombreTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        TextView apellidoTextView = new TextView(getContext());
        apellidoTextView.setText(alumno.getApellido());
        apellidoTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        TextView rutTextView = new TextView(getContext());
        rutTextView.setText(alumno.getRut());
        rutTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        TextView correoTextView = new TextView(getContext());
        correoTextView.setText(alumno.getCorreo());
        correoTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        TextView cursoTextView = new TextView(getContext());
        cursoTextView.setText(alumno.getCurso());
        cursoTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        tableRow.addView(nombreTextView);
        tableRow.addView(apellidoTextView);
        tableRow.addView(rutTextView);
        tableRow.addView(correoTextView);
        tableRow.addView(cursoTextView);

        tableLayout.addView(tableRow);
    }

    private void searchAlumnos() {
        String searchQuery = searchEditText.getText().toString().trim().toLowerCase();
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1); // Limpiar filas anteriores

        for (Alumnos alumno : alumnoList) {
            if (alumno.getNombre().toLowerCase().contains(searchQuery) ||
                    alumno.getRut().toLowerCase().contains(searchQuery) ||
                    alumno.getCorreo().toLowerCase().contains(searchQuery)) {
                addAlumnoToTable(alumno);
            }
        }
    }
}
