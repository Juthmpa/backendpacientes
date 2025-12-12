package com.backend.api.model;

import jakarta.validation.constraints.*;

import java.io.Serializable;

// Implementar Serializable es una buena práctica en EE
public class Paciente implements Serializable {
    private Long id;
    // ----------------------------------------------------
    // VALIDACIÓN ESTRICTA PARA NOMBRE (Solo letras y espacios, mín 3, máx 60)
    // ----------------------------------------------------
    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    // ----------------------------------------------------
    // VALIDACIÓN PARA CORREO
    // ----------------------------------------------------
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String correo;

    // ----------------------------------------------------
    // VALIDACIÓN PARA EDAD (Debe ser un número, y dentro de un rango razonable)
    // ----------------------------------------------------
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad mínima es 0")
    @Max(value = 120, message = "La edad máxima es 120")
    private Integer edad;

    // ----------------------------------------------------
    // VALIDACIÓN PARA DIRECCIÓN (Alfanumérico, máx 255)
    // ----------------------------------------------------
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    private String direccion;

    // ----------------------------------------------------
    // VALIDACIÓN PARA CÉDULA (Solo 10 dígitos numéricos)
    // NOTA: La validación de la LÓGICA de la cédula debe ir en ValidatorUtil/Service.
    // ----------------------------------------------------
    @NotBlank(message = "El número de cédula es obligatorio")
    @Size(min = 10, max = 10, message = "La cédula debe tener exactamente 10 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "La cédula solo debe contener números")
    private String numeroCedula; // Campo clave

    // ----------------------------------------------------
    // VALIDACIÓN PARA ACTIVO
    // ----------------------------------------------------
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo = true;
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