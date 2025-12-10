package com.backend.api.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")  // Base URL → /api/
public class JakartaRestConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        // Registrar aquí TODOS los recursos REST
        resources.add(com.backend.api.resource.PacienteResource.class);
        resources.add(com.backend.api.resource.UsuarioResource.class);

        resources.add(com.backend.api.config.CorsFilter.class);


        return resources;
    }
}