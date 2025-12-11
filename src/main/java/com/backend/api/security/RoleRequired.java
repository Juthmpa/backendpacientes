package com.backend.api.security;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleRequired {
    String[] value();  // uno o varios roles permitidos
}
