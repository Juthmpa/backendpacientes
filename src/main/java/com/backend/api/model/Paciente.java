package com.backend.api.model;

import java.io.Serializable;

// Implementar Serializable es una buena práctica en EE
public class Paciente implements Serializable {
    private Long id;
    private String nombre;
    private String correo;
    private Integer edad;
    private String direccion;
    private String numeroCedula; // Campo clave
    private Boolean activo = true; // Por defecto true para el estado de activación

    // Constructor
    public Paciente() {
    }

    // Contructor con parámetros
    public Paciente(Long id, String nombre, String correo, Integer edad, String direccion, String numeroCedula, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.direccion = direccion;
        this.numeroCedula = numeroCedula;
        this.activo = activo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }
    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public Boolean isActivo() { return activo;  }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}