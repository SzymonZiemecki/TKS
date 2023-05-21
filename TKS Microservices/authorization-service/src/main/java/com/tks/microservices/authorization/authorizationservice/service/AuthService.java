package com.tks.microservices.authorization.authorizationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtProvider jwtProvider;

    public String generateToken(String username, Object role) {
        return jwtProvider.generateToken(username, role);
    }

    public boolean validateToken(String token) {
        return jwtProvider.validateToken(token);
    }
}
