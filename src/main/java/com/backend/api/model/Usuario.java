package com.backend.api.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;

public class Usuario implements Serializable {
    private Long id; // ID no necesita validación si es autogenerado

    // ----------------------------------------------------
    // 1. VALIDACIÓN ESTRICTA PARA NOMBRE (Solo letras y espacios)
    // ----------------------------------------------------
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    // Regex: Acepta letras (mayúsculas/minúsculas), letras acentuadas, ñ/Ñ y espacios. Prohíbe números y símbolos.
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    // ----------------------------------------------------
    // 2. VALIDACIÓN ESTRICTA PARA CORREO (Formato específico)
    // ----------------------------------------------------
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String correo;

    // ----------------------------------------------------
    // 3. VALIDACIÓN ESTRICTA PARA USERNAME (Alfanumérico seguro)
    // ----------------------------------------------------
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 10, message = "El username debe tener entre 4 y 10 caracteres")
    // Regex: Solo letras, números, puntos, guiones y guion bajo.
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "El username solo puede contener letras, números, puntos, guiones y guion bajo")
    private String username;

    // 4. VALIDACIÓN ESTRICTA PARA PASSWORD (Mínimo 8 caracteres, complejidad media/alta)
    // La regex siguiente fuerza:
    // - Mínimo 8 caracteres de longitud total
    // - Al menos una minúscula (?=.*[a-z])
    // - Al menos una mayúscula (?=.*[A-Z])
    // - Al menos un dígito (?=.*[0-9])
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números."
    )
    private String password;

    // ----------------------------------------------------
    // 5. VALIDACIÓN ESTRICTA PARA ROL (Lista fija de valores)
    // ----------------------------------------------------
    @NotBlank(message = "El rol es obligatorio")
    // Regex: Solo permite exactamente estas tres cadenas.
    @Pattern(regexp = "^(admin|medico|paciente)$", message = "El rol debe ser admin, medico o paciente")
    private String rol;

    // ----------------------------------------------------
    // 6. VALIDACIÓN NUMÉRICA (Si tuvieras un campo numérico como Edad/Teléfono)
    // ----------------------------------------------------

    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo = true;

    public Usuario() { }
    // Constructor de Copia (debe existir)
    public Usuario(Usuario otroUsuario) {
        this.id = otroUsuario.getId();
        this.nombre = otroUsuario.getNombre();
        this.correo = otroUsuario.getCorreo();
        this.username = otroUsuario.getUsername();
        this.password = otroUsuario.getPassword();
        this.rol = otroUsuario.getRol();
        this.activo = otroUsuario.getActivo();
    }

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
    // *** AÑADE ESTE GETTER ***
// Garantiza la compatibilidad con serializadores que buscan la convención 'is'
    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
