package com.example.okribateriapsicopedagogica;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Mensajes extends AppCompatActivity {

    private LinearLayout chatFabiola, chatPedro, chatEnriquez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        // Referencias a las opciones de chat
        chatFabiola = findViewById(R.id.chat_fabiola);
        chatPedro = findViewById(R.id.chat_pedro);
        chatEnriquez = findViewById(R.id.chat_enriquez);

        // Click en Profesora Fabiola
        chatFabiola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirChat("Profesora Fabiola");
            }
        });

        // Click en Pedro
        chatPedro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirChat("Pedro");
            }
        });

        // Click en Profesor Enriquez
        chatEnriquez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirChat("Profesor Enriquez");
            }
        });
    }

    // Método para abrir la actividad del chat
    private void abrirChat(String nombreUsuario) {
        Intent intent = new Intent(Mensajes.this, Chat.class);
        intent.putExtra("nombreUsuario", nombreUsuario);
        startActivity(intent);
    }
}