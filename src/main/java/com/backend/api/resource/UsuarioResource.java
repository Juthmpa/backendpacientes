package com.backend.api.resource;

import com.backend.api.dao.UsuarioDAO;
import com.backend.api.model.Usuario;
import com.backend.api.security.RoleRequired;
import com.backend.api.util.ValidatorUtil;
import com.backend.api.util.ValidationException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // =========================================================================
    // 1. LISTAR TODOS (GET /api/usuarios)
    // =========================================================================

    @GET
    @RoleRequired("ADMIN")
    public Response listarTodos() {
        try {
            // CORRECCIÓN: Llamar al método listar()
            List<Usuario> usuarios = usuarioDAO.listar();
            return Response.ok(usuarios).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno al listar usuarios.\"}").build();
        }
    }

    // =========================================================================
    // 2. BUSCAR POR ID (GET /api/usuarios/{id})
    // =========================================================================

    @GET
    @Path("/{id}")
    @RoleRequired("ADMIN")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            // CORRECCIÓN: Llamar al método buscarPorId()
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Usuario no encontrado.\"}").build();
            }
            return Response.ok(usuario).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno al buscar usuario.\"}").build();
        }
    }

    // =========================================================================
    // 3. CREAR USUARIO (POST /api/usuarios)
    // =========================================================================

    @POST
    @RoleRequired("ADMIN")
    public Response crearUsuario(Usuario usuario) {
        try {
            // --- 1. VALIDACIONES DE DATOS ---
            ValidatorUtil.validateRequired(usuario.getNombre(), "Nombre");
            ValidatorUtil.validateRequired(usuario.getCorreo(), "Correo");
            ValidatorUtil.validateRequired(usuario.getUsername(), "Username");
            ValidatorUtil.validateRequired(usuario.getPassword(), "Contraseña");
            ValidatorUtil.validateEmail(usuario.getCorreo());
            ValidatorUtil.validateStringNoNumbers(usuario.getNombre(), "Nombre");
            ValidatorUtil.validatePasswordStrength(usuario.getPassword());

            usuario.setActivo(true);

            // CORRECCIÓN: Llamar al método guardar()
            usuarioDAO.guardar(usuario);

            usuario.setPassword(null);

            return Response.status(Response.Status.CREATED).entity(usuario).build();

        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}").build();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getSQLState().startsWith("23")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"Error: El username o el correo ya están registrados.\"}").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno del servidor al crear el usuario.\"}").build();
        }
    }

    // =========================================================================
    // 4. ACTUALIZAR USUARIO (PUT /api/usuarios/{id})
    // =========================================================================

    @PUT
    @Path("/{id}")
    @RoleRequired("ADMIN")
    public Response actualizarUsuario(@PathParam("id") Long id, Usuario usuario) {
        usuario.setId(id);

        try {
            // CORRECCIÓN: Llamar al método buscarPorId()
            if (usuarioDAO.buscarPorId(id) == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Usuario a actualizar no encontrado.\"}").build();
            }

            // --- 2. VALIDACIONES DE DATOS (Mismas que en POST, omitiendo password si está vacío) ---
            ValidatorUtil.validateRequired(usuario.getNombre(), "Nombre");
            ValidatorUtil.validateRequired(usuario.getCorreo(), "Correo");
            ValidatorUtil.validateRequired(usuario.getUsername(), "Username");
            ValidatorUtil.validateEmail(usuario.getCorreo());
            ValidatorUtil.validateStringNoNumbers(usuario.getNombre(), "Nombre");

            if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
                ValidatorUtil.validatePasswordStrength(usuario.getPassword());
            }

            // CORRECCIÓN: Llamar al método actualizar()
            usuarioDAO.actualizar(usuario);

            usuario.setPassword(null);

            return Response.ok(usuario).build();

        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}").build();

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getSQLState().startsWith("23")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"Error: El username o el correo ya están registrados en otro usuario.\"}").build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno al actualizar el usuario.\"}").build();
        }
    }

    // =========================================================================
    // 5. CAMBIAR ESTADO (PUT /api/usuarios/{id}/status)
    // =========================================================================

    @PUT
    @Path("/{id}/status")
    public Response cambiarEstado(@PathParam("id") Long id, @QueryParam("activo") boolean activo) {
        try {
            // CORRECCIÓN: Llamar al método buscarPorId()
            if (usuarioDAO.buscarPorId(id) == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Usuario no encontrado para cambiar el estado.\"}").build();
            }

            // Llama al método que se añade en el DAO
            usuarioDAO.updateStatus(id, activo);

            return Response.noContent().build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error al cambiar el estado del usuario.\"}").build();
        }
    }
}