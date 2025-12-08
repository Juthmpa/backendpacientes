package com.itq.resource;

import com.itq.dao.PacienteDAO;
import com.itq.model.Paciente;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("pacientes") // Endpoint principal: /api/pacientes
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    // Simulación del patrón DAO (debe inyectarse o instanciarse)
    private final PacienteDAO pacienteDAO = new PacienteDAO();

    // GET /api/pacientes -> Listar todos [cite: 38, 64]
    @GET
    public Response getAllPacientes() {
        List<Paciente> pacientes = pacienteDAO.findAll();
        return Response.ok(pacientes).build(); // 200 OK
    }

    // GET /api/pacientes/{id} -> Obtener por ID [cite: 39]
    @GET
    @Path("{id}")
    public Response getPacienteById(@PathParam("id") Long id) {
        Paciente paciente = pacienteDAO.findById(id);
        if (paciente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Paciente no encontrado").build(); // 404
        }
        return Response.ok(paciente).build(); // 200 OK
    }

    // POST /api/pacientes -> Crear nuevo paciente [cite: 40, 65]
    @POST
    public Response createPaciente(Paciente paciente) {
        // Lógica de validación de Cédula (PENDIENTE: implementar validación real en DAO)
        if (paciente.getNumeroCedula() == null || paciente.getNumeroCedula().isEmpty()) {
            return Response.status(400).entity("El número de cédula es obligatorio").build(); // 400 Bad Request
        }

        // Simulación: Guardar en DB
        pacienteDAO.save(paciente);
        return Response.status(Response.Status.CREATED).entity(paciente).build(); // 201 Created
    }

    // PUT /api/pacientes/{id} -> Actualizar paciente [cite: 41]
    @PUT
    @Path("{id}")
    public Response updatePaciente(@PathParam("id") Long id, Paciente paciente) {
        paciente.setId(id); // Asegurar que el ID del path se use
        pacienteDAO.update(paciente);
        return Response.ok(paciente).build(); // 200 OK
    }

    // PUT /api/pacientes/{id}/status -> Activar/Desactivar [cite: 42] (Implementación REST más limpia)
    @PUT
    @Path("{id}/status")
    public Response updateStatus(@PathParam("id") Long id, @QueryParam("activo") boolean activo) {
        pacienteDAO.updateStatus(id, activo);
        String mensaje = activo ? "activado" : "desactivado";
        return Response.ok("Paciente " + id + " " + mensaje + " correctamente.").build(); // 200 OK
    }
}