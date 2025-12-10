package com.backend.api.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {


    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        // 1. Configuración de encabezados para CORS

        // Permitir peticiones desde cualquier origen (Frontend Vue 3)
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Permitir los métodos HTTP que usaremos (CRUD)
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Permitir encabezados necesarios (Content-Type es crucial para JSON)
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");

        // 2. Manejo de la Petición Preflight (OPTIONS)
        // El navegador envía OPTIONS antes de POST/PUT/DELETE. Debemos responder con éxito.
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            // Detiene el proceso y devuelve una respuesta 200 OK inmediatamente.
            responseContext.setStatus(Response.Status.OK.getStatusCode());
        }
    }
}