package com.tks.microservices.userservice.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.userservice.core.model.User;
import com.tks.microservices.userservice.dto.BaseUserDTO;
import com.tks.microservices.userservice.dto.ChangePasswordDTO;
import com.tks.microservices.userservice.dto.RegisterDTO;
import com.tks.microservices.userservice.dto.UserDTO;
import com.tks.microservices.userservice.port.ui.UserService;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RestController
public class UserController {

    private UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUserById(@PathVariable("id") UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(UserDTO.userToDTO(user));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity updateUser(@PathVariable("id") UUID id, @RequestBody UserDTO updatedUser) {
        User user = userService.updateUser(id, UserDTO.userDTOToDomainModel(updatedUser));
        return ResponseEntity.ok().body(UserDTO.userToDTO(user));
    }

    @PutMapping(value = "/{id}/changePassword")
    public ResponseEntity changePassword(@PathVariable("id") UUID id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changeUserPassword(id, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO registerDto) {
        BaseUserDTO user = new BaseUserDTO(registerDto);
        return ResponseEntity.ok(UserDTO.userToDTO(userService.addUser(UserDTO.userDTOToDomainModel(user))));
    }

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.addUser(UserDTO.userDTOToDomainModel(user)));
    }

    @PutMapping(value = "/{id}/suspendOrResume")
    public ResponseEntity suspendOrResumeUser(@PathParam("id") UUID userId) {
        userService.suspendOrResumeUser(userId);
        return ResponseEntity.ok().build();
    }
}
