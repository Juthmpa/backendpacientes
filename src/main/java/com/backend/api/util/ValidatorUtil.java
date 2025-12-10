package com.backend.api.util;

import jakarta.validation.ValidationException;
import java.time.LocalDateTime;

public class ValidatorUtil {

    // -------------------------------
    // VALIDACIONES GENERALES
    // -------------------------------

    // Valida que un string no sea vacío y solo contenga letras
    public static void validateStringNoNumbers(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("El campo " + fieldName + " es obligatorio.");
        }
        if (!value.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new ValidationException("El campo " + fieldName + " solo debe contener letras y espacios.");
        }
    }

    // Valida cualquier string obligatorio
    public static void validateRequiredString(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("El campo " + fieldName + " es obligatorio.");
        }
    }

    // Valida email
    public static void validateEmail(String email) throws ValidationException {
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new ValidationException("El correo electrónico es inválido.");
        }
    }

    // Valida longitud mínima y máxima
    public static void validateStringLength(String value, String field, int min, int max) throws ValidationException {
        if (value == null) throw new ValidationException("El campo " + field + " es obligatorio.");
        if (value.length() < min || value.length() > max) {
            throw new ValidationException("El campo " + field + " debe tener entre " + min + " y " + max + " caracteres.");
        }
    }

    // Valida username (solo letras y números)
    public static void validateUsername(String username) throws ValidationException {
        validateRequiredString(username, "Username");
        if (!username.matches("^[a-zA-Z0-9._-]{4,20}$")) {
            throw new ValidationException("El username debe tener entre 4 y 20 caracteres y solo contener letras, números, puntos, guiones o guion bajo.");
        }
    }

    // Valida password
    public static void validatePassword(String password) throws ValidationException {
        validateRequiredString(password, "Password");

        // mínimo 8 caracteres, una mayus, una min, un número
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")) {
            throw new ValidationException(
                    "La contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un número."
            );
        }
    }

    // Valida IDs que no sean null ni 0
    public static void validateId(Long id, String field) throws ValidationException {
        if (id == null || id <= 0) {
            throw new ValidationException("El campo " + field + " debe ser un ID válido.");
        }
    }

    // Valida boolean obligatorio
    public static void validateBoolean(Boolean value, String field) throws ValidationException {
        if (value == null) {
            throw new ValidationException("El campo " + field + " es obligatorio.");
        }
    }

    // Valida fechas futuras
    public static void validateFutureDate(LocalDateTime fecha, String field) throws ValidationException {
        if (fecha == null) {
            throw new ValidationException("El campo " + field + " es obligatorio.");
        }
        if (fecha.isBefore(LocalDateTime.now())) {
            throw new ValidationException("La fecha de " + field + " debe ser futura.");
        }
    }

    // -------------------------------
    // VALIDACIONES DE ROLES
    // -------------------------------
    public static void validateRol(String rol) throws ValidationException {
        if (rol == null) throw new ValidationException("El rol es obligatorio.");
        if (!rol.matches("^(admin|medico|paciente)$")) {
            throw new ValidationException("Rol inválido. Solo se permite: admin, medico o paciente.");
        }
    }
}