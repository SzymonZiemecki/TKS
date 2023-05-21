package com.tks.microservices.userservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tks.microservices.userservice.core.model.Admin;
import com.tks.microservices.userservice.core.model.BaseUser;
import com.tks.microservices.userservice.core.model.IdTrait;
import com.tks.microservices.userservice.core.model.Manager;
import com.tks.microservices.userservice.core.model.User;

import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseUserDTO.class, name = "BaseUser"),
        @JsonSubTypes.Type(value = ManagerDTO.class, name = "Manager"),
        @JsonSubTypes.Type(value = AdminDTO.class, name = "Admin"),
})
public class UserDTO extends IdTrait {
    @Size(min = 2, max = 20)
    private String firstName;
    @Size(min = 2, max = 20)
    private String lastName;
    @Size(min = 2, max = 20)
    private String login;
    @Size(min = 2, max = 20)
    private AddressDTO address;
    private boolean suspended;
    private double accountBalance;

    public UserDTO(UserDTO dto) {
        this.setId(dto.getId());
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.login = dto.getLogin();
        this.address = dto.getAddress();
        this.suspended = dto.isSuspended();
        this.accountBalance = dto.getAccountBalance();
    }

    @SneakyThrows
    public static UserDTO userToDTO(User user) {
        UserDTO userDTO;
        if (user instanceof BaseUser) {
            userDTO = new BaseUserDTO();
        } else if (user instanceof Manager) {
            userDTO = new ManagerDTO();
        } else if (user instanceof Admin) {
            userDTO = new AdminDTO();
        } else {
            throw new NotSupportedException();
        }
        copyUserProperties(user, userDTO);
        return userDTO;
    }

    @SneakyThrows
    public static User userDTOToDomainModel(UserDTO userDTO, String password) {
        User user;
        if (userDTO instanceof BaseUserDTO) {
            user = new BaseUser();
        } else if (userDTO instanceof ManagerDTO) {
            user = new Manager();
        } else if (userDTO instanceof AdminDTO) {
            user = new Admin();
        } else {
            throw new NotSupportedException();
        }
        copyUserDTOProperties(user, userDTO, password);
        return user;
    }

    private static void copyUserDTOProperties(User user, UserDTO userDTO, String password) {
        user.setId(userDTO.getId());
        user.setSuspended(userDTO.isSuspended());
        user.setFirstName(userDTO.getFirstName());
        user.setPassword(password);
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setAddress(AddressDTO.addressDTOToDomainModel(userDTO.getAddress() == null ? null : userDTO.getAddress()));
        user.setAccountBalance(userDTO.getAccountBalance());
    }

    private static void copyUserProperties(User user, UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setSuspended(user.isSuspended());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setLogin(user.getLogin());
        userDTO.setAddress(AddressDTO.addressToDTO(user.getAddress() == null ? null : user.getAddress()));
        userDTO.setAccountBalance(user.getAccountBalance());
    }

}
