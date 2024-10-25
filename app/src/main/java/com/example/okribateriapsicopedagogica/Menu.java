package com.example.okribateriapsicopedagogica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu extends AppCompatActivity {
    private LinearLayout optionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        optionsLayout = findViewById(R.id.optionsLayout);
        Button option1 = findViewById(R.id.option1);
        Button option2 = findViewById(R.id.option2);
        Button option3 = findViewById(R.id.option3);
        Button option4 = findViewById(R.id.option4);
        Button chatButton = findViewById(R.id.option5);
        Button option6 = findViewById(R.id.option6);
        Button option7 = findViewById(R.id.option7);
        Button option8 = findViewById(R.id.option8);
        Button option9 = findViewById(R.id.option9); // Botón de "Hacer Formulario"
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        // Configurar el botón flotante para mostrar/ocultar el menú
        floatingActionButton.setOnClickListener(v -> {
            if (optionsLayout.getVisibility() == View.GONE) {
                optionsLayout.setVisibility(View.VISIBLE);
            } else {
                optionsLayout.setVisibility(View.GONE);
            }
        });

        // Verificar el tipo de usuario
        SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
        String userType = prefs.getString("userType", "");

        if ("alumno".equals(userType)) {
            option2.setVisibility(View.GONE); // Ocultar "Registrar Alumno"
            option3.setVisibility(View.GONE); // Ocultar "Revisión Alumnos"
            option7.setVisibility(View.GONE); // Ocultar "Subir Publicación"
            option4.setVisibility(View.GONE); // Ocultar "Subir Publicación"
        }

        // Configurar los botones del menú
        option1.setOnClickListener(v -> loadFragment(new Inicio()));
        option2.setOnClickListener(v -> loadFragment(new RegristrarAlumnos()));
        option3.setOnClickListener(v -> loadFragment(new RevisionAlumnos()));
        option4.setOnClickListener(v -> loadFragment(new Baterias()));
        option6.setOnClickListener(v -> loadFragment(new Notificacion()));
        option7.setOnClickListener(v -> loadFragment(new SubirPublicacion()));
        option8.setOnClickListener(v -> loadFragment(new Perfil()));

        // Configurar el botón "Hacer Formulario"
        option9.setOnClickListener(v -> {
            if ("alumno".equals(userType)) {
                loadFragment(new HacerFormulario()); // Cargar el fragmento solo para alumnos
            }
        });

        // Cargar la actividad de chat
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Mensajes.class);
            startActivity(intent);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contendor, fragment)
                .addToBackStack(null)
                .commit();

        optionsLayout.setVisibility(View.GONE); // Ocultar el menú al seleccionar una opción
    }
}
