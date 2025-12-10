package com.backend.api.util;

/**
 * Clase de utilidad para centralizar todas las reglas de validación de datos.
 * Lanza ValidationException en caso de fallo.
 */
public class ValidatorUtil {

    // =========================================================================
    // 1. VALIDACIÓN OBLIGATORIA (validateRequired)
    // =========================================================================

    /**
     * Valida que un campo String no sea nulo ni vacío.
     * @param value El valor del campo.
     * @param fieldName El nombre del campo (para el mensaje de error).
     * @throws ValidationException si el campo es nulo o vacío.
     */
    public static void validateRequired(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("El campo " + fieldName + " es obligatorio.");
        }
    }

    // =========================================================================
    // 2. VALIDACIÓN DE CORREO ELECTRÓNICO (validateEmail)
    // =========================================================================

    /**
     * Valida el formato de un correo electrónico.
     * @param email El correo a validar.
     * @throws ValidationException si el formato es inválido.
     */
    public static void validateEmail(String email) throws ValidationException {
        validateRequired(email, "Correo"); // Asegurar que no esté vacío primero
        // Regex simple para email (puede ser más robusta, pero suficiente para el examen)
        if (!email.matches("^.+@.+\\..+$")) {
            throw new ValidationException("El formato del correo electrónico es inválido.");
        }
    }

    // =========================================================================
    // 3. VALIDACIÓN DE CADENA SIN NÚMEROS (validateStringNoNumbers)
    // =========================================================================

    /**
     * Valida que un String contenga solo letras y espacios (para nombres).
     * @param value El valor del campo.
     * @param fieldName El nombre del campo.
     * @throws ValidationException si contiene números o caracteres especiales no permitidos.
     */
    public static void validateStringNoNumbers(String value, String fieldName) throws ValidationException {
        validateRequired(value, fieldName);
        // Permite letras (con tildes), Ñ/ñ y espacios
        if (!value.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new ValidationException("El campo " + fieldName + " no debe contener números ni caracteres especiales.");
        }
    }

    // =========================================================================
    // 4. VALIDACIÓN DE FORTALEZA DE CONTRASEÑA (validatePasswordStrength)
    // =========================================================================

    /**
     * Valida la fortaleza de la contraseña.
     * Reglas: 8+ caracteres, al menos una mayúscula, una minúscula y un número.
     * @param password La contraseña a validar.
     * @throws ValidationException si la contraseña no cumple con la fortaleza.
     */
    public static void validatePasswordStrength(String password) throws ValidationException {
        validateRequired(password, "Contraseña");
        if (password.length() < 8) {
            throw new ValidationException("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("La contraseña debe incluir al menos una letra mayúscula.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ValidationException("La contraseña debe incluir al menos una letra minúscula.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new ValidationException("La contraseña debe incluir al menos un número.");
        }
    }
}