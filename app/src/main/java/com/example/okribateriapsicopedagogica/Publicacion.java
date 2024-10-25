package com.example.okribateriapsicopedagogica;

public class Publicacion {
    private String id;
    private String descripcion;
    private String imagenUrl;

    public Publicacion() {
        // Constructor vacío necesario para Firebase
    }

    public Publicacion(String id, String descripcion, String imagenUrl) {
        this.id = id;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
