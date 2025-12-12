package com.backend.api.model;

import jakarta.validation.constraints.*;

public class Medico {
    private Long id;

    // ----------------------------------------------------
    // VALIDACIÓN ESTRICTA PARA NOMBRE (Solo letras y espacios)
    // ----------------------------------------------------
    @NotBlank(message = "El nombre del médico es obligatorio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    // ----------------------------------------------------
    // VALIDACIÓN PARA ESPECIALIDAD (Ej: Oftalmología, Cardiología)
    // ----------------------------------------------------
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 5, max = 50, message = "La especialidad debe tener entre 5 y 50 caracteres")
    // Opcional: Regex para permitir letras y guiones, sin números.
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+$", message = "La especialidad solo debe contener letras, espacios o guiones")
    private String especialidad;

    // ----------------------------------------------------
    // VALIDACIÓN PARA ACTIVO
    // ----------------------------------------------------
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo = true; // Por defecto true para el estado de activación


    public Medico( ) {    }

    public Medico(Long id, String nombre, String especialidad, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
