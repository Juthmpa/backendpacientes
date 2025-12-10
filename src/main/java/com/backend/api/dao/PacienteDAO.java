package com.backend.api.dao;

import com.backend.api.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.backend.api.config.DatabaseConfig.getConnection;

public class PacienteDAO {

    // LISTAR TODOS (GET)
    public List<Paciente> findAll() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCorreo(rs.getString("correo"));
                p.setEdad(rs.getInt("edad"));
                p.setDireccion(rs.getString("direccion"));
                p.setNumeroCedula(rs.getString("numero_cedula"));
                p.setActivo(rs.getBoolean("activo"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // BUSCAR POR ID (GET por ID)
    public Paciente findById(Long id) {
        String sql = "SELECT * FROM pacientes WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setId(rs.getLong("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setCorreo(rs.getString("correo"));
                    p.setEdad(rs.getInt("edad"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setNumeroCedula(rs.getString("numero_cedula"));
                    p.setActivo(rs.getBoolean("activo"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // CREAR PACIENTE (POST)
    public void save(Paciente p) throws SQLException {
        String sql = "INSERT INTO pacientes(nombre, correo, edad, direccion, numero_cedula, activo) VALUES(?,?,?,?,?,?)";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, p.getNombre());
            pst.setString(2, p.getCorreo());
            pst.setInt(3, p.getEdad());
            pst.setString(4, p.getDireccion());
            pst.setString(5, p.getNumeroCedula());
            pst.setBoolean(6, p.isActivo()); // <-- ¡CORREGIDO!
            pst.executeUpdate();

        }
    }

    // ACTUALIZAR PACIENTE (PUT)
    public void update(Paciente p) throws SQLException {
        String sql = "UPDATE pacientes SET nombre=?, correo=?, edad=?, direccion=?, numero_cedula=?, activo=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, p.getNombre());
            pst.setString(2, p.getCorreo());
            pst.setInt(3, p.getEdad());
            pst.setString(4, p.getDireccion());
            pst.setString(5, p.getNumeroCedula());
            pst.setBoolean(6, p.isActivo()); // <-- ¡CORREGIDO!
            pst.setLong(7, p.getId());
            pst.executeUpdate();

        }
    }

    // CAMBIAR ESTADO (PUT de estado)
    public void updateStatus(Long id, boolean activo) throws SQLException {
        String sql = "UPDATE pacientes SET activo=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setBoolean(1, activo);
            pst.setLong(2, id);
            pst.executeUpdate();

        }
    }
}