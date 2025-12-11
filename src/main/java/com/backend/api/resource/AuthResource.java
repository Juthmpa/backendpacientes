package com.backend.api.resource;

import com.backend.api.dao.UsuarioDAO;
import com.backend.api.model.LoginRequest;
import com.backend.api.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private UsuarioDAO usuarioDAO;

    // POST /api/auth/login
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Credenciales requeridas.\"}")
                    .build();
        }

        try {
            // Llama al método del DAO para verificar credenciales
            Usuario usuario = usuarioDAO.buscarPorCredenciales(
                    request.getUsername(),
                    request.getPassword()
            );

            if (usuario != null) {
                // Éxito: Devuelve el objeto Usuario (excluyendo la contraseña si es posible)
                // Se devuelve el objeto completo para que el frontend pueda ver el 'rol' y el 'nombre'.
                // Nota: Idealmente, se devolvería un JWT aquí.

                // Opción segura: Clonar y limpiar password antes de enviar (no obligatorio, pero recomendado)
                Usuario responseUsuario = new Usuario(usuario); // Asume un constructor de copia o usa la lógica de tu modelo
                responseUsuario.setPassword(null);

                return Response.ok(responseUsuario).build();
            } else {
                // Error de autenticación
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Username o contraseña incorrectos, o usuario inactivo.\"}")
                        .build();
            }
        } catch (SQLException e) {
            System.err.println("Error SQL durante el login: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno en el servidor.\"}")
                    .build();
        }
    }
}
