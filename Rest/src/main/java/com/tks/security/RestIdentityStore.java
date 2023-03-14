package com.tks.security;

import com.tks.User.User;
import com.tks.userinterface.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class RestIdentityStore implements IdentityStore {

    @Inject
    private UserService userManager;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            String givenPassword = usernamePasswordCredential.getPasswordAsString();
            User user = userManager.findUserByLogin((usernamePasswordCredential.getCaller()));
            if(user.getPassword().equals(givenPassword) && !user.isSuspended()) {
                CredentialValidationResult cr = new CredentialValidationResult(user.getLogin(), new HashSet<>(Collections.singletonList(user.getClass().getSimpleName())));
                return cr;
            }
            return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
