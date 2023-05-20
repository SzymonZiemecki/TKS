package com.tks.microservices.userservice.rest.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tks.microservices.userservice.rest.dto.ChangePasswordDTO;
import com.tks.microservices.userservice.rest.dto.RegisterDTO;
import com.tks.microservices.userservice.rest.dto.UpdateUserDTO;
import com.tks.microservices.userservice.rest.dto.UserDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;

public interface UserApi {
    @GetMapping("/list")
    public ResponseEntity getUsers();

    @GetMapping(value = "/{id}")
    public ResponseEntity getUserById(@PathVariable("id") UUID id);

    @PutMapping(value = "/{id}")
    public ResponseEntity updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserDTO updatedUser);

    @PutMapping(value = "/{id}/changePassword")
    public ResponseEntity changePassword(@PathVariable("id") UUID id, @RequestBody ChangePasswordDTO changePasswordDTO);

    @PostMapping(value = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO registerDto);

    @PostMapping(value = "/addUser")
    public ResponseEntity addUser(@Valid @RequestBody UserDTO user);

    @PutMapping(value = "/{id}/suspendOrResume")
    public ResponseEntity suspendOrResumeUser(@PathParam("id") UUID userId);
}
