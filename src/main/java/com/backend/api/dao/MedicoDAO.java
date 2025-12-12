package com.backend.api.dao;

import com.backend.api.config.DatabaseConfig;
import com.backend.api.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.backend.api.config.DatabaseConfig.getConnection;

public class MedicoDAO {

    // ===========================
    // LISTAR SOLO ACTIVOS
    // ===========================
    public List<Medico> listarActivos() throws SQLException {
        List<Medico> lista = new ArrayList<>();

        String sql = "SELECT id, nombre, especialidad, activo FROM medico WHERE activo = TRUE";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setActivo(rs.getBoolean("activo"));
                lista.add(m);
            }
        }

        return lista;
    }

    // ===========================
    // LISTAR TODOS
    // ===========================
    public List<Medico> listarTodos() throws SQLException {
        List<Medico> lista = new ArrayList<>();

        String sql = "SELECT id, nombre, especialidad, activo FROM medico";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getLong("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setActivo(rs.getBoolean("activo"));
                lista.add(m);
            }
        }

        return lista;
    }

    // ===========================
    // GUARDAR
    // ===========================
    public boolean guardar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nombre, especialidad, activo) VALUES (?, ?, TRUE)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getEspecialidad());

            return ps.executeUpdate() > 0;
        }
    }

    // ===========================
    // ACTUALIZAR
    // ===========================
    public boolean actualizar(Medico medico) throws SQLException {
        String sql = "UPDATE medico SET nombre = ?, especialidad = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getEspecialidad());
            ps.setLong(3, medico.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // ===========================
    // DESACTIVAR (estado = FALSE)
    // ===========================
    public void updateStatus(Long id, boolean activo) throws SQLException {
        String sql = "UPDATE medico SET activo = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setBoolean(1, activo);
            pst.setLong(2, id);
            pst.executeUpdate();
        }
    }
}


