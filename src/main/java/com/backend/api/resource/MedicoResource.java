package com.backend.api.resource;

import com.backend.api.dao.MedicoDAO;
import com.backend.api.model.Medico;

import com.backend.api.security.RoleRequired;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {

    private MedicoDAO medicoDAO = new MedicoDAO();

    @GET
    public Response listarActivos() {
        try {
            return Response.ok(medicoDAO.listarActivos()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al listar médicos activos.\"}")
                    .build();
        }
    }

    @GET
    @Path("/todos")
    public Response listarTodos() {
        try {
            return Response.ok(medicoDAO.listarTodos()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al listar médicos.\"}")
                    .build();
        }
    }

    @POST
    public Response guardar(Medico medico) {
        try {
            boolean ok = medicoDAO.guardar(medico);
            return ok
                    ? Response.status(Response.Status.CREATED).build()
                    : Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al guardar médico.\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Medico medico) {
        try {
            medico.setId(id);
            boolean ok = medicoDAO.actualizar(medico);
            return ok
                    ? Response.ok().build()
                    : Response.status(Response.Status.NOT_FOUND).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al actualizar médico.\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @RoleRequired({"ADMIN"})
    public Response cambiarEstado(@PathParam("id") Long id, @QueryParam("activo") boolean activo) {
        try {
            medicoDAO.updateStatus(id, activo);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al activar médico.\"}").build();
        }
    }

}


