package com.backend.api.model;

public class Usuario {
    private Long id;
    private String nombre;
    private String correo;
    private String username;
    private String password;
    private String rol; // admin, medico, paciente
    private Boolean activo = true; // Por defecto true para el estado de activación

    public Usuario( ) { };

    public Usuario(Long id, String nombre, String correo, String username,
                   String password, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.username = username;
        this.password = password;
        this.activo = activo;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
