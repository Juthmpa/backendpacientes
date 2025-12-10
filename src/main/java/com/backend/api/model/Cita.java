package com.backend.api.model;

import java.time.LocalDateTime;

public class Cita {
    private Long idCita;
    private Long idCliente;          // FK a clientes
    private Long idOptometrista;     // FK a optometristas
    private LocalDateTime fechaHora;
    private String motivo;
    private Long idEstado;           // FK a estado_cita
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
    public Long getIdOptometrista() { return idOptometrista; }
    public void setIdOptometrista(Long idOptometrista) { this.idOptometrista = idOptometrista; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}