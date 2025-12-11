package com.backend.api.security;

import com.backend.api.dao.UsuarioDAO;
import com.backend.api.model.Usuario;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            String authHeader = requestContext.getHeaderString("Authorization");

            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Credenciales no enviadas\"}")
                        .build());
                return;
            }

            String base64 = authHeader.substring("Basic ".length());
            String decoded = new String(Base64.getDecoder().decode(base64));
            String[] parts = decoded.split(":", 2);

            if (parts.length != 2) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Formato de credenciales incorrecto\"}")
                        .build());
                return;
            }

            String username = parts[0];
            String password = parts[1];

            Usuario u = usuarioDAO.buscarPorCredenciales(username, password);

            if (u == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Usuario o contraseña incorrectos\"}")
                        .build());
                return;
            }

            // Guardamos rol en propiedades
            requestContext.setProperty("userRole", u.getRol());

        } catch (SQLException e) {
            e.printStackTrace();
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error interno en autenticación\"}")
                    .build());
        }
    }
}

