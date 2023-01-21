package com.pas.endpoint.auth;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Path("/auth/login")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@DeclareRoles({"Admin", "Manager", "BaseUser", "Unauthorized"})
public class AuthEndpointImpl {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @POST
    @PermitAll
    public Response authenticate(@NotNull Credentials credentials) {
        Credential credential = new UsernamePasswordCredential(credentials.getLogin(), new Password(credentials.getPassword()));
        CredentialValidationResult cValResult = identityStoreHandler.validate(credential);
        if (cValResult.getStatus() == CredentialValidationResult.Status.VALID) {
            String jwtToken = JWTAuthTokenUtils.generateJwtString(cValResult);
            return Response.accepted().header("Authentication", "Bearer " + jwtToken).type("application/jwt").entity(jwtToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Data
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Credentials {
        private String login;
        private String password;
    }
}
