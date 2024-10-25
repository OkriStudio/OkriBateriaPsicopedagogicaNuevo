package com.example.okribateriapsicopedagogica;

public class NotificacionModel {
    private String descripcion;
    private String fecha; // Puedes agregar más campos si es necesario

    public NotificacionModel(String descripcion, String fecha) {
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }
}
