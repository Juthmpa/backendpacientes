package com.backend.api.util;

/**
 * Excepción personalizada utilizada para errores de validación de datos.
 * Lanza esta excepción cuando los datos de entrada del usuario no cumplen
 * con las reglas de negocio (ej. formato de email, cedula incorrecta, etc.).
 */
public class ValidationException extends Exception {

    /**
     * Constructor con el mensaje de error específico.
     * @param message El mensaje detallado de por qué falló la validación.
     */
    public ValidationException(String message) {
        super(message);
    }
}
