package com.backend.api.model;

import jakarta.validation.constraints.*; // Importar las anotaciones
import java.time.LocalDateTime;

public class Cita {
    private Long idCita;

    // ----------------------------------------------------
    // VALIDACIÓN DE CLAVES FORÁNEAS (No nulas)
    // ----------------------------------------------------
    @NotNull(message = "El ID del cliente/paciente es obligatorio")
    @Min(value = 1, message = "El ID del cliente debe ser positivo")
    private Long idCliente; // FK a clientes

    @NotNull(message = "El ID del médico es obligatorio")
    @Min(value = 1, message = "El ID del médico debe ser positivo")
    private Long idMedico;     // FK a optometristas (cambié el nombre del getter/setter a idMedico para consistencia)

    // ----------------------------------------------------
    // VALIDACIÓN DE ESTADO (Solo valores válidos)
    // ----------------------------------------------------
    @NotNull(message = "El ID del estado de la cita es obligatorio")
    @Min(value = 1, message = "El ID del estado debe ser válido")
    private Long idEstado;           // FK a estado_cita

    // ----------------------------------------------------
    // VALIDACIÓN DE FECHA/HORA (No puede ser en el pasado)
    // ----------------------------------------------------
    @NotNull(message = "La fecha y hora de la cita son obligatorias")
    @FutureOrPresent(message = "La cita no puede programarse en el pasado")
    private LocalDateTime fechaHora;

    // ----------------------------------------------------
    // VALIDACIÓN DE MOTIVO
    // ----------------------------------------------------
    @NotBlank(message = "El motivo de la cita es obligatorio")
    @Size(min = 10, max = 500, message = "El motivo debe tener entre 10 y 500 caracteres")
    private String motivo;

    // ----------------------------------------------------
    // VALIDACIÓN DE FECHA CREACIÓN (Solo se asigna automáticamente)
    // ----------------------------------------------------
    private LocalDateTime fechaCreacion;

    // Constructores
    public Cita() {
        this.fechaCreacion = LocalDateTime.now();
        this.idEstado = EstadoCita.ID_PROGRAMADA;
    }

    // Getters y Setters
    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public Long getIdOptometrista() { return idMedico; }
    public void setIdOptometrista(Long idOptometrista) { this.idMedico = idOptometrista; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}