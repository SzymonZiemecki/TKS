package com.tks.microservices.authorization.authorizationservice.rest.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.authorization.authorizationservice.rest.api.AuthApi;
import com.tks.microservices.authorization.authorizationservice.rest.dto.AuthRequest;
import com.tks.microservices.authorization.authorizationservice.service.AuthService;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String getToken(final AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername(), authenticate.getAuthorities());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @Override
    public ResponseEntity validateToken(final String token) {
        if(authService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
