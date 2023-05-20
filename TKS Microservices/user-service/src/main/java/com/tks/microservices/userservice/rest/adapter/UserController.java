package com.tks.microservices.userservice.rest.adapter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.userservice.core.model.User;
import com.tks.microservices.userservice.rest.api.UserApi;
import com.tks.microservices.userservice.rest.dto.BaseUserDTO;
    import com.tks.microservices.userservice.rest.dto.ChangePasswordDTO;
    import com.tks.microservices.userservice.rest.dto.RegisterDTO;
import com.tks.microservices.userservice.rest.dto.UpdateUserDTO;
import com.tks.microservices.userservice.rest.dto.UserDTO;
import com.tks.microservices.userservice.port.ui.UserService;

@RestController
public class UserController implements UserApi {

    private UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity getUsers() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(UserDTO::userToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    public ResponseEntity getUserById(UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(UserDTO.userToDTO(user));
    }


    public ResponseEntity updateUser(UUID id, UpdateUserDTO dto) {
        User user = userService.updateUser(id, dto);
        return ResponseEntity.ok().body(UserDTO.userToDTO(user));
    }

    public ResponseEntity changePassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        userService.changeUserPassword(id, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity register(RegisterDTO registerDto) {
        BaseUserDTO user = new BaseUserDTO(registerDto);
        return ResponseEntity.ok(UserDTO.userToDTO(userService.addUser(UserDTO.userDTOToDomainModel(user))));
    }

    public ResponseEntity addUser(UserDTO user) {
        return ResponseEntity.ok(userService.addUser(UserDTO.userDTOToDomainModel(user)));
    }

    public ResponseEntity suspendOrResumeUser(UUID userId) {
        userService.suspendOrResumeUser(userId);
        return ResponseEntity.ok().build();
    }
}
