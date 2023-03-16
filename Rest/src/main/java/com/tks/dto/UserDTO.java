package com.tks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.IdTrait;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
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
    private CartDTO cart;
    private boolean suspended;
    private double accountBalance;
    private String userType;
}
