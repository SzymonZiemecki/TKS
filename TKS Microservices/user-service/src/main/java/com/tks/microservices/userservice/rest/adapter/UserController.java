package com.tks.microservices.userservice.rest.adapter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.userservice.core.model.User;
import com.tks.microservices.userservice.repository.model.security.CustomUserDetails;
import com.tks.microservices.userservice.rest.api.UserApi;
import com.tks.microservices.userservice.rest.client.OrderApiClient;
import com.tks.microservices.userservice.rest.dto.BaseUserDTO;
    import com.tks.microservices.userservice.rest.dto.ChangePasswordDTO;
import com.tks.microservices.userservice.rest.dto.CreateClientDto;
import com.tks.microservices.userservice.rest.dto.RegisterDTO;
import com.tks.microservices.userservice.rest.dto.UpdateUserDTO;
import com.tks.microservices.userservice.rest.dto.UserDTO;
import com.tks.microservices.userservice.port.ui.UserService;

@RestController
public class UserController implements UserApi {

    private UserService userService;
    private OrderApiClient orderApiClient;

    public UserController(final UserService userService, final OrderApiClient orderApiClient) {
        this.userService = userService;
        this.orderApiClient = orderApiClient;
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
        User createdUser = userService.addUser(UserDTO.userDTOToDomainModel(user, registerDto.getPassword()));
        CreateClientDto dto = new CreateClientDto();
        dto.setAccountBalance(createdUser.getAccountBalance());
        dto.setAccountId(createdUser.getId());
        orderApiClient.create(dto);
        return ResponseEntity.ok(UserDTO.userToDTO(createdUser));
    }

    public ResponseEntity addUser(UserDTO user) {
        return ResponseEntity.ok(userService.addUser(UserDTO.userDTOToDomainModel(user, "xd")));
    }

    public ResponseEntity suspendOrResumeUser(UUID userId) {
        userService.suspendOrResumeUser(userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public CustomUserDetails findUserForAuthorization(final String login) {
        return userService.findUserForAuthorization(login);
    }
}
