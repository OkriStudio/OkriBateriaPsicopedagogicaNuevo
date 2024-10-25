package com.example.okribateriapsicopedagogica;

public class Alumnos {
    private String id_alumno;
    private String nombre;
    private String apellido;
    private String correo;
    private String curso;
    private String rut;
    private String contraseña;
    private Integer nivel_bateria; // Cambiado a Integer para poder tener null

    // Constructor sin argumentos
    public Alumnos() {
        // Constructor vacío necesario para Firebase
    }

    // Constructor
    public Alumnos(String id_alumno, String nombre, String apellido, String correo, String curso, String rut, String contraseña, Integer nivel_bateria) {
        this.id_alumno = id_alumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.curso = curso;
        this.rut = rut;
        this.contraseña = contraseña;
        this.nivel_bateria = nivel_bateria;
    }

    // Getters y Setters
    public String getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(String id_alumno) {
        this.id_alumno = id_alumno;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
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

    public Integer getNivel_bateria() {
        return nivel_bateria;
    }

    public void setNivel_bateria(Integer nivel_bateria) {
        this.nivel_bateria = nivel_bateria;
    }

    @Override
    public String toString() {
        return "Alumnos{" +
                "id_alumno='" + id_alumno + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", curso='" + curso + '\'' +
                ", rut='" + rut + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", nivel_bateria=" + nivel_bateria +
                '}';
    }
}
