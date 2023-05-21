package com.tks.microservices.authorization.authorizationservice.config;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationRole implements GrantedAuthority {

    private Permission permission;

    @Override
    public String getAuthority() {
        return permission.toString();
    }

    public static ApplicationRole valueOf(String permission) {
        return switch (permission) {
            case "Admin" -> new ApplicationRole(Permission.Admin);
            case "BaseUser" -> new ApplicationRole(Permission.BaseUser);
            case "Manager" -> new ApplicationRole(Permission.Manager);
            default -> throw new IllegalArgumentException();
        };
    }

}

