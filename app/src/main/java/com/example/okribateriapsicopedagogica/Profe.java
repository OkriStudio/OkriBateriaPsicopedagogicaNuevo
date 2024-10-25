package com.example.okribateriapsicopedagogica;

public class Profe {
    private String id_profe;
    private String nombre;
    private String apellido;
    private String correo;
    private String rut;
    private String contraseña;

    // Constructor sin argumentos
    public Profe() {
        // Constructor vacío necesario para Firebase
    }

    // Constructor
    public Profe(String id_profe, String nombre, String apellido, String correo, String rut, String contraseña) {
        this.id_profe = id_profe;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rut = rut;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public String getId_profe() {
        return id_profe;
    }

    public void setId_profe(String id_profe) {
        this.id_profe = id_profe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Profe{" +
                "id_profe='" + id_profe + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", rut='" + rut + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
