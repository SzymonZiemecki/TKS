package com.tks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.IdTrait;
import com.tks.model.Address;
import com.tks.model.Cart;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(callSuper = true)
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
    private Address address;
    private Cart cart;
    private boolean suspended;
    private Double accountBalance;
    private UserType userType;
}
