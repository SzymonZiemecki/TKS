package com.pas.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty
    UUID id;
    @JsonProperty
    @Size(min = 2, max = 20)
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty
    private Address address;
    @JsonProperty
    private Double accountBalance;
    @JsonProperty
    private String role;

    public UserDTO(UUID id, String firstName, String lastName, String login, Address address, Double accountBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.address = address;
        this.accountBalance = accountBalance;
        this.role = "BaseUser";
    }

    public UserDTO(String firstName, String lastName, String login,String password, Address address, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.accountBalance = accountBalance;
        this.role = "BaseUser";
    }

    public UserDTO(String firstName, String lastName, String login, String password, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.accountBalance = accountBalance;
    }

    public static UserDTO fromEntityToDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getAddress(), user.getAccountBalance());
    }

    public static List<UserDTO> entityListToDTO(List<User> users) {
        return users.stream().map(UserDTO::fromEntityToDTO).collect(Collectors.toList());
    }

    public static User fromDTOToEntity(UserDTO userDTO) {
        if (userDTO.getRole().equals("BaseUser")) {
            return new BaseUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), userDTO.getAddress(), new Cart(), false, userDTO.getAccountBalance());
        } else if (userDTO.getRole().equals("Manager")) {
            return new Manager(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), userDTO.getAddress(), new Cart(), false, userDTO.getAccountBalance());
        } else if (userDTO.getRole().equals("Admin")) {
            return new Admin(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), userDTO.getAddress(), new Cart(), false, userDTO.getAccountBalance());
        } else {
            throw new IllegalStateException("User is not instance of proper class");
        }
    }
}
