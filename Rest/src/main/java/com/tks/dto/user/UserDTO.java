package com.tks.dto.user;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tks.IdTrait;
import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.User.User;
import com.tks.dto.AddressDTO;
import com.tks.model.Cart;

import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.*;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private AddressDTO address;
    private boolean suspended;
    private double accountBalance;

    public UserDTO(UserDTO dto) {
        this.setId(dto.getId());
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.login = dto.getLogin();
        this.password = dto.getPassword();
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
    public static User userDTOToDomainModel(UserDTO userDTO) {
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
        copyUserDTOProperties(user, userDTO);
        return user;
    }

    private static void copyUserDTOProperties(User user, UserDTO userDTO) {
        user.setId(userDTO.getId());
        user.setSuspended(userDTO.isSuspended());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setLogin(userDTO.getLogin());
        user.setAddress(AddressDTO.addressDTOToDomainModel(userDTO.getAddress() == null ? null : userDTO.getAddress()));
        user.setAccountBalance(userDTO.getAccountBalance());
        user.setCart(new Cart(new ArrayList<>()));
    }

    private static void copyUserProperties(User user, UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setSuspended(user.isSuspended());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setLogin(user.getLogin());
        userDTO.setAddress(AddressDTO.addressToDTO(user.getAddress() == null ? null : user.getAddress()));
        userDTO.setAccountBalance(user.getAccountBalance());
    }

}
