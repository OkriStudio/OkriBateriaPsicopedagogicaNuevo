package com.example.okribateriapsicopedagogica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Inicio extends Fragment {

    private LinearLayout linearLayoutPublicaciones;
    private DatabaseReference databaseReference;

    public Inicio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("publicaciones");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        linearLayoutPublicaciones = view.findViewById(R.id.linearLayout_publicaciones);

        loadPublicaciones(); // Cargar las publicaciones desde Firebase

        return view;
    }

    private void loadPublicaciones() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                linearLayoutPublicaciones.removeAllViews(); // Limpiar el layout antes de cargar nuevos datos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Publicacion publicacion = snapshot.getValue(Publicacion.class);
                    if (publicacion != null) {
                        addPublicacionToLayout(publicacion);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error al cargar publicaciones: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPublicacionToLayout(Publicacion publicacion) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_publicacion, linearLayoutPublicaciones, false);

        ImageView imageView = itemView.findViewById(R.id.imageView_publicacion);
        TextView textViewDescripcion = itemView.findViewById(R.id.textView_descripcion);

        textViewDescripcion.setText(publicacion.getDescripcion());
        Picasso.get().load(publicacion.getImagenUrl()).into(imageView); // Usando Picasso para cargar la imagen

        linearLayoutPublicaciones.addView(itemView);
    }
}
