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
import org.springframework.web.bind.annotation.RequestParam;

import com.tks.microservices.userservice.repository.model.security.CustomUserDetails;
import com.tks.microservices.userservice.rest.dto.ChangePasswordDTO;
import com.tks.microservices.userservice.rest.dto.RegisterDTO;
import com.tks.microservices.userservice.rest.dto.UpdateUserDTO;
import com.tks.microservices.userservice.rest.dto.UserDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;

public interface UserApi {
    @GetMapping("/list")
    ResponseEntity getUsers();

    @GetMapping(value = "/{id}")
    ResponseEntity getUserById(@PathVariable("id") UUID id);

    @PutMapping(value = "/{id}")
    ResponseEntity updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserDTO updatedUser);

    @PutMapping(value = "/{id}/changePassword")
    ResponseEntity changePassword(@PathVariable("id") UUID id, @RequestBody ChangePasswordDTO changePasswordDTO);

    @PostMapping(value = "/register")
    ResponseEntity register(@Valid @RequestBody RegisterDTO registerDto);

    @PostMapping(value = "/addUser")
    ResponseEntity addUser(@Valid @RequestBody UserDTO user);

    @PutMapping(value = "/{id}/suspendOrResume")
    ResponseEntity suspendOrResumeUser(@PathParam("id") UUID userId);

    @GetMapping("/find")
    CustomUserDetails findUserForAuthorization(@RequestParam("login") String login);
}
