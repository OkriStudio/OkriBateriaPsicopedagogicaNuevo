package com.example.okribateriapsicopedagogica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SubirPublicacion extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView imageView;
    private EditText editTextDescripcion;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public SubirPublicacion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference("publicaciones");
        databaseReference = FirebaseDatabase.getInstance().getReference("publicaciones");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subir_publicacion, container, false);

        imageView = view.findViewById(R.id.imageView_foto);
        editTextDescripcion = view.findViewById(R.id.editText_descripcion);
        Button buttonSubirFoto = view.findViewById(R.id.button_subir_foto);
        Button buttonPublicar = view.findViewById(R.id.button_publicar);

        buttonSubirFoto.setOnClickListener(v -> openFileChooser());
        buttonPublicar.setOnClickListener(v -> publicar());

        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void publicar() {
        if (imageUri != null && !editTextDescripcion.getText().toString().trim().isEmpty()) {
            // Subir imagen a Firebase Storage
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = fileReference.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String descripcion = editTextDescripcion.getText().toString().trim();
                    String imagenUrl = uri.toString();

                    // Guardar en Firebase Realtime Database
                    String id = databaseReference.push().getKey(); // Generar un nuevo ID
                    Publicacion publicacion = new Publicacion(id, descripcion, imagenUrl);
                    databaseReference.child(id).setValue(publicacion).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Publicación exitosa", Toast.LENGTH_SHORT).show();
                            // Resetear campos
                            imageView.setImageResource(android.R.color.darker_gray);
                            editTextDescripcion.setText("");

                            // Navegar de vuelta al fragmento de Inicio
                            navigateToInicio(); // Aquí llamamos al método de navegación
                        } else {
                            Toast.makeText(getContext(), "Error al publicar", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Error al subir la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(getContext(), "Por favor, selecciona una imagen y escribe una descripción", Toast.LENGTH_SHORT).show();
        }
    }
    private void navigateToInicio() {
        Inicio inicioFragment = new Inicio();
        // Cambiar el fragmento actual por el fragmento de Inicio
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contendor, inicioFragment) // Asegúrate de que este ID corresponda al contenedor de tu fragmento
                .addToBackStack(null) // Agrega a la pila de retroceso si deseas poder regresar
                .commit();
    }



}
