package com.backend.api.dao;

import com.backend.api.config.DatabaseConfig;
import com.backend.api.model.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDAO {

    // Nótese que aquí falta la lógica de cifrado de contraseña (BCrypt),
    // lo cual es crítico para la seguridad, pero no está en el código que enviaste.
    // Asumo que la añadirás al método guardar.

    // Método 'guardar' (antes save)
    public Usuario guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, username, correo, rol, password, activo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getPassword());
            stmt.setBoolean(6, usuario.getActivo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                // Asumiendo que la columna PK se llama 'id_usuario' o que devuelve el ID correcto.
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
        }

        return usuario;
    }

    // Método 'listar' (antes findAll)
    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT * FROM usuarios";
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

    // Método 'buscarPorId' (antes findById)
    public Usuario buscarPorId(Long id) throws SQLException {
        // CORRECCIÓN: Usar id_usuario si ese es el nombre de la columna PK
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

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

    // Método 'actualizar' (antes update)
    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, username = ?, correo = ?, rol = ?, activo = ? WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // ... parámetros

            stmt.setLong(6, u.getId()); // Usar el ID del objeto
            stmt.executeUpdate();
        }
    }

    // =========================================================================
    // MÉTODO FALTANTE: updateStatus
    // =========================================================================

    /**
     * Actualiza el estado activo de un usuario por su ID.
     */
    public void updateStatus(Long id, boolean activo) throws SQLException {
        String sql = "UPDATE usuarios SET activo = ? WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, activo);
            stmt.setLong(2, id);

            stmt.executeUpdate();
        }
    }


    // =========================================================================
    // MÉTODO DE MAPEO (mapRow)
    // =========================================================================

    private Usuario mapRow(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        // CORRECCIÓN: Asumiendo que la columna en DB es 'id_usuario'
        u.setId(rs.getLong("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setUsername(rs.getString("username"));
        u.setCorreo(rs.getString("correo"));
        u.setRol(rs.getString("rol"));
        u.setPassword(rs.getString("password"));
        u.setPassword(rs.getString("password"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
