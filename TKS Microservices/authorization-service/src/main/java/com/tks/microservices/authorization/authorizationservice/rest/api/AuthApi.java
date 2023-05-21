package com.tks.microservices.authorization.authorizationservice.rest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tks.microservices.authorization.authorizationservice.rest.dto.AuthRequest;

@RequestMapping(value = "/auth")
public interface AuthApi {

    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequest authRequest);

    @GetMapping("/validate")
    public ResponseEntity validateToken(@RequestParam("token") String token);

}
