package com.pas.model.User;

import com.fasterxml.jackson.annotation.*;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.IdTrait;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(Admin.class),
        @JsonSubTypes.Type(BaseUser.class) }
)
public abstract class User extends IdTrait {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Boolean active;
    private Address address;
    private Cart cart;
    private boolean suspended;
    private Double accountBalance;

    public User(String firstName, String lastName, String login, String password, Boolean active, Address address, Cart cart, boolean suspended, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.active = active;
        this.address = address;
        this.cart = cart;
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }
}
