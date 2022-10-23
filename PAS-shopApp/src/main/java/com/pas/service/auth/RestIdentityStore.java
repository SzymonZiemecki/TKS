package com.pas.service.auth;

import com.pas.manager.UserManager;
import com.pas.model.Role;
import com.pas.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class RestIdentityStore implements IdentityStore {

    @Inject
    private UserManager userManager;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        userManager.register(User.builder().firstName("Szymon").lastName("Ziemecki").login("admin").password("pass").roles(Arrays.asList(Role.ADMIN,Role.USER,Role.MODERATOR)).active(true).build());
        userManager.register(User.builder().firstName("Szymon").lastName("Ziemecki").login("user").password("pass").roles(Arrays.asList(Role.USER)).active(true).build());
        userManager.register(User.builder().firstName("Szymon").lastName("Ziemecki").login("moderator").password("pass").roles(Arrays.asList(Role.MODERATOR)).active(true).build());
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            String givenPassword = usernamePasswordCredential.getPasswordAsString();
            User user = userManager.findByLogin((usernamePasswordCredential.getCaller())).orElseThrow(EntityNotFoundException::new);
            Set<String> userRoles = user.getRoles().stream().map(role -> role.toString()).collect(Collectors.toSet());
            if(user.getPassword().equals(givenPassword) && user.getActive()) {
                return new CredentialValidationResult(user.getLogin(), userRoles);
            }
            return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
