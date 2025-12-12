package com.backend.api.model;

import jakarta.validation.constraints.*;

public class EstadoCita {
    private Long id;
    // ----------------------------------------------------
    // VALIDACIÓN DE NOMBRE (Solo letras y espacios, en mayúsculas recomendado)
    // ----------------------------------------------------
    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(min = 5, max = 20, message = "El nombre debe tener entre 5 y 20 caracteres")
    // Regex para asegurar que solo se usen letras y MAYÚSCULAS/guiones
    @Pattern(regexp = "^[A-Z_]+$", message = "El nombre del estado debe estar en mayúsculas (Ej: PROGRAMADA)")
    private String nombre;

    // Constantes basadas en el script SQL (INSERT INTO estado_cita)
    public static final Long ID_PROGRAMADA = 1L;
    public static final Long ID_ATENDIDA = 2L;
    public static final Long ID_CANCELADA = 3L;
    public static final Long ID_REAGENDADA = 4L;

    // Constructores
    public EstadoCita() {}
    public EstadoCita(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}