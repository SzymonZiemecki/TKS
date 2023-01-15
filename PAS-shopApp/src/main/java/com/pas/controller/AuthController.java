package com.pas.controller;/*
package com.pas.controller;

import com.pas.endpoint.auth.AuthAPI;
import com.pas.endpoint.auth.JWTAuthTokenUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@RequestScoped
@Named
@Getter
@Setter
@Slf4j
public class AuthController implements Serializable {

    @Inject
    IdentityStoreHandler identityStoreHandler;
    @Inject
    FacesContext facesContext;
    @Inject
    SecurityContext securityContext;
    private String login;
    private String password;

    @Inject
    private HttpServletRequest request;
    @Inject
    private UserManager userManager;

    public String login() throws ServletException {
        facesContext = FacesContext.getCurrentInstance();
        Credential credential = new UsernamePasswordCredential(login, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                getRequest(facesContext),
                getResponse(facesContext),
                withParams()
                        .credential(credential)
                        .newAuthentication(true)
                        .rememberMe(true)
        );
        return "start";
    }

    public String logout() throws ServletException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("Authorization");
        return "start";
    }

    private String authenticate(@NotNull AuthAPI.Credentials credentials) {
        Credential credential = new UsernamePasswordCredential(credentials.getLogin(), new Password(credentials.getPassword()));
        CredentialValidationResult cValResult = identityStoreHandler.validate(credential);
        if (cValResult.getStatus() == CredentialValidationResult.Status.VALID) {
            String jwtToken = JWTAuthTokenUtils.generateJwtString(cValResult);
            String authHeader = "Bearer " + jwtToken;
            return authHeader;
        } else {
            return "Unauthorized";
        }
    }

    private static HttpServletResponse getResponse(FacesContext context) {
        return (HttpServletResponse) context
                .getExternalContext()
                .getResponse();
    }

    private static HttpServletRequest getRequest(FacesContext context) {
        return (HttpServletRequest) context
                .getExternalContext()
                .getRequest();
    }

}
*/
