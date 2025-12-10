package com.backend.api.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de configuración centralizada para la conexión a la base de datos MySQL
 * a través de Docker.
 */
public class DatabaseConfig {

    // Credenciales y URL de conexión definidas en el docker-compose.yml
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pacientes_db?serverTimezone=UTC";
    private static final String JDBC_USER = "user";
    private static final String JDBC_PASSWORD = "password";

    /**
     * Proporciona una nueva conexión a la base de datos.
     * Este método debe ser public y static para ser usado fácilmente por los DAOs.
     * @return Una nueva conexión JDBC.
     * @throws SQLException Si ocurre un error de conexión a la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        // En WildFly se recomienda usar un DataSource, pero para JDBC simple, esto es correcto.
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}
