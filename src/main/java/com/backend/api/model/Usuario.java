package com.backend.api.model;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String correo;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "El username solo puede contener letras, números, puntos, guiones y guion bajo")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "^(admin|medico|paciente)$", message = "El rol debe ser admin, medico o paciente")
    private String rol;

    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo = true;

    public Usuario() { }

    public Usuario(Long id, String nombre, String correo, String username,
                   String password, String rol, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.username = username;
        this.password = password;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
