package com.example.okribateriapsicopedagogica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Notificacion extends Fragment {

    private RecyclerView recyclerViewNotificaciones;
    private NotificacionAdapter adapter;
    private DatabaseReference databaseReference;
    private List<NotificacionModel> listaNotificaciones;

    public Notificacion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("publicaciones");
        listaNotificaciones = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacion, container, false);
        recyclerViewNotificaciones = view.findViewById(R.id.recyclerViewNotificaciones);
        recyclerViewNotificaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NotificacionAdapter(listaNotificaciones);
        recyclerViewNotificaciones.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaNotificaciones.clear(); // Limpia la lista anterior
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String descripcion = postSnapshot.child("descripcion").getValue(String.class);
                    String fecha = postSnapshot.child("fecha").getValue(String.class); // Asegúrate de tener una fecha en tu base de datos
                    listaNotificaciones.add(new NotificacionModel(descripcion, fecha));
                }
                adapter.notifyDataSetChanged(); // Actualiza el adaptador
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores
            }
        });

        return view;
    }
}
