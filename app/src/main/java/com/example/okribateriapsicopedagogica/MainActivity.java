package com.example.okribateriapsicopedagogica;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences; // Asegúrate de importar esto
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(this::login);

        // Inicializa la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void login(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Verificar en alumnos
        databaseReference.child("alumnos").orderByChild("correo").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Alumnos alumno = snapshot.getValue(Alumnos.class);
                        if (alumno != null && alumno.getContraseña().equals(password)) {
                            // Guardar ID del alumno y tipo de usuario en SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("id_alumno", alumno.getId_alumno()); // Guardamos el ID del alumno
                            editor.putString("userType", "alumno"); // Guardamos el tipo de usuario
                            editor.remove("id_profe");  // Eliminar el ID de profesor si existiera
                            editor.apply();

                            // Log del ID guardado
                            Log.d("MainActivity", "ID de Alumno guardado: " + alumno.getId_alumno());
                            Log.d("MainActivity", "Tipo de usuario guardado: alumno");

                            // Iniciar actividad para Alumnos
                            Toast.makeText(MainActivity.this, "Bienvenido, " + alumno.getNombre(), Toast.LENGTH_SHORT).show();

                            // Iniciar nueva actividad
                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            startActivity(intent);
                            finish(); // Opcional: Cierra MainActivity si no quieres que vuelva atrás
                            return;
                        }
                    }
                }

                // Verificar en profesores
                databaseReference.child("profesores").orderByChild("correo").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Profe profe = snapshot.getValue(Profe.class);
                                if (profe != null && profe.getContraseña().equals(password)) {
                                    // Guardar ID del profesor y tipo de usuario en SharedPreferences
                                    SharedPreferences prefs = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("id_profe", profe.getId_profe()); // Guardamos el ID del profesor
                                    editor.putString("userType", "profesor"); // Guardamos el tipo de usuario
                                    editor.remove("id_alumno");  // Eliminar el ID de alumno si existiera
                                    editor.apply();

                                    // Log del ID guardado
                                    Log.d("MainActivity", "ID de Profesor guardado: " + profe.getId_profe());
                                    Log.d("MainActivity", "Tipo de usuario guardado: profesor");

                                    // Iniciar actividad para Profesores
                                    Toast.makeText(MainActivity.this, "Bienvenido, " + profe.getNombre(), Toast.LENGTH_SHORT).show();

                                    // Iniciar nueva actividad
                                    Intent intent = new Intent(MainActivity.this, Menu.class);
                                    startActivity(intent);
                                    finish(); // Opcional: Cierra MainActivity si no quieres que vuelva atrás
                                    return;
                                }
                            }
                        }

                        // Si las credenciales son incorrectas
                        Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Error de base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error de base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
