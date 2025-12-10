package com.backend.api.dao;

import com.backend.api.config.DatabaseConfig;
import com.backend.api.model.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDAO {

    // Método 'guardar'
    public Usuario guardar(Usuario usuario) throws SQLException {
        // SQL con el orden de las columnas: nombre(1), correo(2), username(3), password(4), rol(5), activo(6)
        String sql = "INSERT INTO usuario (nombre, correo, username, password, rol, activo)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // ¡ORDEN CORREGIDO!
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword()); // Asumo que aquí aplicarás el cifrado (BCrypt)
            stmt.setString(5, usuario.getRol());
            stmt.setBoolean(6, usuario.getActivo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    // El ID generado siempre está en la columna 1
                    usuario.setId(rs.getLong(1));
                }
            }
        }
        return usuario;
    }

    // Método 'listar' (correcto)
    public List<Usuario> listar() throws SQLException {
        // Puedes agregar 'WHERE activo = TRUE' para listar solo usuarios activos.
        String sql = "SELECT * FROM usuario";
        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = mapRow(rs);
                lista.add(u);
            }
        }
        return lista;
    }

    // Método 'buscarPorId' (correcto)
    public Usuario buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // Método 'actualizar' (correcto, pero notar que no actualiza la contraseña)
    public void actualizar(Usuario u) throws SQLException {
        // En el backend, debes tener un segundo método de actualización para el password,
        // o construir el SQL dinámicamente si u.getPassword() no es vacío.
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, username = ?, rol = ?, activo = ? \n" +
                " WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getUsername());
            stmt.setString(4, u.getRol());
            stmt.setBoolean(5, u.getActivo());
            stmt.setLong(6, u.getId());

            stmt.executeUpdate();
        }
    }

    // Método 'updateStatus' (correcto)
    public void updateStatus(Long id, boolean activo) throws SQLException {
        String sql = "UPDATE usuario SET activo = ? \n" +
                " WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, activo);
            stmt.setLong(2, id);

            stmt.executeUpdate();
        }
    }


    // =========================================================================
    // MÉTODO DE MAPEO CORREGIDO (mapRow)
    // =========================================================================

    private Usuario mapRow(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();

        // ¡CORRECCIÓN! Usar 'id'
        u.setId(rs.getLong("id"));

        u.setNombre(rs.getString("nombre"));
        u.setCorreo(rs.getString("correo"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
