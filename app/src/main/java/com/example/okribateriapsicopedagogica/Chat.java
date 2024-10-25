package com.example.okribateriapsicopedagogica;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Chat extends AppCompatActivity {
    private TextView chatNombre;
    private EditText inputMensaje;
    private Button botonEnviar;
    private LinearLayout layoutMensajes;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Inicialización de los elementos de la interfaz
        chatNombre = findViewById(R.id.chat_nombre);
        inputMensaje = findViewById(R.id.input_mensaje);
        botonEnviar = findViewById(R.id.boton_enviar);
        layoutMensajes = findViewById(R.id.layout_mensajes);
        scrollView = findViewById(R.id.scroll_view);

        // Obtener el nombre del usuario del Intent
        Intent intent = getIntent();
        String nombreUsuario = intent.getStringExtra("nombreUsuario");
        chatNombre.setText("Chat con " + nombreUsuario);

        // Configurar el botón de enviar
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = inputMensaje.getText().toString();
                if (!mensaje.isEmpty()) {
                    agregarMensajeEnviado(mensaje);
                    inputMensaje.setText(""); // Limpiar el campo de texto
                }
            }
        });

        // Cargar mensajes predeterminados
        cargarMensajesPredeterminados();
    }

    private void cargarMensajesPredeterminados() {
        // Mensajes predeterminados que se mostrarán
        String[] mensajes = {
                "Hola, ¿cómo estás?",
                "Todo bien, ¿y usted?",
                "¿Te gustaría discutir sobre el tema de la clase?",
                "Claro, me gustaría saber más."
        };

        // Agregar mensajes al layout de mensajes
        for (String mensaje : mensajes) {
            agregarMensajeRecibido(mensaje);
        }
    }

    private void agregarMensajeRecibido(String mensaje) {
        TextView mensajeRecibido = new TextView(this);
        mensajeRecibido.setText(mensaje);
        mensajeRecibido.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        mensajeRecibido.setTextColor(getResources().getColor(android.R.color.white));
        mensajeRecibido.setPadding(12, 12, 12, 12);
        mensajeRecibido.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Agregar el mensaje al layout
        layoutMensajes.addView(mensajeRecibido);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN)); // Desplazar hacia abajo
    }

    private void agregarMensajeEnviado(String mensaje) {
        TextView mensajeEnviado = new TextView(this);
        mensajeEnviado.setText(mensaje);
        mensajeEnviado.setBackgroundColor(getResources().getColor(R.color.light_blue_100)); // Cambia el color si es necesario
        mensajeEnviado.setTextColor(getResources().getColor(android.R.color.white));
        mensajeEnviado.setPadding(12, 12, 12, 12);
        mensajeEnviado.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mensajeEnviado.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mensajeEnviado.setGravity(View.TEXT_ALIGNMENT_VIEW_END); // Alinear a la derecha

        // Agregar el mensaje al layout
        layoutMensajes.addView(mensajeEnviado);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN)); // Desplazar hacia abajo
    }
}
