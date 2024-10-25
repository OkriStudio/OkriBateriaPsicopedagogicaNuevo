package com.example.okribateriapsicopedagogica;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HacerFormulario extends Fragment {

    private RadioGroup radioGroupHistoria, radioGroupRazonamiento, radioGroupMatematicas, radioGroupLenguaje;
    private Button buttonEnviar;
    private DatabaseReference database;
    private SharedPreferences prefs;
    private String idAlumno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para el fragmento
        View view = inflater.inflate(R.layout.fragment_hacer_formulario, container, false);

        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance().getReference("alumnos");

        // Inicializar SharedPreferences para obtener el ID del alumno
        prefs = requireActivity().getSharedPreferences("MiAppPrefs", getContext().MODE_PRIVATE);
        final String idAlumno = prefs.getString("id_alumno", null); // Recoger el ID del alumno desde SharedPreferences

        if (idAlumno == null) {
            Toast.makeText(getContext(), "ID de alumno no encontrado", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
            return null;
        }

        // Verificar si el formulario ya fue completado
        boolean formularioCompletado = prefs.getBoolean("formulario_completado_" + idAlumno, false);
        if (formularioCompletado) {
            Toast.makeText(getContext(), "Ya has completado el formulario.", Toast.LENGTH_SHORT).show();
            cargarFragmentoInicio();  // Redirigir al fragmento de inicio si ya fue completado
            return null;
        }

        // Inicializar vistas
        radioGroupHistoria = view.findViewById(R.id.radioGroupHistoria);
        radioGroupRazonamiento = view.findViewById(R.id.radioGroupRazonamiento);
        radioGroupMatematicas = view.findViewById(R.id.radioGroupMatematicas);
        radioGroupLenguaje = view.findViewById(R.id.radioGroupLenguaje);
        buttonEnviar = view.findViewById(R.id.buttonEnviar);

        // Configuración del botón "Enviar"
        buttonEnviar.setOnClickListener(v -> {
            // Calcular el puntaje total de todas las preguntas
            int puntajeTotal = calcularPuntaje();

            // Recuperar el nivel de batería desde SharedPreferences
            Integer nivelBateria = prefs.getInt("nivel_bateria", 1); // Por defecto, nivel de batería es 1

            // Actualizar el nivel de batería en Firebase
            database.child(idAlumno).child("nivel_bateria").setValue(nivelBateria)
                    .addOnSuccessListener(aVoid -> {
                        // Guardar el puntaje total en el mismo registro
                        database.child(idAlumno).child("puntuacion").setValue(puntajeTotal)
                                .addOnSuccessListener(aVoid1 -> {
                                    Toast.makeText(getContext(), "Datos enviados correctamente.", Toast.LENGTH_SHORT).show();

                                    // Marcar el formulario como completado en SharedPreferences
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putBoolean("formulario_completado_" + idAlumno, true);
                                    editor.apply();

                                    // Redirigir al fragmento de inicio
                                    cargarFragmentoInicio();
                                })
                                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al enviar puntaje.", Toast.LENGTH_SHORT).show());
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al actualizar nivel de batería.", Toast.LENGTH_SHORT).show());
        });

        return view;
    }

    private int calcularPuntaje() {
        int puntaje = 0;
        puntaje += obtenerPuntajePregunta(radioGroupHistoria);
        puntaje += obtenerPuntajePregunta(radioGroupRazonamiento);
        puntaje += obtenerPuntajePregunta(radioGroupMatematicas);
        puntaje += obtenerPuntajePregunta(radioGroupLenguaje);
        return puntaje;
    }

    private int obtenerPuntajePregunta(RadioGroup radioGroup) {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if (radioButtonId == -1) return 0;

        RadioButton radioButton = radioGroup.findViewById(radioButtonId);
        String respuesta = radioButton.getText().toString();

        switch (respuesta) {
            case "A":
                return 3;
            case "B":
                return 2;
            case "C":
                return 1;
            default:
                return 0;
        }
    }

    // Método para redirigir al fragmento de inicio
    private void cargarFragmentoInicio() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contendor, new Inicio()) // Reemplazar con tu fragmento de inicio
                .commit();
    }
}
