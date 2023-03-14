package com.tks.adapter;

import com.tks.security.JWTAuthTokenUtils;
import com.tks.api.AuthRestApi;
import com.tks.dto.JWTResponse;
import com.tks.dto.LoginDTO;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

public class AuthEndpointImpl implements AuthRestApi {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    public Response authenticate(@NotNull LoginDTO credentials) {
        Credential credential = new UsernamePasswordCredential(credentials.getLogin(), new Password(credentials.getPassword()));
        CredentialValidationResult cValResult = identityStoreHandler.validate(credential);
        if (cValResult.getStatus().equals(VALID)) {
            String jwtToken = JWTAuthTokenUtils.generateJwtString(cValResult);
            JWTResponse jwtResponse = new JWTResponse(jwtToken);
            return Response.accepted().header("Authentication", "Bearer " + jwtToken).type(MediaType.APPLICATION_JSON).entity(jwtResponse).build();
        } else {
            return Response.status(UNAUTHORIZED).entity(UNAUTHORIZED).build();
        }
    }
}
