package com.backend.api.resource;

import com.backend.api.dao.PacienteDAO;
import com.backend.api.model.Paciente;
import com.backend.api.security.RoleRequired;
import com.backend.api.util.CedulaValidator;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    private PacienteDAO pacienteDAO = new PacienteDAO();

    // 1. LISTAR TODOS (GET /api/pacientes)
    @GET
    @RoleRequired({"ADMIN", "MEDICO"})
    public List<Paciente> listarTodos() {
        return pacienteDAO.findAll();
    }

    // 2. BUSCAR POR ID (GET /api/pacientes/{id})
    @GET
    @Path("/{id}")
    @RoleRequired({"ADMIN", "MEDICO"})
    public Response buscarPorId(@PathParam("id") Long id) {
        Paciente paciente = pacienteDAO.findById(id);
        if (paciente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(paciente).build();
    }

    // 3. CREAR PACIENTE (POST /api/pacientes)
    @POST
    @RoleRequired({"ADMIN", "MEDICO"})
    public Response crearPaciente(Paciente paciente) {

        // VALIDACIÓN DE CÉDULA
        if (!CedulaValidator.isValidarCedula(paciente.getNumeroCedula())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Número de cédula ecuatoriana no válido.\"}").build();
        }

        try {
            // Se asegura que el nuevo paciente se guarde como activo
            paciente.setActivo(true);
            pacienteDAO.save(paciente);

            return Response.status(Response.Status.CREATED).entity(paciente).build();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getSQLState().startsWith("23")) {
                // Manejo de error de duplicidad (UNIQUE constraint)
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"Error: La cédula o el correo ya están registrados.\"}").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno del servidor: " + e.getMessage() + "\"}").build();
        }
    }

    // 4. ACTUALIZAR PACIENTE (PUT /api/pacientes/{id})
    @PUT
    @Path("/{id}")
    @RoleRequired({"ADMIN", "MEDICO"})
    public Response actualizarPaciente(@PathParam("id") Long id, Paciente paciente) {
        paciente.setId(id);

        // VALIDACIÓN DE CÉDULA
        if (!CedulaValidator.isValidarCedula(paciente.getNumeroCedula())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Número de cédula ecuatoriana no válido.\"}").build();
        }

        try {
            if (pacienteDAO.findById(id) == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            pacienteDAO.update(paciente);

            return Response.ok(paciente).build();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getSQLState().startsWith("23")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"Error: La cédula o el correo ya están registrados en otro paciente.\"}").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno al actualizar.\"}").build();
        }
    }

    // 5. CAMBIAR ESTADO (PUT /api/pacientes/{id}/status)
    @PUT
    @Path("/{id}/status")
    @RoleRequired({"ADMIN"})
    public Response cambiarEstado(@PathParam("id") Long id, @QueryParam("activo") boolean activo) {
        try {
            pacienteDAO.updateStatus(id, activo);

            // 204 No Content
            return Response.noContent().build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al cambiar el estado del paciente.\"}").build();
        }
    }
}