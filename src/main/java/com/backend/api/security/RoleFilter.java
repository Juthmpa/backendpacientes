package com.backend.api.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Arrays;

@Provider
@RoleRequired({})
@Priority(Priorities.AUTHORIZATION)
public class RoleFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        RoleRequired annotation = resourceInfo.getResourceMethod().getAnnotation(RoleRequired.class);
        if (annotation == null) {
            return; // el endpoint no requiere rol
        }

        String userRole = (String) requestContext.getProperty("userRole");

        if (userRole == null) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\": \"No autorizado\"}").build());
            return;
        }

        boolean permit = Arrays.asList(annotation.value()).contains(userRole);

        if (!permit) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\": \"Acceso denegado: Rol insuficiente\"}")
                    .build());
        }
    }
}