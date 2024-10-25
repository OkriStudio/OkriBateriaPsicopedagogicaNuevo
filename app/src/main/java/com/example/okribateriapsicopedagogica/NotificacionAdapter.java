package com.example.okribateriapsicopedagogica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.ViewHolder> {
    private final List<NotificacionModel> notificaciones;

    public NotificacionAdapter(List<NotificacionModel> notificaciones) {
        this.notificaciones = notificaciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificacionModel notificacion = notificaciones.get(position);
        holder.textViewDescripcion.setText(notificacion.getDescripcion());
        holder.textViewFecha.setText(notificacion.getFecha()); // Asegúrate de tener una fecha en tu modelo
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDescripcion;
        TextView textViewFecha;

        ViewHolder(View itemView) {
            super(itemView);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
        }
    }
}
